package com.flytxt.tp.domain;

import java.util.HashMap;

public class Workflow extends HashMap<String, String> {

    public final String name = "name";

    public final String init = "init";

    public final String absProcessor = "absProcessor";

    public final String extract = "extract";

    public final String done = "done";

    public final String transformation = "transformation";

    public final String store = "store";

    public final String type = "type"; // single,hybrid

    public final String sample = "sample";

    public final String outputfolder = "outputfolder";

    public final String regex = "regex";

    public final String inputFolder = "inputFolder";

    public final String hostName = "hostName";

}
