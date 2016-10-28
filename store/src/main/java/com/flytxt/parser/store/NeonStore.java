package com.flytxt.parser.store;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Qualifier("neonStore")

public class NeonStore extends LocalFileStore {

    public void set(String folderName, String fileName, String... headers) {
        super.set(folderName, fileName, headers);
    }
}
