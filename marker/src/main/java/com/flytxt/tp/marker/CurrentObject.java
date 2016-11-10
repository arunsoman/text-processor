package com.flytxt.tp.marker;

import lombok.Getter;

public final class CurrentObject {
	@Getter private String folderName;
	@Getter private String fileName;
	@Getter private byte[] line;
	@Getter private int index;
	@Getter private int length;
	
	public void init(String folderName, String fileName){
		this.fileName = fileName;
		this.folderName = folderName;
	}
	public void setCurrentLine(byte[] data, int index, int length){
		this.line = data;
		this.index = index;
		this.length = length;
	}
}
