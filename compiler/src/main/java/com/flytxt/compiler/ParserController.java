package com.flytxt.compiler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flytxt.compiler.domain.CompileNTest;

@Controller
@EnableAutoConfiguration
public class ParserController {

    @Autowired
    private ParserDomain parserDomain;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/compileNtest")
    public ResponseEntity<String> compileNtest(@RequestBody final CompileNTest cNt) {
        String output;
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        try {
            output = parserDomain.compileNtest(cNt);
        } catch (Exception e) {
            output = e.getMessage();
        }
        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("text/plain")).body(output);
    }

    @RequestMapping(path = "/getJar", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<InputStreamResource> getJar(@RequestParam("host") final String host) {

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache");
        headers.add("Pragma", "no-cache");
        headers.add("Accept", "application/java-archive");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=" + host + ".jar");

        try {
            File jar = parserDomain.getJar(host);
            return ResponseEntity.ok().headers(headers).contentLength(jar.length()).contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new FileInputStream(jar)));
        } catch (Exception e) {
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream(("no content for" + host).getBytes())));
        }

    }

    // @Test
    // public void testAbsReplacement() {
    // System.out.println(replaceWithConsoleStore("\n\n\nsdlasdlasdlasdld\nblahblahbvlah\nblahprivate final Store wcStore = new HdfsStore();"));
    // }

}