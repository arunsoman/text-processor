package com.flytxt.tp.store;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flytxt.tp.marker.Marker;

public class LocalFileStore implements Store {

    private static final byte COMMA = (byte) ',';

    private static final byte[] NEWLINE = System.lineSeparator().getBytes();

    public final static String TMP = ".tmp";

    private final ByteBuffer bBuff = ByteBuffer.allocateDirect(6144);

    private final ByteBuffer headBuff = ByteBuffer.allocateDirect(100);

    private final Logger logger = LoggerFactory.getLogger("applicationLog");

    public final String folderName; // Destination folder

    public final String[] headers; // Headers of the output file

    private IOException e;

    private int status;

    private RandomAccessFile channel;

    private Path filePath; // Destination file's absolute path

    public LocalFileStore(String folderName, String... headers) {
        this.folderName = folderName.endsWith("/") ? folderName : folderName + "/";
        this.headers = headers;
    }

    @Override
    public void set(final String fileName) {
        this.filePath = Paths.get(folderName + fileName);
        deleteTempFile();
        createFile();
    }

    private void deleteTempFile() {
        final Filter<Path> filter = new Filter<Path>() {

            @Override
            public boolean accept(final Path entry) throws IOException {
                return entry.toFile().toString().endsWith(TMP);
            }
        };
        final Path folder = Paths.get(folderName);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder, filter)) {
            for (final Path entry : stream)
                entry.toFile().delete();
        } catch (final IOException ex) {

        }
    }

    private void createFile() {
        if (!Files.exists(filePath)) {
            final Path folder = Paths.get(folderName);
            try {
                Files.createDirectories(folder);
            } catch (final IOException e) {
                status = -1;
                this.e = e;
                logger.debug("could not create folder @ " + folder.toString());
                return;
            }
        }
        final String name = filePath + TMP;
        final Path tempFile = Paths.get(name);
        try {
            channel = new RandomAccessFile(tempFile.toString(), "rw");

            for (final String aheader : headers) {
                headBuff.put(aheader.getBytes());
                headBuff.put(COMMA);
            }
            headBuff.put(NEWLINE);
            headBuff.flip();
            channel.getChannel().write(headBuff);
            headBuff.clear();
            logger.debug("file created @ " + tempFile.toString());
        } catch (final IOException e) {
            logger.debug("could not create file @ " + tempFile.toString(), e);
            status = -1;
            this.e = e;
        }
        // catch (final BufferOverflowException e) {
        // logger.error("Increase byte buffer size, current size :" + bBuff.capacity());
        // status = -1;
        // TODO handle this
        // }
    }

    @Override
    public void save(final byte[] data, final String fileName, final Marker... markers) throws IOException {
        if (status == -1)
            throw e;
        try {
            int delta = fileName.getBytes().length;
            bBuff.put(fileName.getBytes());
            for (final Marker aMarker : markers) {
                bBuff.put(COMMA);
                try {
                    bBuff.put(aMarker.getData(), aMarker.index, aMarker.length);
                    delta += aMarker.length;
                } catch (NullPointerException e) {
                    delta += 1;
                    continue;
                }
            }
            delta *= 2;
            bBuff.put(NEWLINE);
            if (bBuff.position() + delta > bBuff.limit()) {
                bBuff.flip();
                channel.getChannel().write(bBuff);
                bBuff.clear();
            }
        } catch (final IOException e) {
            createFile();
            save(data, fileName, markers);
        }
    }

    @Override
    public String done() throws IOException {

        if (bBuff.hasRemaining()) {
            bBuff.flip();
            try {
                channel.getChannel().write(bBuff);
            } catch (final IOException e) {
                createFile();
                channel.getChannel().write(bBuff);
            }
        }
        bBuff.clear();
        channel.close();
        final String doneFile = filePath.getFileName().toString() + "_" + System.currentTimeMillis();
        Files.move(Paths.get(filePath.toAbsolutePath() + TMP), Paths.get(folderName + "/" + doneFile));
        return null;
    }
}
