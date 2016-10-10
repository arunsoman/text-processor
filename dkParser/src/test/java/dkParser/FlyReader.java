package dkParser;

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

import com.flytxt.parser.dkParser.dk.Extract;
import com.flytxt.parser.dkParser.dk.Load;
import com.flytxt.parser.dkParser.dk.Transform;
import com.flytxt.parser.marker.FlyList;
import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

import lombok.Getter;


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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void set(final String folder, final LineProcessor lp) {
        this.lp = lp;
        this.folder = folder;
        logger.debug("file reader @ " + folder);
    }

    public void run() {
        final Path folderP = Paths.get(folder);
        if (!Files.exists(folderP)) {
            try {
                Files.createDirectories(folderP);
            } catch (final IOException e1) {
                logger.info("could not create input folder, stopping this FlyReader ", e1);
                stopRequested = true;
            }
        }
        logger.debug("Starting file reader @ " + folder);
        final ByteBuffer buf = ByteBuffer.allocate(51200);
        final MarkerFactory mf = new MarkerFactory();
        mf.setMaxListSize(1000);
        while (!stopRequested) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(folder))) {
                for (final Path path : directoryStream) {
                    final RandomAccessFile file = new RandomAccessFile(path.toString(), "rw");
                    logger.debug("picked up " + path.toString());
                    try {
                       /* lp.setInputFileName(path.getFileName().toString());*/
                        processFile(buf, path, file.getChannel(), mf);
                        buf.clear();
                        if (stopRequested) {
                            logger.debug("shutting down Worker @ :" + folder);
                            break;
                        }
                    } catch (final OverlappingFileLockException e) {
                        logger.error("Could not process " + path.toString(), e);
                    } finally {
                        file.close();
                    }
                }
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        logger.debug("Worker down " + folder);
    }

    private void processFile(final ByteBuffer buf, final Path path, final FileChannel file, final MarkerFactory mf) throws IOException {
        final long t1 = System.currentTimeMillis();
        readLines(file, buf, mf);
        /*lp.done();*/
        file.close();
        Files.delete(path);
        logger.debug("total time taken: " + (System.currentTimeMillis() - t1));
        mf.printStat();
    }

    private final void readLines(final FileChannel file, final ByteBuffer buf, final MarkerFactory mf) throws IOException {
        int readCnt;
        Extract extract = new ExtractImpl();
        Transform transform = new TransformImpl();
        Load load = new StoreImpl();
        final byte[] data = buf.array();
        while ((readCnt = file.read(buf)) > 0) {
            long eolPosition;
            long previousEolPosition = 0;
            {
                do {
                    eolPosition = getEOLPosition(data, (int) previousEolPosition + eol.length, readCnt);
                    if (eolPosition < 0) {
                        if (previousEolPosition == 0) {
                            logger.error("Increase byte array size, current size :" + data.length);
                            throw new IOException("can't process " + readCnt + " long line");
                        }
                        readLines(file.position(file.position() - (readCnt - previousEolPosition - eol.length)), (ByteBuffer) buf.clear(), mf);
                        continue;
                    } else {
                        try {
                        	FlyList<Marker> extractMarkers = extract.process(data,"");
                        	FlyList<Marker> transformMarkers = transform.process(data, extractMarkers);
                        	load.process(data, transformMarkers);
                        	
                            // final long T1 = System.nanoTime();
                            /*lp.process(data, previousEolPosition == 0 ? 0 : (int) previousEolPosition + eol.length, (int) eolPosition, mf);*/
                            // logger.debug("Total: " + (System.nanoTime() - T1));
                            mf.reclaim();
                            previousEolPosition = eolPosition;
                        } catch (final IndexOutOfBoundsException e) {
                            logger.debug("could not process : " + new String(data, 0, (int) eolPosition) + " \n cause:" + e.getMessage());
                        }
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
        logger.debug("check " + folderName + " & " + lp.getFolder());
        if (lp.getFolder().equals(folderName)) {
            final String regex = lp.getFilter();
            if (regex == null) {
                return true;
            }
            final Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(fileName).find();
        } else {
            return false;
        }
    }

    public long getEOLPosition(final byte[] data, final int startIndex, final int endIndex) {
        try {
            int tokenIndex, currentIndex = startIndex;
            while (currentIndex <= endIndex) {
                for (tokenIndex = 0; tokenIndex < eol.length && eol[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++) { // loop to check if EOL is present at position currentIndex
                    ;
                }
                if (tokenIndex == eol.length) {
                    return currentIndex;
                }
                currentIndex++;
            }
        } catch (final Exception e) {
        }
        return -1;
    }
    
    public static void main(String[] args) {
    	
    	FlyReader flyReader =new FlyReader();
    	flyReader.set("/home/gomathy/Desktop/Demo_dk_ui/INMTR/Input/", null);
		flyReader.run();
	}
}