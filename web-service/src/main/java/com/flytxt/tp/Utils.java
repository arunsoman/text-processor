package com.flytxt.tp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public String readLptemplate(String name) throws IOException, URISyntaxException {
        URI uri = getClass().getClassLoader().getResource(name).toURI();
        String str = new String(Files.readAllBytes(Paths.get(uri)));
        return str;
    }

    public static String replaceWithConsoleStore(String absProcessor) {
        Pattern p = Pattern.compile("(new\\s)+(.+)(Store\\()", Pattern.MULTILINE);
        Matcher m = p.matcher(absProcessor);
        if (m.find())
            return m.replaceAll("$1 Console$3");
        return null;
    }
}
