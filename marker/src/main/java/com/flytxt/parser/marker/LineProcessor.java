package com.flytxt.parser.marker;

import java.io.IOException;

public interface LineProcessor {

    String getFolder();

    void setInputFileName(String currentFileName);

    void process(byte[] data, int startIndex, int readCnt, MarkerFactory mf) throws IOException;

    void done() throws IOException;

    String getFilter();

    int getMaxListSize();
}
