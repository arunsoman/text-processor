package com.flytxt.parser.compiler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    private ParserDomain parserDomain;

    @RequestMapping(path = "/compileNtest", method = RequestMethod.POST, produces = "text/plain")
    public ResponseEntity<String> compileNtest(@RequestParam("name") final String name, @RequestParam("init") final String init, @RequestParam("absProcessor") String absProcessor,
            @RequestParam("extract") final String extract, @RequestParam("transformation") final String transformation, @RequestParam("store") final String store,
            @RequestParam("type") final String type, // single,hybrid
            @RequestParam("sample") final String sampleData) {
        String output;
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        try {
            absProcessor = replaceWithConsoleStore(absProcessor);
            output = parserDomain.compileNtest(name, init, absProcessor, extract, transformation, store, type, sampleData);
            // return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(new
            // ByteArrayInputStream((output).getBytes())));
        } catch (Exception e) {
            output = e.getMessage();
        }
        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("text/plain")).body(output);
    }

    private String replaceWithConsoleStore(String absProcessor) {
        int toBeReplacedEnd = absProcessor.indexOf("Store("), toBeReplacedStart;
        for (toBeReplacedStart = toBeReplacedEnd; toBeReplacedStart > 0; toBeReplacedStart--)
            if (absProcessor.charAt(toBeReplacedStart) == ' ')
                break;
        String toBeReplaced = absProcessor.substring(toBeReplacedStart + 1, toBeReplacedEnd);
        return absProcessor.replace(toBeReplaced, "Console");

    }

    @RequestMapping(path = "/getJar", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<InputStreamResource> getJar(@RequestParam("host") final String host) {

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Accept", "application/java-archive");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=" + host + ".jar");

        try {
            File jar = parserDomain.getJar(host);
            return ResponseEntity.ok().headers(headers).contentLength(jar.length()).contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new FileInputStream(jar)));
        } catch (final FileNotFoundException e) {
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream(("no content for" + host).getBytes())));
        }

    }

    // @Test
    // public void testAbsReplacement() {
    // System.out.println(replaceWithConsoleStore("\n\n\nsdlasdlasdlasdld\nblahblahbvlah\nblahprivate final Store wcStore = new HdfsStore();"));
    // }

}