package com.flytxt.parser.marker;

import java.io.IOException;

public interface LineProcessor {

    String getSourceFolder();

    void init(String currentFileName);

    void process() throws IOException;

    String done() throws IOException;

    String getFilter();

    MarkerFactory getMf();
}
