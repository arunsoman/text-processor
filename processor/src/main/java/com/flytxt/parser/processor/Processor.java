package com.flytxt.parser.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.flytxt.parser.marker.LineProcessor;

@Component
public class Processor {

    @Autowired
    private ProxyScripts proxy;

    @Autowired
    private ApplicationContext ctx;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final List<FlyReader> fileReaders = new ArrayList<>();

    private ThreadPoolExecutor executor;

    public void stopFileReads() {
        for (final FlyReader aReader : fileReaders) {
            aReader.stop();
        }
    }

    @PostConstruct
    public void startFileReaders() throws Exception {
        try {
            final List<LineProcessor> lpInstance = proxy.getLPInstance();
            executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(lpInstance.size());
            String folder;
            for (final LineProcessor lP : lpInstance) {
                final FlyReader reader = (FlyReader) ctx.getBean("flyReader");
                folder = lP.getFolder();
                reader.set(folder, lP);
                fileReaders.add(reader);
                executor.submit(reader);
            }

        } catch (final Exception e) {
            logger.error("can't start readers", e);
            throw e;
        }
    }

    public void handleEvent(final String folderName, final String fileName) {
        for (final FlyReader aReader : fileReaders) {
            if (aReader.canProcess(folderName, fileName) && aReader.getStatus() != FlyReader.Status.RUNNING) {
                executor.submit(aReader);
                break;
            }
        }
    }

    @PreDestroy
    public void init0() {
        for (final FlyReader aReader : fileReaders) {
            aReader.stop();
        }
        executor.shutdown();

    }
}
