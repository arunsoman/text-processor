package dkParser;

import com.flytxt.parser.dkParser.dk.Extract;
import com.flytxt.parser.marker.FlyList;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.marker.TokenFactory;

public class ExtractImpl extends VariablesImpl implements Extract {

	@Override
	private MarkerFactory mf;

	@Override
	private TokenFactory tf;

	private byte[] pipe = tf.create("|");

	private byte[] bucket = tf.create("[1~");

	private byte[] tilde = tf.create("~");

	// 'line' common && hard-coded
	@Override
	public FlyList<Marker> process(byte[] line, String fileName) {
		mf.setMaxListSize(1000); //no of extraction set
		FlyList<Marker> markers = mf.create(0, line.length).splitAndGetMarkers(line, pipe, mf);
		a = markers.get(1);
		b = markers.get(3);
		c = markers.get(8);
		FlyList<Marker> mList1 = c.splitAndGetMarkers(line, bucket, mf);
		Marker mrp = mList1.get(2).splitAndGetMarker(line, tilde, 3, mf);
		FlyList<Marker> result = mf.getArrayList();
		result.add(a);
		result.add(b);
		result.add(c);
		// TODO Auto-generated method stub
		return result;
	}

}
