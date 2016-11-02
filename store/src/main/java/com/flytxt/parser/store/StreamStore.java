package com.flytxt.parser.store;

import java.io.IOException;

import com.flytxt.commons.chronicle.ChronicleWriter;
import com.flytxt.parser.marker.Marker;
import com.flytxt.realtime.commons.RTEvent;

public class StreamStore implements Store {

    private RTEvent event = new RTEvent();

    private ChronicleWriter<RTEvent> cWriter;

    @Override
    public void set(String fileName) {
        cWriter = new ChronicleWriter<>(fileName);
    }

    @Override
    public void save(byte[] data, String fileName, Marker... markers) throws IOException {
        populateValues(markers);
        cWriter.write(event);
    }

    private void populateValues(Marker[] markers) {

    }

    @Override
    public String done() throws IOException {
        cWriter.destroy();
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
