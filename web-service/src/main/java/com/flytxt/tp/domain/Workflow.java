package com.flytxt.tp.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Workflow {
	final String name;

    final String init;

    final String absProcessor;

    final String extract;

    final String done;

    final String transformation;

    final String store;

    final String type; // single,hybrid

    final String sample;
    final String outputfolder;
    final String regex;
    final String inputFolder;


    public Map<String, String> toMap() {
        final Map<String, String> values = new HashMap<>();
        values.put("name", name);
        values.put("init", init);
        values.put("absProcessor", absProcessor);
        values.put("extract", extract);
        values.put("transformation", transformation);
        values.put("store", store);
        values.put("type", type); // single,hybrid
        values.put("outputfolder",outputfolder);
        values.put("regex",regex);
        values.put("inputFolder",inputFolder);
        values.put("done",done);
        values.put("sample", sample);
        return values;
    }

}
