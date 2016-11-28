package com.flytxt.tp.fileutils.loader;

import java.io.BufferedReader;
import java.io.IOException;

import com.flytxt.tp.fileutils.DiskChannel;
import com.flytxt.tp.lookup.Lookup;
import com.flytxt.tp.marker.MarkerFactory;

public class FileLoader implements Loader {
	private String fileName;
	private MarkerFactory mf;
	
    public FileLoader(String fileName, MarkerFactory mf) {
    		this.fileName = fileName;
    		this.mf = mf;
    }
    
    @Override
    public void load(Lookup lookup) {
        try(final BufferedReader bufferedReader = 
        		new DiskChannel().open(fileName);){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String key = line.substring(0, line.indexOf('|'));
                final String value = line.substring(key.length()+1, line.length());
                final byte[] valueByteArray = value.getBytes();

                if (!key.isEmpty())
                	lookup.load(key.getBytes(), mf.createMarker(valueByteArray, 0, valueByteArray.length));
            }
        } catch (final IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
	}
}
