package com.flytxt.tp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
    public String readLptemplate(String name) throws IOException, URISyntaxException{
        URI uri = getClass().getClassLoader().getResource(name).toURI();
        String str = new String(Files.readAllBytes(Paths.get(uri)));
        return str;
    }

}
