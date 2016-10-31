package com.flytxt.parser.store;

public class HdfsStore extends LocalFileStore {

    public HdfsStore(String folderName, String... headers) {
        super("/tmp/hdfs" + folderName, headers);
    }
}
