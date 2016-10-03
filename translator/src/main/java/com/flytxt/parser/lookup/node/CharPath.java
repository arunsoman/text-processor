package com.flytxt.parser.lookup.node;

public class CharPath<T> {
	public final byte[] key;
	public final int charPos;
	public final T value;
	
	public CharPath(byte[] key, int charPos, T value) {
		super();
		this.key = key;
		this.charPos = charPos;
		this.value = value;
	}
	
	public int match(byte[] search){
		if(search.length > key.length - charPos){
			return -1;
		}
		int matchCnt = 0;
		for(int i = 0; i < search.length; i++){
			if(key[charPos+1] == search[i])
				matchCnt++;
			else{
				return matchCnt;
			}
		}
		return matchCnt;
	}
}
