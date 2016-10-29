package com.flytxt.parser.store;

public class NeonStore extends LocalFileStore {

    public void set(String folderName, String fileName, String... headers) {
        super.set(folderName, fileName, headers);
    }
}
