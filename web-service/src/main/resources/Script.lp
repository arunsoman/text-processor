import com.flytxt.parser.marker.ImmutableMarker;
import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.lookup.Lookup;
import com.flytxt.parser.lookup.PrefixLookupIgnoreCase;
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
import java.io.IOException;

public class %(name) implements LineProcessor{
	public MarkerFactory mf;
	private ImmutableMarker fileName;
	private Marker m_inter = TpConstant.INTERDATATYPE;
    private Marker m_intra = TpConstant.INTRADATATYPE;
    private String str_inter = new String(TpConstant.INTERDATATYPE.getData());
	private TpString tpString = new TpString();
	private TpDate tpDate = new TpDate();
	private TpMath tpMath = new TpMath();
	private TpLogic tpLogic = new TpLogic();
	private Marker line = new Marker();
	public final String outputFolder = "%(outputfolder)";
	public final String regex = "%(regex)";
	public final String inputFolder = "%(inputFolder)";
	%(absProcessor)
	
	public %(name)(){
	%(init)
	}
	
	@Override
     public void init(String fileName, MarkerFactory mf){
          store.set(fileName);
          byte[] tt = fileName.getBytes();
          this.mf = mf;
          this.fileName = mf.createMarker(tt, 0, tt.length);

	}
	
	public String getSourceFolder(){
		return inputFolder;
	}
	     
	public void process() throws IOException{
		process(mf.getLineMarker().getData());
		translate(mf.getLineMarker().getData());
		store(mf.getLineMarker().getData());
		mf.reclaim();
	}

	public String done() throws IOException{
		return neonStore.done();
		//TODO
	}

	public String getFilter(){
	    return this.regex;
	}
	
	public MarkerFactory getMf() {
	    return mf;
	}

	 public int getMaxListSize() {
        return mf.getListSize();
    }
	
	public void process(final byte[] data){
		%(extract)
	}
	
	public void translate(final byte[] data){
		%(transformation)
	}
	
	public void store(final byte[] data) throws IOException {
		%(store)
	}
}