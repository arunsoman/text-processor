package com.flytxt.tp.processor.filefilter;

import java.io.File;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RegexFilter extends FilterChain{
	 private String regex;
	private Pattern pattern;
     
	public void setRegex(String regex){
		this.regex = regex;
		pattern = Pattern.compile(regex);
	}
	@Override
	public File[] canProcess(File...files ) {
		for(File aFile: files){
			if(pattern.matcher(aFile.getName()).matches())
				aFile = null;
		}
		if(nextLink != null)
			return nextLink.canProcess(files);
		return files;
	}
}
