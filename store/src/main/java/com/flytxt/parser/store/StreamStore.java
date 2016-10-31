package com.flytxt.parser.store;

import java.io.IOException;

import com.flytxt.parser.marker.Marker;


public class StreamStore implements Store {

    @Override
    public void set(String fileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(byte[] data, String fileName, Marker... markers) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public String done() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * private MappedBusWriter writer;
     *
     * private MarkerSerializer ms = new MarkerSerializer();
     *
     * public final static String TMP = ".tmp";
     *
     * public StreamStore(String file, String... headers) { writer = new MappedBusWriter(file, 10000, 15, true); try { writer.open(); } catch (IOException e) { return; } }
     *
     * @Override public void save(final byte[] data, String fileName, final Marker... markers) throws IOException { ms.set(data, markers); writer.write(ms); }
     *
     * @Override public String done() throws IOException { writer.close(); return null; }
     *
     * @Override public void set(String fileName) { }
     */
}
