package com.flytxt.parser.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScriptChangeListener {

    @Autowired
    private Processor processor;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, path = "/newJars")
    public @ResponseBody String reload() {
        processor.stopFileReads();
        try {
            processor.startFileReaders();
            return "ok";
        } catch (final Exception e) {
            logger.error("can't start readers", e);
        }
        return null;
    }

}
