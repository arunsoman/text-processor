package com.flytxt.parser.compiler;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.flytxt.parser.domain.Job;
import com.flytxt.parser.domain.JobRepo;

@EnableAutoConfiguration
@Component
public class ParserDomain {

    @Autowired
    private LocationSettings loc;

    @Autowired
    private JobRepo repo;

    private Job job;

    @Autowired
    private Utils utils;

    public String compileNtest(final String name, final String init, final String absProcessor, final String extract, final String transformation, final String store, final String type, // single,hybrid
            final String sampleData) {
        final Map<String, String> values = new HashMap<>();
        values.put("name", name);
        values.put("init", init);
        values.put("absProcessor", absProcessor);
        values.put("extract", extract);
        values.put("transformation", transformation);
        values.put("store", store);
        values.put("type", type); // single,hybrid
        values.put("sample", sampleData);
        final StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");

        try {
            final String result = sub.replace(utils.createSingleVM());
            utils.createFile(loc.javaHome, result, name + ".java");
            utils.complie(loc.getJavaHome() + name + ".java", loc.getClassDumpLoc("demo"));
            final String output = utils.testRunLp(utils.loadClass(loc.getClassDumpLoc("demo"), name), sampleData.split("\n"));
            return output;
        } catch (final Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public File getJar(@RequestParam("host") final String host) throws Exception {
        List<Job> jobFilter = repo.findByhostNameAndIsActive(host, 1L);
        StringBuilder sb = new StringBuilder();
        StringBuilder sbWatch = new StringBuilder();
        String createSingleVM = utils.createSingleVM();
        Map<String, String> values = new HashMap<>();
        StrSubstitutor sub = null;
        for (Job job : jobFilter) {
            values = utils.parseScript(job.getDkPscript());
            values.put("name",job.getName());
            sub = new StrSubstitutor(values, "%(", ")");
            String result = sub.replace(createSingleVM);
            utils.createFile(loc.javaHome + host, result, job.getName() + ".java");
            sbWatch.append("new Watch(\"")
				.append(job.getInputPath()).append("\",")
            .append(job.getRegex()==null?null:"\""+job.getRegex()+"\"")
            .append(",\"").append(loc.javaHome).append(job.getName()).append("\")");
            sb.append("list.add(").append(sbWatch.toString()).append(");");
            sbWatch.delete(0, sbWatch.length());
        }
        values = new HashMap<>();
        values.put("list", sb.toString());
        sub = new StrSubstitutor(values, "%(", ")");
        String result = sub.replace(utils.folderEvent());
        utils.createFile(loc.javaHome + host, result, "FolderListener.java");
        utils.complie(loc.javaHome+host+"/", loc.getClassDumpLoc(host));
        utils.createJar(loc.getClassDumpLoc(host), loc.getJarDumpLocatiom(host) + "/host.jar");

        return Paths.get(loc.getJarDumpLocatiom(host) + "/host.jar").toFile();
    }

}