package com.flytxt.parser.store;

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

import com.flytxt.parser.marker.Marker;

public class LocalFileStore implements Store {

    private RandomAccessFile channel;

    private final byte csv = (byte) ',';

    private final byte[] newLine = System.lineSeparator().getBytes();

    private int status;

    private final Path filePath;

    private final String[] headers;

    private final ByteBuffer bBuff = ByteBuffer.allocateDirect(6144);

    private final ByteBuffer headBuff = ByteBuffer.allocateDirect(100);

    private IOException e;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public final static String TMP = ".tmp";

    public LocalFileStore(final String fileName, final String... headers) {
        this.filePath = Paths.get(fileName);
        this.headers = headers;
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
        final Path folder = filePath.getParent();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder, filter)) {
            for (final Path entry : stream) {
                entry.toFile().delete();
            }
        } catch (final IOException ex) {

        }
    }

    private void createFile() {
        if (!Files.exists(filePath)) {
            final Path folder = filePath.getParent();
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
        final Path fileNameP = Paths.get(name);
        try {
            channel = new RandomAccessFile(fileNameP.toString(), "rw");

            for (final String aheader : headers) {
                headBuff.put(aheader.getBytes());
                headBuff.put(csv);
            }
            headBuff.put(newLine);
            headBuff.flip();
            channel.getChannel().write(headBuff);
            headBuff.clear();
            logger.debug("file created @ " + fileNameP.toString());
        } catch (final IOException e) {
            logger.debug("could not create file @ " + fileNameP.toString(), e);
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
        if (status == -1) {
            throw e;
        }
        try {
            int delta = fileName.getBytes().length;
            bBuff.put(fileName.getBytes());
            for (final Marker aMarker : markers) {
                bBuff.put(csv);
                bBuff.put(data, aMarker.index, aMarker.length);
                delta += aMarker.length;
            }
            delta *= 2;
            bBuff.put(newLine);
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
        final String[] tmp = filePath.getFileName().toString().split("\\.");
        final String doneFile = tmp[0] + System.currentTimeMillis() + "." + tmp[1];
        Files.move(Paths.get(filePath.toAbsolutePath() + TMP), Paths.get(filePath.getParent() + "/" + doneFile));
        return null;
    }

    @Override
    public void set(final String fileName, final String... headers) {
        // TODO Auto-generated method stub

    }
}