package com.processor;

import com.flytxt.parser.marker.*;
import com.flytxt.parser.store.*;
import com.flytxt.parser.translator.*;

import java.io.IOException;

import com.flytxt.parser.lookup.*;
public class {0} implements LineProcessor{
	private String currentFileName;
	private Marker fileName;
	private String folderName;
	private MarkerFactory mf;
	//\n{1}
	
	public String getFolder(){
		return folderName;
	}
	public void setMarkerFactory(MarkerFactory mf){
		this.mf = mf;
	}
	public void setInputFileName(String currentFileName){
		this.currentFileName = currentFileName;
		byte[] tt = currentFileName.getBytes();
		this.fileName = mf.createMarker(tt, 0. tt.length);
	}

	public void process(byte[] data, int startIndex, int readCnt, MarkerFactory mf) throws IOException{
		process();
		translate();
		store();
	}

	public void done() throws IOException{
		//TODO
	}

	public String getFilter(){
		//TODO
		return null;
	}

	public int getMaxListSize(){
		//TODO
		return 10000;
	}
	public void process(){
		{2}
	}
	public void translate(){
		{3}
	}
	public void store(){
		{4}
	}
}
