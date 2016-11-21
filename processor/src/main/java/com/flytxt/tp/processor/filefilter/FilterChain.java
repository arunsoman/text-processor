package com.flytxt.tp.processor.filefilter;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Setter;

public abstract class FilterChain {
	@Setter
	@Autowired
	protected FilterChain nextLink;
	
	public abstract File[] canProcess(File... files);
}
