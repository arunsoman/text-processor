package com.flytxt.parser.marker;

import java.io.IOException;

public interface LineProcessor {

    String getSourceFolder();

    void init(String currentFileName, MarkerFactory mf);

    void process() throws IOException;

    String done() throws IOException;

    String getFilter();

    int getMaxListSize();

    MarkerFactory getMf();
}
