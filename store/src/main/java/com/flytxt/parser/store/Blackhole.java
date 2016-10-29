package com.flytxt.parser.store;


import java.io.IOException;

import com.flytxt.parser.marker.Marker;


public class Blackhole implements Store {

    public void set(String folderName, String file, String... headers) {
    }

    @Override
    public void save(final byte[] data, String fileName, final Marker... markers) throws IOException {
    }

    @Override
    public String done() throws IOException {
        return null;
    }
}
