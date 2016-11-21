package com.flytxt.tp.processor;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flytxt.tp.marker.CurrentObject;

import lombok.Getter;


public class FlyReader implements Callable<FlyReader> {

    private LineProcessor lp;

    private boolean stopRequested;

    public enum Status {
        RUNNING, TERMINATED, SHUTTINGDOWN
    }

    @Getter
    private Status status;

    byte[] eol = "\n".getBytes();// System.lineSeparator().getBytes();
    final ByteBuffer buf = ByteBuffer.allocate(51200);

    private final Logger appLog = LoggerFactory.getLogger("applicationLog");

    private final Logger transLog = LoggerFactory.getLogger("transactionLog");

    public void set(final String folder, final LineProcessor lp) {
        this.lp = lp;
        appLog.debug("file reader @ " + folder);
    }

    public void run() {
    	String folder = lp.getSourceFolder();
    	assert folder != null;
    	
        final Path folderP = Paths.get(lp.getSourceFolder());
        assert Files.exists(folderP);
        
        while (!stopRequested) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(folder))) {
                for (final Path path : directoryStream) {
                	try{
                		appLog.debug("picked up " + path.toString());
                		buf.clear();
                        String fileName = path.getFileName().toString();
                        lp.getMf().getCurrentObject().init(folder, fileName);
                        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                        lp.init(fileName, attr.lastModifiedTime().toMillis());
                        processFile(path);
                        Files.delete(path);
                        if (stopRequested) {
                            appLog.debug("shutting down Worker @ :" + folder);
                            break;
                        }
                    } catch (final OverlappingFileLockException e) {
                        appLog.error("Could not process " + path.toString(), e);
                    }
                }
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        appLog.debug("Worker down " + lp.getSourceFolder());
    }

    private void processFile(final Path path) throws Exception {
        final long t1 = System.currentTimeMillis();
        final long fileSize = Files.size(path);
        final String inputFile = path.toString();
        try(final RandomAccessFile file = new RandomAccessFile(inputFile, "rw")){
        	readLines(file.getChannel(), buf);
        	lp.done();
        	file.close();        
        }catch (Exception e) {
			throw e;
		}
        final long totalTimeTaken = System.currentTimeMillis() - t1;
        appLog.debug("total time taken: " + totalTimeTaken);
        transLog.debug("{},{},{}", inputFile, fileSize, totalTimeTaken);
    }

    private final void readLines(final FileChannel file, final ByteBuffer buf)  throws Exception {
        CurrentObject currentObject = lp.getMf().getCurrentObject();
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
                            currentObject.setCurrentLine(data, previousEolPosition == 0 ? 0 : (int) previousEolPosition + eol.length, (int) (eolPosition - previousEolPosition));
                            lp.process();
                            previousEolPosition = eolPosition;
                        } catch (final IndexOutOfBoundsException e) {
                            appLog.debug("could not process : " + new String(data, 0, (int) eolPosition) + " \n cause:", e);
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
