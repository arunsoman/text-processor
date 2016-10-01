package dkParser;

import com.flytxt.parser.dkParser.dk.ExtractColumnMap;
import com.flytxt.parser.dkParser.dk.Transform;
import com.flytxt.parser.marker.FlyList;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.translator.*;

public class TransformImpl extends VariablesImpl implements Transform , ExtractColumnMap{

	private TpMath tpmath = new TpMath();
	
	private TpLogic tpLogic = new TpLogic();
	
	private TpString tpString = new TpString();

	private MarkerFactory mf = new MarkerFactory();

	@Override
	public FlyList<Marker> process(byte[] line, FlyList<Marker> markers) {
		
		Marker col1 = tpString.merge(line, a, line, b, mf);
		Marker cc1 = tpmath.abs(line, a, mf);
		boolean equalsCol1 = tpmath.eq(line, col1, line, cc1, mf);
		boolean equalsCol2 = tpmath.lessEqThan(line, col1, line, cc1, mf);
		boolean andBool = tpLogic.and(equalsCol1,equalsCol2);
		
		
		
		markers.add(cc1);

		// TODO Auto-generated method stub
		return markers;
	}
}
