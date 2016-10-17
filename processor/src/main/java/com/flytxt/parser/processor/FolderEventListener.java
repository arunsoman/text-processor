package com.flytxt.parser.processor;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "folder")
@Data
public class FolderEventListener {

    private AtomicBoolean recursive = new AtomicBoolean(true);

    private WatchService watcher;

    private Timer timer;

    private LoadingCache<String, OutputUnit> cache;

    private Map<WatchKey, List<OutputUnit>> hashCache = new HashMap<>(10);

    private long cacheCleanUpTime;

    private int expireTimeInMinutes;

    @Autowired
    private ProxyScripts ps;

    @Data
    public static class Watch {

        private String source;

        private String regex;

        private String destination;

        public Watch(String source, String regex, String destination) {
            super();
            this.source = source;
            this.regex = regex;
            this.destination = destination;
        }

    }

    @PostConstruct
    public void start() {
        try {
            watcher = FileSystems.getDefault().newWatchService();
            cache = CacheBuilder.newBuilder().maximumSize(Integer.MAX_VALUE).expireAfterAccess(expireTimeInMinutes, TimeUnit.MINUTES)
                    .removalListener(RemovalListeners.asynchronous(new RemovalListenerImpl(), Executors.newCachedThreadPool())).build(new CacheLoader<String, OutputUnit>() {

                        @Override
                        public OutputUnit load(final String arg0) throws Exception {
                            return null;
                        }
                    });

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        final Thread thread = new Thread(new Producer());
        thread.start();
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                cache.cleanUp();
            }

        }, 0, cacheCleanUpTime); // every 30s cache clean up will happen

        for (final Watch w : ps.getFolderWatch())
            try {
                attachFolder(w.source, w.regex, w.destination);
            } catch (final IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    private class OutputUnit {

        private final String regex;

        private final String outFolder;

        private final LoadingCache<String, OutputUnit> cache;

        @SuppressWarnings("unused")
        private final Map<WatchKey, List<OutputUnit>> hashCache;

        public OutputUnit(final String regex, final String outFolder, final LoadingCache<String, OutputUnit> cache, final Map<WatchKey, List<OutputUnit>> hCache) {
            super();
            this.regex = regex;
            this.outFolder = outFolder;
            this.cache = cache;
            this.hashCache = hCache;

        }

    }

    public void attachFolder(final String source, final String regex, final String outfolder) throws IOException {
        final WatchKey key = Paths.get(source).register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        List<OutputUnit> outputUnits = hashCache.get(key);
        try {
            outputUnits.add(new OutputUnit(regex, outfolder, cache, hashCache));
        } catch (final NullPointerException e) {
            outputUnits = new ArrayList<>();
            outputUnits.add(new OutputUnit(regex, outfolder, cache, hashCache));
        }
        hashCache.put(key, outputUnits);

    }

    @PreDestroy
    public void shutdown() {
        cache.cleanUp();
        timer.cancel();
        recursive.set(false);
    }

    public class RemovalListenerImpl implements RemovalListener<String, OutputUnit> {

        @Override
        public void onRemoval(final RemovalNotification<String, OutputUnit> remove) {
            try {
                if (remove.wasEvicted()) {
                    final OutputUnit value = remove.getValue();
                    createlinkonFileWriteComplete(remove.getKey(), value);
                }
            } catch (UnsupportedOperationException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void createlinkonFileWriteComplete(final String sourceFile, final OutputUnit outputUnit) throws InterruptedException, IOException, UnsupportedOperationException {
        final File file = new File(sourceFile);
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            Files.createSymbolicLink(Paths.get(outputUnit.outFolder.concat("/").concat(file.getName())), Paths.get(sourceFile));
        } catch (final IOException e) {
            if (file.exists())
                outputUnit.cache.put(sourceFile, outputUnit);
            else
                System.out.println("File was deleted while copying: '" + file.getAbsolutePath() + "'");
        } finally {
            if (raf != null)
                raf.close();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> WatchEvent<T> cast(final WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    private class Producer implements Runnable {

        @Override
        public void run() {
            try {
                WatchKey take = null;
                while (recursive.get()) {
                    try {
                        take = watcher.take();
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (final WatchEvent<?> event : take.pollEvents()) {
                        final Kind<?> kind = event.kind();

                        if (kind == OVERFLOW)
                            continue;
                        Path source = (Path) take.watchable();
                        final WatchEvent<Path> ev = cast(event);
                        final Path file = ev.context();
                        final String fileName = file.getFileName().toString();
                        System.out.format("%s: %s\n", event.kind().name(), fileName);

                        if (kind == ENTRY_CREATE) {
                            final List<OutputUnit> outputUnits = hashCache.get(take);
                            boolean found = false;
                            for (final OutputUnit outputUnit : outputUnits)
                                if (outputUnit.regex != null && fileName.matches(outputUnit.regex)) {
                                    outputUnit.cache.put(source.toString().concat("/").concat(fileName), outputUnit);
                                    found = true;
                                }
                            if (!found)
                                System.out.println("no regx found for" + fileName);
                        }
                        if (kind == ENTRY_MODIFY)
                            cache.get(source.toString().concat("/").concat(fileName));
                    }
                    take.reset();
                }
            } catch (final ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}