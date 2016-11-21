package com.flytxt.tp.processor.filefilter;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FifoFilter extends FilterChain{

	@Override
	public File[] canProcess(File... files) {
		Collections.sort(Arrays.asList(files), new Comparator<File>(){

			@Override
			public int compare(File o1, File o2) {
				return ((int)(o1.lastModified()-o2.lastModified()));
			}});
		return null;
	}

}
