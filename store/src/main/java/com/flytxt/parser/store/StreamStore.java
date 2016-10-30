package com.flytxt.parser.store;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.flytxt.parser.marker.Marker;
@Component
@Scope("prototype")
@Qualifier("streamStore")
public class StreamStore implements Store {

	@Override
	public void set(String folderName, String fileName, String ...headers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(byte[] data, String fileName, Marker... markers) throws IOException {
		// TODO Auto-generated method stub
		
	}

    @Override
	public String done() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
