package com.flytxt.parser.store;

import java.io.IOException;

import com.flytxt.parser.marker.Marker;
import com.github.arunsoman.ipc.mappedbus.MappedBusWriter;

public class StreamStore implements Store {

    private MappedBusWriter writer;

    private MarkerSerializer ms = new MarkerSerializer();

    public final static String TMP = ".tmp";

    public StreamStore(String file, String... headers) {
        writer = new MappedBusWriter(file, 100, 15, true);
        try {
            writer.open();
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void save(final byte[] data, final String fileName, final Marker... markers) throws IOException {
        ms.set(data, markers);
        writer.write(ms);
    }

    @Override
    public String done() throws IOException {
        writer.close();
        return null;
    }

    @Override
    public void set(String fileName) {
    }
}