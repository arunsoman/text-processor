package com.flytxt.parser.store;

import java.io.IOException;

import com.flytxt.parser.marker.Marker;

public class ConsoleStore implements Store {

    private String[] headers;

    public final static String TMP = ".tmp";

    private StringBuilder array = new StringBuilder("[");

    public ConsoleStore(String outputFolder, String... headers) {
        this.headers = headers;
    }

    @Override
    public void save(final byte[] data, String fileName, final Marker... markers) throws IOException {
        StringBuilder currentProperty = new StringBuilder();
        if (headers.length == markers.length) {
            currentProperty.append("{");

            for (int i = 0; i < markers.length; i++) {
                String str = markers[i].toString();
                if (str == null || str.length() == 0)
                    str = "null";
                else
                    str = "\"" + str + "\"";
                currentProperty.append("\"").append(headers[i]).append("\":").append(str).append(",");
            }
            if (currentProperty.length() > 0)
                currentProperty.deleteCharAt(currentProperty.length() - 1);
            currentProperty.append("},");
        } else {
            currentProperty.append("{");
            for (int i = 0; i < markers.length; i++) {
                String str = markers[i].toString();
                if (str == null || str.length() == 0)
                    str = "null";
                else
                    str = "\"" + str + "\"";
                currentProperty.append("\"Key").append(i).append("\":").append(str).append(",");
            }
            if (currentProperty.length() > 0)
                currentProperty.deleteCharAt(currentProperty.length() - 1);
            currentProperty.append("},");
        }
        array.append(currentProperty.toString());
    }

    @Override
    public String done() throws IOException {
        if (array.length() > 0)
            array.deleteCharAt(array.length() - 1);
        array.append("]");
        System.out.println(array.toString());
        return array.toString();
    }

    @Override
    public void set(String fileName) {

    }
}