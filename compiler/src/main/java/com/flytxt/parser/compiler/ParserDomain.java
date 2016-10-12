package com.flytxt.parser.compiler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@EnableAutoConfiguration
@Component
public class ParserDomain {

    @Autowired
    private LocationSettings loc;

    @Autowired
    private Utils utils;

    public String compileNtest(final String name, final String init, final String absProcessor, final String extract, final String transformation, final String store, final String type, // single,hybrid
            final String sampleData) {
        final Map<String, String> values = new HashMap<>();
        values.put("name", name);
        values.put("init", init);
        values.put("absProcessor", absProcessor);
        values.put("extract", extract);
        values.put("transformation", transformation);
        values.put("store", store);
        values.put("type", type); // single,hybrid
        values.put("sample", sampleData);
        final StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");

        try {
            final String result = sub.replace(utils.createSingleVM());
            utils.createFile(loc.javaHome, result, name + ".java");
            utils.complie(loc.getJavaHome() + name + ".java", loc.getClassDumpLoc("demo"));
            final String output = utils.testRunLp(utils.loadClass(loc.getClassDumpLoc("demo"), name), sampleData.split("\n"));
            return output;
        } catch (final Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public File getJar(@RequestParam("host") final String host) {

        // load from db all scripts for this host
        // compile each of them
        // form a jar
        // transfer the jar
        return null;
    }

}