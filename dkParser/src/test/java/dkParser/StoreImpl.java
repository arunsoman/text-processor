package dkParser;

import com.flytxt.parser.dkParser.dk.Load;
import com.flytxt.parser.marker.FlyList;
import com.flytxt.parser.marker.Marker;

public class StoreImpl implements Load {

	@Override
	public void process(byte[] line, FlyList<Marker> markers) {

		for (int i = 1; i < markers.size(); i++) {
			System.out.println(
					markers.get(i).toString(markers.get(i).getData() == null ? line : markers.get(i).getData()));
		}
		// TODO Auto-generated method stub

	}

}
