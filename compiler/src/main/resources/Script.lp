import com.flytxt.parser.marker.ImmutableMarker;
import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.lookup.Lookup;
import com.flytxt.parser.lookup.PrefixLookupIgnoreCase;
import com.flytxt.parser.store.ConsoleStore;
import com.flytxt.parser.store.LocalFileStore;
import com.flytxt.parser.store.Store;
import com.flytxt.parser.store.StreamStore;
import com.flytxt.parser.translator.TpConstant;
import com.flytxt.parser.translator.TpDate;
import com.flytxt.parser.translator.TpLogic;
import com.flytxt.parser.translator.TpMath;
import com.flytxt.parser.translator.TpString;
import java.io.IOException;

public class %(name) implements LineProcessor{
	public final MarkerFactory mf = new MarkerFactory();
	private ImmutableMarker fileName;
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
     public void init(String fileName){
          store.set(fileName);
          byte[] tt = fileName.getBytes();
          this.fileName = mf.createImmutable(tt, 0, tt.length);
	}
	
	public String getSourceFolder(){
		return inputFolder;
	}
	     
	public void process(byte[] data, int startIndex, int readCnt) throws IOException{
		line.index = startIndex;
          line.length = readCnt;
		process(data);
		translate(data);
		store(data);
		mf.reclaim();
	}

	public String done() throws IOException{
		return store.done();
		//TODO
	}

	public String getFilter(){
	    return this.regex;
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