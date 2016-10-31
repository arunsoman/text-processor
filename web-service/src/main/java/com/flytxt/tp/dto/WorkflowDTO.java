package com.flytxt.tp.dto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flytxt.compiler.RealtimeCompiler;
import com.flytxt.parser.marker.CurrentObject;
import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.tp.Job;
import com.flytxt.tp.Utils;
import com.flytxt.tp.domain.Workflow;

@Component
public class WorkflowDTO {

    private Utils utils = new Utils();

    private String scriptlp;

    public WorkflowDTO() throws IOException, URISyntaxException {
        scriptlp = utils.readLptemplate("Script.lp");
    }

    public String execute(Workflow workflow) throws Exception {
        prep(workflow);
        LineProcessor lp = (LineProcessor) RealtimeCompiler.getClass(workflow.get(workflow.name)).newInstance();
        MarkerFactory mf = lp.getMf();
        CurrentObject obj = mf.getCurrentObject();
        obj.init("", "");

        lp.init(workflow.get(workflow.name));
        for (String aLine : workflow.get(workflow.sample).split("\n")) {
            byte[] data = aLine.getBytes();
            obj.setCurrentLine(data, 0, data.length);
            lp.process();
        }
        return lp.done();
    }

    public byte[] serialize(Workflow workflow) throws Exception {
        return prep(workflow);
    }

    private byte[] prep(Workflow workflow) throws Exception {
        StrSubstitutor sub = new StrSubstitutor(workflow, "%(", ")");
        String result = sub.replace(scriptlp);
        // TODO replace input Location to link location
        return RealtimeCompiler.compileToBytes(workflow.get(workflow.name), result);
    }

    public Workflow convert(Job job) {
        Workflow wf = parseScript(job.getSchema());
        wf.put(wf.name, job.getName());
        wf.put(wf.regex, job.getRegex());
        wf.put(wf.outputfolder, job.getOutputPath());
        wf.put(wf.hostName, job.getHostName());
        return null;
    }

    private Workflow parseScript(String script) {
        ObjectMapper mapper = new ObjectMapper();
        Workflow scriptMap = new Workflow();
        try {
            scriptMap = mapper.readValue(script, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return scriptMap;
    }
}
