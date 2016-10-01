package com.flytxt.parser.lookup;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.collections4.trie.UnmodifiableTrie;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class PrefixLookup extends Lookup {
	private Map<byte[], Marker> map = new HashMap<byte[], Marker>();
	private UnmodifiableTrie<byte[], Marker> fMap;
	private MarkerFactory mf = new MarkerFactory();
	
	public void load(byte[]key, byte[]val){
		map.put(key, mf.createImmutable(val, 0, val.length));
	}
	
	public void bake(){
		fMap = new UnmodifiableTrie<byte[], Marker>(new PatriciaTrie(map));
	}
	
	public Marker get(byte[]key){
		return fMap.get(key);
	}
}
