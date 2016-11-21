package test;
import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.lookup.Lookup;
import com.flytxt.tp.lookup.PrefixLookupIgnoreCase;
import com.flytxt.tp.processor.LineProcessor;
import com.flytxt.tp.store.ConsoleStore;
import com.flytxt.tp.store.HdfsStore;
import com.flytxt.tp.store.LocalFileStore;
import com.flytxt.tp.store.NeonStore;
import com.flytxt.tp.store.Store;
import com.flytxt.tp.store.StreamStore;
import com.flytxt.tp.marker.ConstantMarker;
import com.flytxt.tp.translator.TpDate;
import com.flytxt.tp.translator.TpLogic;
import com.flytxt.tp.translator.TpMath;
import com.flytxt.tp.translator.TpString;
import com.flytxt.tp.marker.Router;
import java.io.IOException;

public final class TestScript implements LineProcessor,ConstantMarker {
	public final String outputFolder = "TestScript1479106100547";
	public final String regex = "TestScript1479106100547";
	public final String inputFolder = "TestScript1479106100547";
     private String str_inter = new String(ConstantMarker.INTERDATATYPE.getData());

	public final MarkerFactory mf = new MarkerFactory();

	private TpMath tpMath = new TpMath();
	private TpDate tpDate = new TpDate();
	private TpString tpString = new TpString();
	private TpLogic tpLogic = new TpLogic();

	private Marker m_inter = ConstantMarker.INTERDATATYPE;
     private Marker m_intra = ConstantMarker.INTRADATATYPE;
	private Marker line;;
	private Marker fileName;
	private Marker mlastModifiedTime;
     private Marker mpickedTime;
     
	private Marker m__temp1 = mf.createMarker(null,0, 0);

private byte[] token_124= "|".getBytes();

private Marker mpr = mf.createMarker(null,0, 0);

private Store hdfsStore = new  ConsoleStore(outputFolder,"__temp1","pr");

	
	public TestScript(){
	m__temp1 = mf.createMarker(null,0, 0);

mpr = mf.createMarker(null,0, 0);

hdfsStore.set("TestScript");

	line = mf.getLineMarker();
	}
	
	@Override
     public void init(String fileNameStr,   long lastModifiedTime){
          byte[] b1 = String.valueOf(lastModifiedTime).getBytes();
          mlastModifiedTime = mf.createMarker(b1, 0 , b1.length);
          byte[] b2 = String.valueOf(System.currentTimeMillis()).getBytes();
          mpickedTime = mf.createMarker(b2, 0 , b2.length);
          byte[] tt = fileNameStr.getBytes();
          this.fileName = mf.createMarker(tt, 0, tt.length); 
          Router router = new Router(new int[]{1});
           fileName.splitAndGetMarkers(token_124,router,mf,mpr);
hdfsStore.set(fileNameStr);
         
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
	    sb.append(hdfsStore.done()).append(",");

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
		Router router = new Router(new int[]{1});
		line.splitAndGetMarkers(token_124,router,mf,m__temp1);

	}
	
	public void translate(final byte[] data) throws Exception {
		
	}
	
	public void store(final byte[] data) throws Exception {
		hdfsStore.save(data, fileName.toString(), m__temp1,mpr);

	}
}
