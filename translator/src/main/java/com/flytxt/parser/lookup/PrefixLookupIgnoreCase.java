package com.flytxt.parser.lookup;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.collections4.trie.UnmodifiableTrie;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.translator.TpString;

public class PrefixLookupIgnoreCase extends Lookup {
	private Map<byte[], Marker> map = new HashMap<byte[], Marker>();
	private UnmodifiableTrie<byte[], Marker> fMap;
	private MarkerFactory mf = new MarkerFactory();
	private TpString tpString = new TpString();
	private byte capsA = 'A';
	private byte capsZ = 'Z';
	
	private byte[] toLower(byte[] data1){
		final byte[] dest = new byte[data1.length];
        int index = 0;
        for (int i = 0; i < data1.length; i++) {
            if (data1[i] >= capsA && data1[i] <= capsZ) {
                dest[index++] = (byte) (data1[i] - 'A'-'a');
            } else {
                dest[index++] = data1[i];
            }
        }
        return dest;
	}
	public void load(byte[]key, byte[]val){
		map.put(toLower(key), mf.createImmutable(val, 0, val.length));
	}
	
	public void bake(){
		fMap = new UnmodifiableTrie<byte[], Marker>(new PatriciaTrie(map));
	}
	
	public Marker get(byte[]key){
		return fMap.get(toLower(key));
	}
}
