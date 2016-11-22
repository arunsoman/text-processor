package com.flytxt.tp.lookup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.flytxt.tp.marker.MarkerFactory;

public abstract class Lookup<T> {

    protected String fileName;
    protected MarkerFactory mf;

    @SuppressWarnings("unchecked")
    protected void loadFromFile() {

        try(final BufferedReader bufferedReader = 
        		new BufferedReader(new FileReader(new File(fileName)));){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String key = line.substring(0, line.indexOf('|'));
                final String value = line.substring(key.length(), line.length() - 1);
                final byte[] valueByteArray = value.getBytes();

                if (!key.isEmpty())
                    this.load(key.getBytes(), (T) mf.createMarker(valueByteArray, 0, valueByteArray.length));
            }
        } catch (final IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public abstract void load(final byte[] key, final T val);

    public abstract void bake();

    public abstract T get(byte[] bytes);
}
