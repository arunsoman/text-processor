package com.flytxt.tp.processor;


import java.io.File;
import java.io.IOException;

import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.processor.LineProcessor;

public class LineProcessorImpl implements LineProcessor{
	
	public final MarkerFactory mf = new MarkerFactory();
	
	public static final String SOURCE_PTH = "src"+File.separator+"test"+File.separator+"resources"+File.separator+"data";
	
	@Override
	public String getSourceFolder() {

		return SOURCE_PTH;
	}

	@Override
	public void init(String currentFileName, long lastModifiedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String done() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFilter() {
		return null;
	}

	@Override
	public MarkerFactory getMf() {
		return mf;
	}

	@Override
	public void preDestroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
