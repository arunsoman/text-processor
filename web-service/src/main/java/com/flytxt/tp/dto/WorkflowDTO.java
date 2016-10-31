package com.flytxt.tp.dto;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.flytxt.compiler.RealtimeCompiler;
import com.flytxt.parser.marker.CurrentObject;
import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.tp.Utils;
import com.flytxt.tp.domain.Workflow;

public class WorkflowDTO {
	private Utils utils = new Utils();
	private String scriptlp;

	public WorkflowDTO() throws IOException, URISyntaxException{
		scriptlp = utils.readLptemplate("Script.lp");
	}
	public String execute(Workflow workflow) throws Exception {
		prep(workflow);
		LineProcessor lp = (LineProcessor) RealtimeCompiler.getClass(workflow.getName()).newInstance();
		MarkerFactory mf = new MarkerFactory();
		CurrentObject obj = mf.getCurrentObject();
		obj.init("", "");
		
		lp.init(workflow.getName());
		for (String aLine : workflow.getSample().split("\n")) {
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
		StrSubstitutor sub = new StrSubstitutor(workflow.toMap(), "%(", ")");
		String result = sub.replace(scriptlp);
		//TODO replace input Location to link location
		return RealtimeCompiler.compileToBytes(workflow.getName(), result);
	}
	
	public Workflow convert(String schema) {
		// TODO Auto-generated method stub
		return null;
	}
}
