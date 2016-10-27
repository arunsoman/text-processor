package com.flytxt.parser.store;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.flytxt.parser.marker.Marker;

import gherkin.deps.com.google.gson.JsonArray;
import gherkin.deps.com.google.gson.JsonObject;
@Component
@Qualifier("consoleStore")
public class ConsoleStore implements Store {

    private String[] headers;

    public final static String TMP = ".tmp";

    JsonArray array = new JsonArray();

    public ConsoleStore(){}
    
    public void set(String fileName, String folderName,	String... headers) {
    
        this.headers = headers;
    }

    @Override
    public void save(final byte[] data, final String fileName, final Marker... markers) throws IOException {
        JsonObject object = new JsonObject();
        for (int i = 0; i < markers.length; i++)
            object.addProperty(headers[i], markers[i].toString());
        array.add(object);
    }

    @Override
    public String done() throws IOException {
        return array.toString();
    }

}