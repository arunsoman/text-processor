package com.flytxt.tp.dto;

import java.io.IOException;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flytxt.tp.Utils;
import com.flytxt.tp.compiler.RealtimeCompiler;
import com.flytxt.tp.domain.Workflow;
import com.flytxt.tp.marker.CurrentObject;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.processor.Job;
import com.flytxt.tp.processor.LineProcessor;

@Component
public class WorkflowDTO {

    private Utils utils = new Utils();

    private String scriptlp;

    public WorkflowDTO() throws Exception{
        scriptlp = utils.readLptemplate("Script.lp");
    }

    public String execute(Workflow workflow) throws Exception {
        prep(workflow);
        try {
            Class<LineProcessor> class1 = (Class<LineProcessor>) RealtimeCompiler.getClass(workflow.get(workflow.name));
            LineProcessor lp = class1.newInstance();
            MarkerFactory mf = lp.getMf();
            CurrentObject obj = mf.getCurrentObject();
            obj.init("", "");

            lp.init(workflow.get(workflow.name), System.currentTimeMillis());
            for (String aLine : workflow.get(workflow.sample).split("\n")) {
                byte[] data = aLine.getBytes();
                obj.setCurrentLine(data, 0, data.length);
                lp.process();
            }
            return lp.done();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public byte[] serialize(Workflow workflow) throws Exception {
        return prep(workflow);
    }

    private byte[] prep(Workflow workflow) throws Exception {
        StrSubstitutor sub = new StrSubstitutor(workflow, "%(", ")");
        String result = sub.replace(scriptlp);
        result = Utils.replaceWithConsoleStore(result);
        // TODO replace input Location to link location
        return RealtimeCompiler.compileToBytes(workflow.get(workflow.name), result);
    }

    public Workflow convert(Job job) {
        Workflow wf = parseScript(job.getSchema());

        wf.put(wf.name, job.getName());
        wf.put(wf.regex, job.getRegex());
        wf.put(wf.inputFolder, "/tmp/" + job.getName() + job.getInputPath());
        wf.put(wf.outputfolder, job.getOutputPath());
        wf.put(wf.hostName, job.getHostName());
        return wf;
    }

    private Workflow parseScript(String script) {
        ObjectMapper mapper = new ObjectMapper();
        Workflow scriptMap = new Workflow();
        try {
            scriptMap = mapper.readValue(script, Workflow.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return scriptMap;
    }
}
