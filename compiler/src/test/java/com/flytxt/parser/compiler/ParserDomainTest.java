package com.flytxt.parser.compiler;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import com.flytxt.compiler.ParserDomain;
import com.flytxt.compiler.domain.CompileNTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
@EnableJpaRepositories("com.flytxt.compiler.repo")
@EntityScan("com.flytxt.compiler.domain")
@ComponentScan("com.flytxt.compiler")
public class ParserDomainTest {

    @Autowired
    private ParserDomain parserDomain;

    // @Test
    public void testgetJars() throws Exception {
        File file = parserDomain.getJar("demo");
        System.out.println(file.length());

    }

    @Test
    public void compileNtest() throws Exception {
        String name, init, absProcessor, extract, transformation = ";", store, type, sampleData, output = null;
        name = "TestJavaCode";
        init = "this.mf.setMaxListSize(7);mzxcswewq=mf.create(0,0);";
        absProcessor = "protected Marker mzxcswewq;protected byte[] token_124= \"|\".getBytes();private Store store = new ConsoleStore(\"/tmp/output\");public final String folderName=\"/tmp/java/INRecharge/\";";
        extract = "mzxcswewq = line.splitAndGetMarker(data, token_124,1, mf);";
        type = "single";
        sampleData = "0003657095|1|2|09-19-16|20:00:45.0|||09-19-16|20:00:45.8|98956|919712569423|0|9141069913091232|17|pps|16|52|919712569423|98956|Asia/Calcutta|0.0000|2|0.0000|1|0.0000|2|9141069913091232||0|0|0|0||404050220707456|0|313|0.0000|0.0000|0.0000|0|0|0|0.00|0|124@@@@@@|12424@@@@|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|0|0.0000|||1|0|36||0|919825401010|404050220707456|101|1||||||||17|0|22|1*CORE BALANCE*10.4490*0.0000*1* *2*0*2*V2V_LOCAL_BAL*0.0000*0.0000*2* *2*0*3*SMS_BALANCE*0.0000*0.0000*4* *2*0*4*V2V_NIGHT_MINUTES*0.0000*0.0000*2* *2*0*5*STD_MINUTES*0.0000*0.0000*2* *2*0*6*V2V_LOCAL_MINUTES*0.0000*0.0000*2* *2*0*7*M2M_LOCAL*0.0000*0.0000*2* *2*0*8*Minutes_LOCAL*0.0000*0.0000*2* *2*0*9*SMS_STD_LO*0.0000*0.0000*4* *2*0*10*LOCAL_NIGHT_MINUTES*0.0000*0.0000*2* *2*0*11*LOCAL_STD_BAL*0.0000*0.0000*2* *2*0*22*CORE2_BALANCE*0.0000*0.0000*1* *2*0*42*V2O_BAL*0.0000*0.0000*2* *2*0*62*DUMMY_SMS*0.0000*0.0000*4* *2*0*63*V2V_LOC_NAT_SMS*0.0000*0.0000*4* *2*0*65*ROAM_INTL_IC_FREE*0.0000*0.0000*2* *2*0*66*ERW_LOCAL_MOBILE_SECS*0.0000*0.0000*2* *2*0*67*CallDropV2VL*0.0000*0.0000*2* *2*0*68*ERW_SMS*0.0000*0.0000*4* *2*0*69*ERW_NIGHT_V2V_SECS*0.0000*0.0000*2* *2*0*70*ERW_TALKTIME*0.0000*0.0000*1* *2*0*73*FLEX_BAL*0.0000*0.0000*1* *2*0*|1|5*ACCUM_SMS_100*0.0000*0.0000*|0||401D413F0BE4D1|1|0|3|0.0000||";
        store = "store.save(data, fileName.toString() , mzxcswewq);";
        transformation = ";";
        CompileNTest cNt = new CompileNTest(name, init, extract, transformation, store, type, sampleData);
        cNt.setAbsProcessor(absProcessor);
        output = parserDomain.compileNtest(cNt);
        System.out.println(output);
    }
}
