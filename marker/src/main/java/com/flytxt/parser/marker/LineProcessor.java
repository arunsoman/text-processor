package com.flytxt.parser.marker;

import java.io.IOException;

public interface LineProcessor {

    String getSourceFolder();

    void init(String currentFileName);

    void process(byte[] data, int startIndex, int readCnt) throws IOException;

    String done() throws IOException;

    String getFilter();

    int getMaxListSize();
}
