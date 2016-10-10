package com.flytxt.parser.store;

import java.io.IOException;

import com.flytxt.parser.marker.Marker;

public class ConsoleStore implements Store {

    private String[] headers;

    public final static String TMP = ".tmp";

    private final StringBuilder result = new StringBuilder();

    @Override
    public void set(final String fileName, final String... headers) {
        this.headers = headers;

    }

    @Override
    public void save(final byte[] data, final String fileName, final Marker... markers) throws IOException {
        result.append('{');
        for (int i = 0; i < markers.length; i++)
            result.append("'").append(headers[i]).append("':'").append(markers[i].toString(markers[i].getData() == null ? data : markers[i].getData())).append("'\n");
        result.append('}');
    }

    @Override
    public String done() throws IOException {
        return result.toString();
    }
}