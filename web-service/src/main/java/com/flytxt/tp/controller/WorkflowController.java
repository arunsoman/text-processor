package com.flytxt.tp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.flytxt.tp.EntityMetaData;
import com.flytxt.tp.EntityMetaData.KeyValue;
import com.flytxt.tp.NeonMeta;
import com.flytxt.tp.dao.WorkflowDao;
import com.flytxt.tp.domain.Workflow;
import com.flytxt.tp.dto.WorkflowDTO;

@Controller
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
    @PostMapping(path = "/validate")
    public ResponseEntity<String> validate(@RequestBody final Workflow workflow){
        String result;
        try{
            result= workflowDto.execute(workflow);
            return ResponseEntity.ok().headers(headers)
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(result);
        }catch(Exception e){
            return ResponseEntity.status(500).headers(headers)
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/neonMeta")
    public ResponseEntity<List<NeonMeta>> neonMeta() {
        
        try {
           	
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/json"))
                    .body(dao.retrieveNeonMeta());
        } catch (Exception e) {
        	System.out.println(e);
            return ResponseEntity.status(500).headers(headers).contentType(MediaType.parseMediaType("application/json"))
                    .body(null);
        }
    }
}
