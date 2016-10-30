package com.flytxt.parser.store;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
@Scope("prototype")
@Qualifier("hdfsStore")
public class HdfsStore extends LocalFileStore {

	public void set(String folderName, String fileName, String... headers) {
        super.set(folderName, fileName, headers);
    }
}