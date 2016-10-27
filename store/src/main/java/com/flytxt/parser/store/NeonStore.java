package com.flytxt.parser.store;

public class NeonStore extends LocalFileStore {

    public NeonStore(String folderName, String... headers) {
        super(folderName, headers);
    }
}