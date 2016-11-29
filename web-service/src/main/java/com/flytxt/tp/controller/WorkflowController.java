package com.flytxt.tp.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flytxt.tp.NeonMeta;
import com.flytxt.tp.dao.WorkflowDao;
import com.flytxt.tp.domain.Workflow;
import com.flytxt.tp.dto.WorkflowDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WorkflowController {

	@Autowired
	private WorkflowDTO workflowDto;

	@Autowired
	private WorkflowDao dao;

	private HttpHeaders headers;

	@PostConstruct
	public void init(){
		headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache");
		headers.add("Pragma", "no-cache");
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = "/validate",method = RequestMethod.POST)
	public ResponseEntity<String> validate(@RequestBody final Workflow workflow){
		String result;
		try{
			result= workflowDto.execute(workflow);
			return ResponseEntity.ok().headers(headers)
					.contentType(MediaType.parseMediaType("application/json"))
					.body(result);
		}catch(final Exception e){
			return ResponseEntity.status(500).headers(headers)
					.contentType(MediaType.parseMediaType("application/json"))
					.body(e.getMessage());
		}
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = "/neonMeta",method = RequestMethod.POST)
	public ResponseEntity<List<NeonMeta>> neonMeta() {

		try {

			return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/json"))
					.body(dao.retrieveNeonMeta());
		} catch (final Exception e) {
			log.info("/neonMeta",e);
			return ResponseEntity.status(500).headers(headers).contentType(MediaType.parseMediaType("application/json"))
					.body(null);
		}
	}
}
