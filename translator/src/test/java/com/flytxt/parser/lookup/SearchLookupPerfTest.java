package com.flytxt.parser.lookup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class SearchLookupPerfTest {

    class Datum {

        String key;

        String value;

        public Datum(final String key, final String value) {
            super();
            this.key = key;
            this.value = value;
        }
    }

    // @Test
    // public void perfTest() {
    //
    // final Search<String> search = new Search<>();
    // final ArrayList<Datum> data = new ArrayList<>();
    // try {
    // final ClassLoader classLoader = getClass().getClassLoader();
    //
    // final File file = new File(classLoader.getResource("searchdata.csv").getFile());
    // final FileReader fileReader = new FileReader(file);
    // final BufferedReader bufferedReader = new BufferedReader(fileReader);
    // String line;
    // while ((line = bufferedReader.readLine()) != null) {
    // final String[] tt = line.split(",");
    // if (tt.length == 2) {
    // if (tt[0].trim().length() > 0) {
    // data.add(new Datum(tt[0].trim().getBytes(), tt[1]));
    // }
    // }
    // }
    // fileReader.close();
    // } catch (final IOException e) {
    // e.printStackTrace();
    // }
    //
    // long start = System.currentTimeMillis();
    // for (final Datum d : data) {
    // search.load(d.key, d.value);
    // }
    // final long end = System.currentTimeMillis();
    // System.out.println("total time millis:" + (end - start) + " count: " + data.size() + " inserts/sec:" + (data.size() * 1000 / (end - start)));
    //
    // long counter = 0;
    // start = System.currentTimeMillis();
    // for (final Datum d : data) {
    // search.get(d.key);
    // if (counter++ % 10000 == 0) {
    // System.out.println(System.currentTimeMillis() - start);
    // start = System.currentTimeMillis();
    // }
    //
    // }
    // // end = System.currentTimeMillis();
    // // System.out.println("total time:" + (end - start) + " count: " + data.size() + " gets/sec:" + (data.size() * 1000 / (end - start)));
    //
    // }

    @Test
    public void perfTest2() {

        final PrefixLookup<String> search = new PrefixLookup<>();
        final ArrayList<Datum> data = new ArrayList<>();
        try {
            final ClassLoader classLoader = getClass().getClassLoader();

            final File file = new File(classLoader.getResource("ndclist.csv").getFile());
            final FileReader fileReader = new FileReader(file);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String key = line.substring(0, line.indexOf('|'));
                final String value = line.substring(key.length(), line.length() - 1);
                if (!key.isEmpty()) {
                    data.add(new Datum(key, value));
                }
            }
            fileReader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        long start = System.nanoTime();
        for (final Datum d : data) {
            search.load(d.key, d.value);
        }
        long end = System.nanoTime();
        System.out.println("total time nano:" + (end - start) + " count: " + data.size());

        search.bake();
        start = System.nanoTime();
        for (final Datum d : data) {
            search.get(d.key);
            // System.out.println(search.get(d.key));
        }
        end = System.nanoTime();
        System.out.println("total time nano:" + (end - start) + " count: " + data.size());

    }

}
