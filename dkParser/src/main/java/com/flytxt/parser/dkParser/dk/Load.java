package com.flytxt.parser.dkParser.dk;

import com.flytxt.parser.marker.FlyList;
import com.flytxt.parser.marker.Marker;

public interface Load {
	
	void process(byte[] line , FlyList<Marker> markers);

}
