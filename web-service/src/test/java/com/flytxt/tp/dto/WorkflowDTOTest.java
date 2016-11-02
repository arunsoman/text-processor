package com.flytxt.tp.dto;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;

import com.flytxt.compiler.RealtimeCompiler;
import com.flytxt.parser.marker.CurrentObject;
import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.tp.domain.Workflow;

public class WorkflowDTOTest {

    WorkflowDTO dto;

    @Before
    public void init() throws IOException, URISyntaxException {
        dto = new WorkflowDTO();
    }

    // @Test
    public void compileFromMap() {
        try {
            System.out.println(dto.execute(createFromString()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // @Test
    public void compileFromFile() {
        try {
            String className = "TestScript";
            String sample = "0003657095|1|2|09-19-16|20:00:45.0|||09-19-16|20:00:45.8|98956|919712569423|0|9141069913091232|17|pps|16|52|919712569423|98956|Asia/Calcutta|0.0000|2|0.0000|1|0.0000|2|9141069913091232||0|0|0|0||404050220707456|0|313|0.0000|0.0000|0.0000|0|0|0|0.00|0|124@@@@@@|12424@@@@|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|||1|0|36||0|919825401010|404050220707456|101|1||||||||17|0|22|1*CORE BALANCE*10.4490*0.0000*1* *2*0*2*V2V_LOCAL_BAL*0.0000*0.0000*2* *2*0*3*SMS_BALANCE*0.0000*0.0000*4* *2*0*4*V2V_NIGHT_MINUTES*0.0000*0.0000*2* *2*0*5*STD_MINUTES*0.0000*0.0000*2* *2*0*6*V2V_LOCAL_MINUTES*0.0000*0.0000*2* *2*0*7*M2M_LOCAL*0.0000*0.0000*2* *2*0*8*Minutes_LOCAL*0.0000*0.0000*2* *2*0*9*SMS_STD_LO*0.0000*0.0000*4* *2*0*10*LOCAL_NIGHT_MINUTES*0.0000*0.0000*2* *2*0*11*LOCAL_STD_BAL*0.0000*0.0000*2* *2*0*22*CORE2_BALANCE*0.0000*0.0000*1* *2*0*42*V2O_BAL*0.0000*0.0000*2* *2*0*62*DUMMY_SMS*0.0000*0.0000*4* *2*0*63*V2V_LOC_NAT_SMS*0.0000*0.0000*4* *2*0*65*ROAM_INTL_IC_FREE*0.0000*0.0000*2* *2*0*66*ERW_LOCAL_MOBILE_SECS*0.0000*0.0000*2* *2*0*67*CallDropV2VL*0.0000*0.0000*2* *2*0*68*ERW_SMS*0.0000*0.0000*4* *2*0*69*ERW_NIGHT_V2V_SECS*0.0000*0.0000*2* *2*0*70*ERW_TALKTIME*0.0000*0.0000*1* *2*0*73*FLEX_BAL*0.0000*0.0000*1* *2*0*|1|5*ACCUM_SMS_100*0.0000*0.0000*|0||401D413F0BE4D1|1|0|3|0.0000||";
            RealtimeCompiler.compileToBytes(className, (readFromFile(new File("/tmp/java/demo/", "TestScript.java"))));
            Class<?> class1 = RealtimeCompiler.getClass(className);

            LineProcessor lp = (LineProcessor) class1.newInstance();
            MarkerFactory mf = lp.getMf();
            CurrentObject obj = mf.getCurrentObject();
            obj.init("", "");

            lp.init(className);
            for (String aLine : sample.split("\n")) {
                byte[] data = aLine.getBytes();
                obj.setCurrentLine(data, 0, data.length);
                lp.process();
            }
            System.out.println(lp.done());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Workflow createFromString() {
        String name = "TestScript1477050017694";
        String init = "mtemp1 = mf.createMarker(null,0, 0);m__temp2 = mf.createMarker(null,0, 0);m__temp6 = mf.createMarker(null,0, 0);store.set(\"TestScript\");\n";
        String absProcessor = "private Marker mtemp1;private Marker m__temp2;private byte[] token_124= \"|\".getBytes();private Marker m__temp6;private byte[] token_49= \"1\".getBytes();private boolean mtemp3;private boolean m__temp5;private Marker m__temp4;private Marker statefull = TpConstant.booleanFalseMarker;private Marker aggregatable = TpConstant.booleanTrueMarker;private Marker m__temp__1;private Marker m;private Marker passthrough = TpConstant.booleanFalseMarker;private Marker eventType = mf.createMarker(\"AGG_AND_TIME\".getBytes(), 0, 11);private Marker fieldType = mf.createMarker(\"SECONDS\".getBytes(), 0, 6);private Store store = new ConsoleStore(\"__temp6\",\"temp3\");\n";
        String extract = "line.splitAndGetMarkers(token_124,new int[]{18,23},mf,mtemp1,m__temp2);fileName.splitAndGetMarkers(token_49,new int[]{1},mf,m__temp6);\n";
        String transformation = "mtemp3 = tpString.endsWith(mtemp1,  m__temp2); if (tpString.containsIgnoreCase( m__temp2, m__temp2) ) { m__temp5 = tpString.containsIgnoreCase( m__temp2,  m__temp2); }\n";
        String store = "store.save(data, fileName.toString(), m__temp6, mtemp3 ? TpConstant.booleanTrueMarker : TpConstant.booleanFalseMarker);\n";
        String type = "single";
        String sample = "0003657095|1|2|09-19-16|20:00:45.0|||09-19-16|20:00:45.8|98956|919712569423|0|9141069913091232|17|pps|16|52|919712569423|98956|Asia/Calcutta|0.0000|2|0.0000|1|0.0000|2|9141069913091232||0|0|0|0||404050220707456|0|313|0.0000|0.0000|0.0000|0|0|0|0.00|0|124@@@@@@|12424@@@@|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|||1|0|36||0|919825401010|404050220707456|101|1||||||||17|0|22|1*CORE BALANCE*10.4490*0.0000*1* *2*0*2*V2V_LOCAL_BAL*0.0000*0.0000*2* *2*0*3*SMS_BALANCE*0.0000*0.0000*4* *2*0*4*V2V_NIGHT_MINUTES*0.0000*0.0000*2* *2*0*5*STD_MINUTES*0.0000*0.0000*2* *2*0*6*V2V_LOCAL_MINUTES*0.0000*0.0000*2* *2*0*7*M2M_LOCAL*0.0000*0.0000*2* *2*0*8*Minutes_LOCAL*0.0000*0.0000*2* *2*0*9*SMS_STD_LO*0.0000*0.0000*4* *2*0*10*LOCAL_NIGHT_MINUTES*0.0000*0.0000*2* *2*0*11*LOCAL_STD_BAL*0.0000*0.0000*2* *2*0*22*CORE2_BALANCE*0.0000*0.0000*1* *2*0*42*V2O_BAL*0.0000*0.0000*2* *2*0*62*DUMMY_SMS*0.0000*0.0000*4* *2*0*63*V2V_LOC_NAT_SMS*0.0000*0.0000*4* *2*0*65*ROAM_INTL_IC_FREE*0.0000*0.0000*2* *2*0*66*ERW_LOCAL_MOBILE_SECS*0.0000*0.0000*2* *2*0*67*CallDropV2VL*0.0000*0.0000*2* *2*0*68*ERW_SMS*0.0000*0.0000*4* *2*0*69*ERW_NIGHT_V2V_SECS*0.0000*0.0000*2* *2*0*70*ERW_TALKTIME*0.0000*0.0000*1* *2*0*73*FLEX_BAL*0.0000*0.0000*1* *2*0*|1|5*ACCUM_SMS_100*0.0000*0.0000*|0||401D413F0BE4D1|1|0|3|0.0000||";
        String outputfolder = "";
        String regex = "";
        String inputFolder = "";
        String done = "sb.append(store.done()).append(',');";

        Workflow flow = new Workflow();
        flow.put(flow.name, name);
        flow.put(flow.init, init);
        flow.put(flow.absProcessor, absProcessor);
        flow.put(flow.extract, extract);
        flow.put(flow.transformation, transformation);
        flow.put(flow.store, store);
        flow.put(flow.type, type);
        flow.put(flow.sample, sample);
        flow.put(flow.outputfolder, outputfolder);
        flow.put(flow.regex, regex);
        flow.put(flow.inputFolder, inputFolder);
        flow.put(flow.done, done);

        return flow;
    }

    private String readFromFile(File file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file.getPath())));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
