package com.flytxt.tp.lookup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.flytxt.tp.fileutils.Channel;
import com.flytxt.tp.fileutils.ClasspathChannel;
import com.flytxt.tp.fileutils.loader.FileLoader;
import com.flytxt.tp.marker.MarkerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchLookupPerfTest {

//    @Test
    public void perfTest() {
        class Datum {

            byte[] key;

            String value;

            public Datum(byte[] key, String value) {
                super();
                this.key = key;
                this.value = value;
            }
        }
        MarkerFactory mf = new MarkerFactory();
        Search<String> search = new Search<>(mf);
        FileLoader fLoader = new FileLoader("searchdata.csv", mf);
        fLoader.load(search);
        ArrayList<Datum> data = new ArrayList<>();
        try (Channel cpC = new ClasspathChannel()){
        		BufferedReader bufferedReader = cpC.open("");
        	String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tt = line.split(",");
                if (tt.length == 2)
                    if (tt[0].trim().length() > 0)
                        data.add(new Datum(tt[0].trim().getBytes(), tt[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long start = System.nanoTime();
        for (Datum d : data)
            search.load(d.key, d.value);
        long end = System.nanoTime();
        log.info("total time nano:" + (end - start) + " count: " + data.size() + " inserts/sec:" + ((end - start) / data.size()));

        start = System.currentTimeMillis();
        @SuppressWarnings("unused")
        String str;
        for (Datum d : data)
            str = search.get(d.key);
        end = System.currentTimeMillis();
        log.info("total time:" + (end - start) + " count: " + data.size() + " gets/sec:" + ((end - start) / data.size()));

    }
}
