import com.flytxt.parser.lookup.Lookup;
import com.flytxt.parser.lookup.PrefixLookupIgnoreCase;
import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.store.ConsoleStore;
import com.flytxt.parser.store.Store;
import com.flytxt.parser.translator.TpDate;
import com.flytxt.parser.translator.TpLogic;
import com.flytxt.parser.translator.TpMath;
import com.flytxt.parser.translator.TpString;
import java.io.IOException;

public class %(name) implements LineProcessor{
	private Marker fileName;
	private String folderName;
	private MarkerFactory mf;
	private TpString tpString = new TpString();
	private TpDate tpDate = new TpDate();
	private TpMath tpMath = new TpMath();
	private TpLogic tpLogic = new TpLogic();
	private Store wcStore = new ConsoleStore();
	
	%(absProcessor)
	
	public void init(){
     %(init)    
	}
	
	public String getFolder(){
		return folderName;
	}
	
	public void setMarkerFactory(MarkerFactory mf){
		this.mf = mf;
	}
	
	public void setInputFileName(String currentFileName){
		this.currentFileName = currentFileName;
		byte[] tt = currentFileName.getBytes();
		this.fileName = mf.createImmutable(tt, 0, tt.length);
	}
	     
	public void process(byte[] data, int startIndex, int readCnt, MarkerFactory mf) throws IOException{
		process(data);
		translate(data);
		store(data);
	}

	public String done() throws IOException{
		//TODO
		return null;
	}

	public String getFilter(){
		//TODO
		return null;
	}

	public int getMaxListSize(){
		//TODO
		return 10000;
	}
	
	public void process(final byte[] data){
		%(extract)
	}
	
	public void translate(final byte[] data){
		%(transformation)
	}
	
	public void store(final byte[] data) throws IOException {
		wcStore.save(data, "test", mj);
	}
}