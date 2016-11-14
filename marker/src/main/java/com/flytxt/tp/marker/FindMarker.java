package com.flytxt.tp.marker;

public class FindMarker {
	
	public int findPreMarker(byte[] token, int from, int end, byte[] data){
		int counter = 0;
		for(int j =from; j <= end; j++){
			for(int i = 0;i< token.length;i++){
				if(data[j] == token[i]){
					counter++;
					j++;
				}
				else{
					counter = 0;
					break;
				}
			}
			if(counter == token.length)
				return j;
		}
		return -1;
	}

	public int findPreMarker(byte token, int from, int end, byte[] data){
		try{
			int j = from;
			while( data[j] != token && j <= end){
				j++;
			}
			if(j == end && data[j] != token){
				return -1;
			}
		return j;
		}catch(ArrayIndexOutOfBoundsException e){
			return -1;
		}
	}
	/*
	public int findNthPreMarker(int n, byte token[], int from, int end, byte[] data){
		int stage = from;
		int i = 0;
		for(; i < n; i++){
			stage = findPreMarker(token, stage, end, data);
			if(stage == -1)
				return -1;
		}
		if(i == n) return stage; else return -1;
	}
	public int findNthPreMarker(int n, byte token, int from, int end, byte[] data){
		int stage = from;
		int i = 0;
		for(; i < n; i++){
			stage = findPreMarker(token, stage, end, data);
			if(stage == -1)
				return -1;
		}
		if(i == n) return stage; else return -1;
	}
	
	*/
}
