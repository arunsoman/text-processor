package com.flytxt.parser.lookup.node;

import java.util.ArrayList;
import java.util.List;

public class CharacterNode<T> {
	public final Character aChar;
	private final List<CharPath<T>> paths = new ArrayList<CharPath<T>>();
	
	public CharacterNode(Character aChar) {
		super();
		this.aChar = aChar;
	}
	public void addPath(CharPath<T> charPath){
		paths.add(charPath);
	}
	public T getValue(byte[] search){
		for(CharPath<T> aPath: paths){
			if(aPath.match(search)> 0)
				return aPath.value;
		}
		return null;
	}
	
}
