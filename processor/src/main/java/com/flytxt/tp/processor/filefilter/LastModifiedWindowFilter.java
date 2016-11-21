package com.flytxt.tp.processor.filefilter;

import java.io.File;

public class LastModifiedWindowFilter extends WindowFilter{

	@Override
	public File[] canProcess(File...files) {
			for(File aFile: files){
				if( (System.currentTimeMillis() -windowUnit)< aFile.lastModified())
					aFile = null;
			}
			if(nextLink != null)
				return nextLink.canProcess(files);
			return files;
		}

}
