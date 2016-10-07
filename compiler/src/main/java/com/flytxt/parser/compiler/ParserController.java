package com.flytxt.parser.compiler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class ParserController {

    @Autowired
    private LocationSettings loc;

    @Autowired
    private Utils utils;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public @ResponseBody String getScripts(@RequestParam("host") final String host) {
        return "Script.pl";
    }

    @RequestMapping(path = "/compileNtest", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<InputStreamResource> compileNtest(
    		@RequestParam("name") final String name,
    		@RequestParam("absProcessor") final String absProcessor,
    		@RequestParam("extract") final String extract,
    		@RequestParam("store") final String store,
    		@RequestParam("type") final String type, //single,hybrid
    		@RequestParam("sample") final String sampleData
    		) {
    	Map<String, String> values = new HashMap<String, String>();
		values.put("name", name);
		values.put("absProcessor", absProcessor);
		values.put("extract", extract);
		values.put("store", store);
		values.put("type", type); //single,hybrid
		values.put("sample", sampleData);
    	StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
    	try {
			String result = sub.replace(utils.createSingleVM());
			utils.createFile(loc.javaHome, result, name+".java");
			utils.complie(loc.getJarHome(), loc.getClassDumpLoc("demo"));
			String output = utils.testRunLp(utils.loadClass(loc.getClassDumpLoc("demo"), name), sampleData.split("\n"));
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream((output).getBytes())));
    	} catch (Exception e) {
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream((e.getMessage()).getBytes())));
		}
    }
    @RequestMapping(path = "/submit", method = RequestMethod.GET)
    public @ResponseBody String submitScript(@RequestParam("host") final String host, @RequestParam("script") final String script, @RequestParam("scriptName") final String scriptName) {
        if (!Character.isUpperCase(scriptName.charAt(0)) || !scriptName.endsWith(".pl")) {
            return scriptName + " should start with uppercase and should end with .pl";
        }
        try {

            utils.createFile(loc.getScriptDumpLoc(host), script, scriptName);
            final String javaContent = utils.createJavaContent(loc.getScriptURI(host, scriptName));
            final String javaFile = utils.createFile(loc.getJavaDumpLoc(host), javaContent, scriptName.replaceAll(".pl", ".java"));
            utils.complie(javaFile, loc.getClassDumpLoc(host));
            utils.createJar(loc.getClassDumpLoc(host), loc.getJarDumpLocatiom(host) + host + ".jar");
        } catch (final Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping(path = "/getJar", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<InputStreamResource> getJar(@RequestParam("host") final String host) {

        final File jar = new File(loc.jarHome + host + "/" + host + ".jar");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Accept", "application/java-archive");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=" + host + ".jar");

        try {
            return ResponseEntity.ok().headers(headers).contentLength(jar.length()).contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new FileInputStream(jar)));
        } catch (final FileNotFoundException e) {
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream(("no content for" + host).getBytes())));
        }

    }

}