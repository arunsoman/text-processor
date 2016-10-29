package com.flytxt.parser.store;

import java.io.IOException;

import com.flytxt.parser.marker.Marker;

public interface Store {

    public void set(final String folderName, final String fileName, String ...headers);

    public void save(final byte[] data,   String fileName, final Marker... markers) throws IOException;

    public String done() throws IOException;
}