package com.flytxt.parser.processor;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
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
import com.sun.nio.file.SensitivityWatchEventModifier;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
	private Map<WatchKey, List<OutputUnit>> hashCache = new HashMap<WatchKey, List<OutputUnit>>(10);
	private long cacheCleanUpTime;
	private int expireTimeInMinutes;

	
	private List<Watch> watch;


	@Data
	public static class Watch {
		private String source;
		private String regex;
		private String destination;

		public Watch() {
			System.out.println("------------");
		}

	}

	@PostConstruct
	public void start() {
		try {
		 
			watcher = FileSystems.getDefault().newWatchService();
			cache = CacheBuilder.newBuilder().maximumSize(Integer.MAX_VALUE)
					.expireAfterAccess(expireTimeInMinutes, TimeUnit.MINUTES)
					.removalListener(
							RemovalListeners.asynchronous(new RemovalListenerImpl(), Executors.newCachedThreadPool()))
					.build(new CacheLoader<String, OutputUnit>() {

						@Override
						public OutputUnit load(String arg0) throws Exception {
							return null;
						}
					});

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Thread thread = new Thread(new Producer());
		thread.start();
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				cache.cleanUp();
			}

		}, 0, cacheCleanUpTime); // every 30s cache clean up will happen

		for (Watch w : watch)
			try {
				attachFolder(w.source, w.regex, w.destination);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private class OutputUnit {
		private String regex;
		private String outFolder;
		private LoadingCache<String, OutputUnit> cache;
		private Map<WatchKey, List<OutputUnit>> hashCache;
		

		public OutputUnit(String regex, String outFolder, LoadingCache<String, OutputUnit> cache,
				Map<WatchKey, List<OutputUnit>> hCache) {
			super();
			this.regex = regex;
			this.outFolder = outFolder;
			this.cache = cache;
			this.hashCache = hCache;
			
		}

	}

	public void attachFolder(String source, String regex, String outfolder) throws IOException {
		WatchKey key = Paths.get(source).register(watcher,new WatchEvent.Kind[]{ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY},
				SensitivityWatchEventModifier.HIGH);
		List<OutputUnit> outputUnits = hashCache.get(key);
		try {
			System.out.println(((Path)key.watchable()));
			outputUnits.add(new OutputUnit(regex, outfolder, cache, hashCache));
		} catch (NullPointerException e) {
			outputUnits = new ArrayList<OutputUnit>();
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

		public void onRemoval(RemovalNotification<String, OutputUnit> remove) {
			try {
				if (remove.wasEvicted()) {
					OutputUnit value = remove.getValue();
					createlinkonFileWriteComplete(remove.getKey(), value);
				}
			} catch (UnsupportedOperationException | InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void createlinkonFileWriteComplete(String sourceFile, OutputUnit outputUnit)
			throws InterruptedException, IOException, UnsupportedOperationException {
		File file = new File(sourceFile);
		RandomAccessFile raf = null;
		try {

			raf = new RandomAccessFile(file, "rw");
			Files.createSymbolicLink(Paths.get(outputUnit.outFolder.concat("/").concat(file.getName())),
					Paths.get(sourceFile));
		} catch (IOException e) {
			if (file.exists()) {
				outputUnit.cache.put(sourceFile, outputUnit);
			} else {
				System.out.println("File was deleted while copying: '" + file.getAbsolutePath() + "'");
			}
		} finally {
			if (raf != null) {
				raf.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T> WatchEvent<T> cast(WatchEvent<?> event) {
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
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                     
					for (WatchEvent<?> event : take.pollEvents()) {
						WatchEvent.Kind kind = event.kind();
						if (kind == OVERFLOW) {
							continue;
						}
						Path source =(Path)take.watchable();
						WatchEvent<Path> ev = cast(event);
						Path file = ev.context();
						String fileName = file.getFileName().toString();
						System.out.format("%s: %s\n", event.kind().name(), fileName);
						
						if (kind == ENTRY_CREATE) {
							List<OutputUnit> outputUnits = hashCache.get(take);
							boolean found = false;
							for (OutputUnit outputUnit : outputUnits) {
								if (fileName.matches(outputUnit.regex)) {
									outputUnit.cache.put(source.toString().concat("/").concat(fileName), outputUnit);
									found = true;
								}
							}
							if (!found) {
								System.out.println("no regx found for" + fileName);
							}
						}
						if (kind == ENTRY_MODIFY) {
							cache.get(source.toString().concat("/").concat(fileName));
						}
					}
					take.reset();
				}
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}