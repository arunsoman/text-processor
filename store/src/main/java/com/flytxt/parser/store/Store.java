package com.flytxt.parser.store;

import java.io.IOException;

import com.flytxt.parser.marker.Marker;

public interface Store {

    public void set(final String fileName, final String... headers);

    public void save(final byte[] data, final String fileName, final Marker... markers) throws IOException;

    public String done() throws IOException;
}
