package com.flytxt.parser.dkParser.dk;

import com.flytxt.parser.marker.FlyList;
import com.flytxt.parser.marker.Marker;

public interface Extract {
	
	FlyList<Marker> process(byte[] line, String fileName);

}
