package com.flytxt.parser.processor;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.flytxt.parser.marker.LineProcessor;

import lombok.Getter;

@Component
@Scope("prototype")
public class FlyReader implements Callable<FlyReader> {

    private String folder;

    private LineProcessor lp;

    private boolean stopRequested;

    public enum Status {
        RUNNING, TERMINATED, SHUTTINGDOWN
    }

    @Getter
    private Status status;

    byte[] eol = System.lineSeparator().getBytes();

    private final Logger appLog = LoggerFactory.getLogger("applicationLog");

    private final Logger transLog = LoggerFactory.getLogger("transactionLog");

    public void set(final String folder, final LineProcessor lp) {
        this.lp = lp;
        this.folder = folder;
        appLog.debug("file reader @ " + folder);
    }

    public void run() {
        final Path folderP = Paths.get(folder);
        if (!Files.exists(folderP))
            try {
                Files.createDirectories(folderP);
            } catch (final IOException e1) {
                appLog.info("could not create input folder, stopping this FlyReader ", e1);
                stopRequested = true;
            }
        appLog.debug("Starting file reader @ " + folder);
        final ByteBuffer buf = ByteBuffer.allocate(51200);

        while (!stopRequested)
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(folder))) {
                for (final Path path : directoryStream) {
                    final RandomAccessFile file = new RandomAccessFile(path.toString(), "rw");
                    appLog.debug("picked up " + path.toString());
                    try {
                        lp.init(path.getFileName().toString());
                        processFile(buf, path, file.getChannel());
                        buf.clear();
                        if (stopRequested) {
                            appLog.debug("shutting down Worker @ :" + folder);
                            break;
                        }
                    } catch (final OverlappingFileLockException e) {
                        appLog.error("Could not process " + path.toString(), e);
                    } finally {
                        file.close();
                    }
                }
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        appLog.debug("Worker down " + folder);
    }

    private void processFile(final ByteBuffer buf, final Path path, final FileChannel file) throws IOException {
        final long t1 = System.currentTimeMillis();
        final long fileSize = Files.size(path);
        final String inputFile = Files.readSymbolicLink(path).toString();
        readLines(file, buf);
        lp.done();
        file.close();
        Files.delete(path);
        final long totalTimeTaken = System.currentTimeMillis() - t1;
        appLog.debug("total time taken: " + totalTimeTaken);
        transLog.debug("{},{},{}", inputFile, fileSize, totalTimeTaken);
        // mf.printstat(); TODO
    }

    private final void readLines(final FileChannel file, final ByteBuffer buf) throws IOException {
        int readCnt;
        final byte[] data = buf.array();
        while ((readCnt = file.read(buf)) > 0) {
            long eolPosition;
            long previousEolPosition = 0;
            {
                do {
                    eolPosition = getEOLPosition(data, (int) previousEolPosition + eol.length, readCnt);
                    if (eolPosition < 0) {
                        if (previousEolPosition == 0) {
                            appLog.error("Increase byte array size, current size :" + data.length);
                            throw new IOException("can't process " + readCnt + " long line");
                        }
                        readLines(file.position(file.position() - (readCnt - previousEolPosition - eol.length)), (ByteBuffer) buf.clear());
                        continue;
                    } else
                        try {
                            // final long T1 = System.nanoTime();
                            lp.process(data, previousEolPosition == 0 ? 0 : (int) previousEolPosition + eol.length, (int) eolPosition);
                            // logger.debug("Total: " + (System.nanoTime() - T1));
                            previousEolPosition = eolPosition;
                        } catch (final IndexOutOfBoundsException e) {
                            appLog.debug("could not process : " + new String(data, 0, (int) eolPosition) + " \n cause:" + e.getMessage());
                        }
                } while (eolPosition > 0);
            }
        }
    }

    @PreDestroy
    public void stop() {
        stopRequested = true;
    }

    @Override
    public FlyReader call() throws Exception {
        run();
        return this;
    }

    public boolean canProcess(final String folderName, final String fileName) {
        appLog.debug("check " + folderName + " & " + lp.getSourceFolder());
        if (lp.getSourceFolder().equals(folderName)) {
            final String regex = lp.getFilter();
            if (regex == null)
                return true;
            final Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(fileName).find();
        } else
            return false;
    }

    public long getEOLPosition(final byte[] data, final int startIndex, final int endIndex) {
        try {
            int tokenIndex, currentIndex = startIndex;
            while (currentIndex <= endIndex) {
                for (tokenIndex = 0; tokenIndex < eol.length && eol[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++)
                    ;
                if (tokenIndex == eol.length)
                    return currentIndex;
                currentIndex++;
            }
        } catch (final Exception e) {
        }
        return -1;
    }
}
