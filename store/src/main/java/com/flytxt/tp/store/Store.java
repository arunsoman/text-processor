package com.flytxt.tp.store;

import java.io.IOException;

import com.flytxt.tp.marker.Marker;

public interface Store {

    public void set(final String fileName);

    public void save(final byte[] data,   String fileName, final Marker... markers) throws IOException;

    public String done() throws IOException;
    
    void preDestroy();
}