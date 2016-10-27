package com.flytxt.parser.store;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.flytxt.parser.marker.Marker;

@Component
@Scope("prototype")
@Qualifier("blackHole")
public class Blackhole implements Store {

    public void set(String folderName, String file, String... headers) {
    }

    @Override
    public void save(final byte[] data, final String fileName, final Marker... markers) throws IOException {
    }

    @Override
    public String done() throws IOException {
    	return null;
    }
}
