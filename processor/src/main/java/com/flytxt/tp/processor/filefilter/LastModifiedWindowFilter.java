package com.flytxt.tp.processor.filefilter;

import java.io.File;

public class LastModifiedWindowFilter extends WindowFilter{

	@Override
	public File[] canProcess(File...files) {
		int i = 0;
			for(File aFile: files){
				if( (System.currentTimeMillis() -windowUnit)< aFile.lastModified())
					files[i] = null;
				i++;
			}
			if(nextLink != null)
				return nextLink.canProcess(files);
			return files;
		}

}
