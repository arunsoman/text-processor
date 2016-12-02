package com.flytxt.tp.processor;

import java.io.IOException;

import com.flytxt.tp.marker.MarkerFactory;

public interface LineProcessor {

    String getSourceFolder();

    void init(String currentFileName, long lastModifiedTime);

    void process() throws Exception;

    String done() throws IOException;

    String getFilter();

    MarkerFactory getMf();
    
    void preDestroy()throws Exception;
}
