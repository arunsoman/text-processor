package com.flytxt.parser.store;

import gherkin.deps.com.google.gson.JsonArray;
import gherkin.deps.com.google.gson.JsonObject;

import java.io.IOException;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.translator.TpConstant;

public class ConsoleStore implements Store {

    private String[] headers;

    public final static String TMP = ".tmp";

    private JsonArray array = new JsonArray();

    public ConsoleStore(String outputFolder, String... headers) {
        this.headers = headers;
    }

    @Override
    public void save(final byte[] data, String fileName, final Marker... markers) throws IOException {
        JsonObject object = new JsonObject();
        StringBuilder currentProperty = new StringBuilder();
        for (int i = 0, j = 0; i < markers.length; i++) {
            currentProperty.append(markers[i].toString(data)).append(',');
            if (i == markers.length - 1 || markers[i + 1].getData() == TpConstant.INTERDATATYPE.getData()) {
                currentProperty.deleteCharAt(currentProperty.length() - 1);
                object.addProperty(headers[j], currentProperty.toString());
                currentProperty.delete(0, currentProperty.length() - 1);
                j += 2;
            }
        }
        array.add(object);
    }

    @Override
    public String done() throws IOException {
        return array.toString();
    }

    @Override
    public void set(String fileName) {

    }
}