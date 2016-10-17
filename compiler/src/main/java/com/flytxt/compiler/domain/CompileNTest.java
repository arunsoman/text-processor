package com.flytxt.compiler.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class CompileNTest {

    final String name;

    final String init;

    String absProcessor;

    final String extract;

    final String transformation;

    final String store;

    final String type; // single,hybrid

    final String sample;

    final String initMarkers;

    final String markerSize;

    public Map<String, String> toMap() {
        final Map<String, String> values = new HashMap<>();
        values.put("name", name);
        values.put("init", init);
        values.put("absProcessor", absProcessor);
        values.put("extract", extract);
        values.put("transformation", transformation);
        values.put("store", store);
        values.put("type", type); // single,hybrid
        values.put("sample", sample);
        values.put("markerSize", markerSize);
        values.put("initMarkers", initMarkers);
        return values;
    }

}
