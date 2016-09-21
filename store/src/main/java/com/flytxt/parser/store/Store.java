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

public class Store {

    private RandomAccessFile channel;

    private final byte csv = (byte) ',';

    private final byte[] newLine = System.lineSeparator().getBytes();

    private int status;

    private final String fileName;

    private final String[] headers;

    private final ByteBuffer bBuff = ByteBuffer.allocateDirect(1024);

    private IOException e;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public final static String TMP = ".tmp";

    public Store(final String fileName, final String... headers) {
        this.fileName = fileName;
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
        final Path folder = Paths.get(fileName).getParent();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder, filter)) {
            for (final Path entry : stream) {
                entry.toFile().delete();
            }
        } catch (final IOException ex) {

        }
    }

    private void createFile() {
        Path fileNameP = Paths.get(fileName);
        if (!Files.exists(fileNameP)) {
            final Path folder = fileNameP.getParent();
            try {
                Files.createDirectories(folder);
            } catch (final IOException e) {
                status = -1;
                this.e = e;
                logger.debug("could not create folder @ " + folder.toString());
                return;
            }
        }
        final String name = fileName + TMP;
        fileNameP = Paths.get(name);
        try {
            channel = new RandomAccessFile(fileNameP.toString(), "rw");

            for (final String aheader : headers) {
                bBuff.put(aheader.getBytes());
                bBuff.put(csv);
            }
            bBuff.put(newLine);
            bBuff.flip();
            channel.getChannel().write(bBuff);
            bBuff.clear();
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

    public void save(final byte[] data, final String fileName, final Marker... markers) throws IOException {
        if (status == -1) {
            throw e;
        }
        try {
            bBuff.put(fileName.getBytes());
            for (final Marker aMarker : markers) {
                bBuff.put(csv);
                bBuff.put(data, aMarker.index, aMarker.length);
            }
            bBuff.put(newLine);
            bBuff.flip();
            channel.getChannel().write(bBuff);
            bBuff.clear();
        } catch (final IOException e) {
            createFile();
            save(data, fileName, markers);
        }
    }

    public void done() throws IOException {
        channel.close();
        final String[] tmp = fileName.split("\\.");
        final String doneFile = tmp[0] + System.currentTimeMillis() + "." + tmp[1];
        Files.move(Paths.get(fileName + TMP), Paths.get(doneFile));
    }
}
