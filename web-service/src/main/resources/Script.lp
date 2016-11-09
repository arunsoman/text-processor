import com.flytxt.parser.marker.ImmutableMarker;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.lookup.Lookup;
import com.flytxt.parser.lookup.PrefixLookupIgnoreCase;
import com.flytxt.parser.processor.LineProcessor;
import com.flytxt.parser.store.ConsoleStore;
import com.flytxt.parser.store.HdfsStore;
import com.flytxt.parser.store.LocalFileStore;
import com.flytxt.parser.store.NeonStore;
import com.flytxt.parser.store.Store;
import com.flytxt.parser.store.StreamStore;
import com.flytxt.parser.translator.TpConstant;
import com.flytxt.parser.translator.TpDate;
import com.flytxt.parser.translator.TpLogic;
import com.flytxt.parser.translator.TpMath;
import com.flytxt.parser.translator.TpString;
import com.flytxt.realtime.commons.*;

import java.io.IOException;

public class %(name) implements LineProcessor{
	public final String outputFolder = "%(outputfolder)";
	public final String regex = "%(regex)";
	public final String inputFolder = "%(inputFolder)";
     private String str_inter = new String(TpConstant.INTERDATATYPE.getData());

	public final MarkerFactory mf = new MarkerFactory();

	private TpMath tpMath = new TpMath();
	private TpDate tpDate = new TpDate();
	private TpString tpString = new TpString();
	private TpLogic tpLogic = new TpLogic();

	private Marker m_inter = TpConstant.INTERDATATYPE;
     private Marker m_intra = TpConstant.INTRADATATYPE;
	private Marker line;
	private Marker mnull;
	private Marker fileName;
	private Marker mlastModifiedTime;
     private Marker mpickedTime;
     
	%(absProcessor)
	
	public %(name)(){
	%(init)
	line = mf.getLineMarker();
	mnull = mf.createMarker(" ".getBytes(), 0, 0);
	}
	
	@Override
     public void init(String fileNameStr,   long lastModifiedTime){
          byte[] b1 = String.valueOf(lastModifiedTime).getBytes();
          mlastModifiedTime = mf.createMarker(b1, 0 , b1.length);
          byte[] b2 = String.valueOf(System.currentTimeMillis()).getBytes();
          mpickedTime = mf.createMarker(b2, 0 , b2.length);
          byte[] tt = fileNameStr.getBytes();
          this.fileName = mf.createMarker(tt, 0, tt.length); 
           %(setStore)         
	}
	
	public String getSourceFolder(){
		return inputFolder;
	}
	     
	public void process() throws Exception {
		process(mf.getLineMarker().getData());
		translate(mf.getLineMarker().getData());
		store(mf.getLineMarker().getData());
		mf.reclaim();
	}

	public String done() throws IOException{
	    StringBuilder sb = new StringBuilder("[");
	    %(done)
	    sb.deleteCharAt(sb.length() - 1);
	    sb.append(']');
	    return sb.toString();
	}

	public String getFilter(){
	    return this.regex;
	}
	
	public MarkerFactory getMf() {
	    return mf;
	}
	
	public void process(final byte[] data) throws Exception {
		%(extract)
	}
	
	public void translate(final byte[] data) throws Exception {
		%(transformation)
	}
	
	public void store(final byte[] data) throws Exception {
		%(store)
	}
}