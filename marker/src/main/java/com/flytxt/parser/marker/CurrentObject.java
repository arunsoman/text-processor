package com.flytxt.parser.marker;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Scope("prototype")
@Data
public final class CurrentObject {
	private String folderName;
	private String fileName;
	private byte[] lineMarker;
	private int index;
	private int length;
	
	public void setCurrentLine(int index, int length){
		this.index = index;
		this.length = length;
	}
}
