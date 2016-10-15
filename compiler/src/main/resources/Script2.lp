import java.util.ArrayList;
import java.util.List;
import com.flytxt.parser.processor.FolderEventListener.Watch;


public class FolderListener {

public List<com.flytxt.parser.processor.FolderEventListener.Watch> getList() {
List<com.flytxt.parser.processor.FolderEventListener.Watch> list = new ArrayList<>();
%(list)  
return list;
}
}
/*
 a = new Watch("/Files/INPUT/metric" , "tmp" , "dest");
list.add(a);
*/