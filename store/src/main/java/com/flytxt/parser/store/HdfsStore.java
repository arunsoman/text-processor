package com.flytxt.parser.store;

public class HdfsStore extends LocalFileStore {

	public void set(String folderName, String fileName, String... headers) {
        super.set(folderName, fileName, headers);
    }
}