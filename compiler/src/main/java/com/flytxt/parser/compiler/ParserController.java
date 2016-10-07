package com.flytxt.parser.compiler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

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

import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.marker.MarkerFactory;

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
    public @ResponseBody ResponseEntity<InputStreamResource> compileNtest(@RequestParam("name") final String name, @RequestParam("absProcessor") final String absProcessor,
            @RequestParam("init") final String init, @RequestParam("extract") final String extract, @RequestParam("transformation") final String transformation,
            @RequestParam("store") final String store, @RequestParam("type") final String type, // single,hybrid
            @RequestParam("sample") final String sampleData) throws Exception {
        final Map<String, String> values = new HashMap<>();
        values.put("name", name);
        values.put("absProcessor", absProcessor);
        values.put("extract", extract);
        values.put("transformation", transformation);
        values.put("store", store);
        values.put("init", init);

        final StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        final String str = utils.createSingleVM();
        final String javaClass = sub.replace(str);

        utils.createFile(loc.getJavaHome(), javaClass, name + ".java");
        utils.complie(loc.getJavaHome() + name + ".java", loc.getClassHome());
        utils.createJar(loc.getClassHome(), loc.getJarHome() + "demo.jar");

        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        LineProcessor processor = null;
        final URL url = new URL("file:///tmp/jar/demo/demo.jar");
        try (JarInputStream jIs = new JarInputStream(url.openStream())) {
            ZipEntry zipEntry;
            String aClass;
            final List<LineProcessor> lps = new ArrayList<>();

            try (URLClassLoader loader = new URLClassLoader(new URL[] { url }, contextClassLoader)) {
                while ((zipEntry = jIs.getNextEntry()) != null) {
                    if (!zipEntry.isDirectory()) {
                        aClass = zipEntry.getName().replaceAll("/", ".");
                        aClass = aClass.substring(0, aClass.length() - ".class".length());
                        // logger.debug("loading class:" + aClass);
                        @SuppressWarnings("unchecked")
                        final Class<LineProcessor> loadClass = (Class<LineProcessor>) loader.loadClass(aClass);
                        processor = loadClass.newInstance();
                    }
                }
            }
        }

        final String[] data = sampleData.split("\n\r");
        final MarkerFactory mf = new MarkerFactory();
        mf.setMaxListSize(100);
        for (final String datum : data) {
            processor.process(datum.getBytes(), 0, datum.length(), mf);
        }
        final String result = processor.done();
        // loader.close();

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Accept", "application/text");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(new ByteArrayInputStream((result).getBytes())));
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