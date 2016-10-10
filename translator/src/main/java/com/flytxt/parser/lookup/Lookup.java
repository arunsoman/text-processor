package com.flytxt.parser.lookup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

@ComponentScan
public abstract class Lookup<T> {

    protected String fileName;

    @Autowired
    private MarkerFactory mf;

    @SuppressWarnings("unchecked")
    protected void loadFromFile() {

        try {
            final File file = new File(fileName);
            final FileReader fileReader = new FileReader(file);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String key = line.substring(0, line.indexOf('|'));
                final String value = line.substring(key.length(), line.length() - 1);
                final byte[] valueByteArray = value.getBytes();

                if (!key.isEmpty()) {
                    this.load(key.getBytes(), (T) mf.createImmutable(valueByteArray, 0, valueByteArray.length));
                }
            }
            fileReader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void load(final byte[] key, final T val);

    public abstract void bake();

    public abstract Marker get(byte[] bytes);
}
