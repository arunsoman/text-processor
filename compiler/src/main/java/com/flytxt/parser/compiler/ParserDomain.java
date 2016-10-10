package com.flytxt.parser.compiler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableAutoConfiguration
public class ParserDomain {

    @Autowired
    private LocationSettings loc;

    @Autowired
    private Utils utils;

    public String compileNtest(
    		final String name,
    		final String absProcessor,
    		final String extract,
    		final String store,
    		final String type, //single,hybrid
    		final String sampleData
    		) {
    	Map<String, String> values = new HashMap<String, String>();
		values.put("name", name);
		values.put("absProcessor", absProcessor);
		values.put("extract", extract);
		values.put("store", store);
		values.put("type", type); //single,hybrid
		values.put("sample", sampleData);
    	StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
    	try {
			String result = sub.replace(utils.createSingleVM());
			utils.createFile(loc.javaHome, result, name+".java");
			utils.complie(loc.getJarHome(), loc.getClassDumpLoc("demo"));
			String output = utils.testRunLp(utils.loadClass(loc.getClassDumpLoc("demo"), name), sampleData.split("\n"));
            return output;
    	} catch (Exception e) {
            return e.getMessage();
		}
    }

    public File getJar(@RequestParam("host") final String host) {

    	//load from db all scripts for this host
    	//compile each of them
    	//form a jar
    	//transfer the jar
    	return null;
    }

}