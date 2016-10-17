package com.flytxt.compiler;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import com.flytxt.compiler.domain.CompileNTest;
import com.flytxt.compiler.domain.Job;
import com.flytxt.compiler.repo.JobRepo;

@EnableAutoConfiguration
@Component
public class ParserDomain {

    @Autowired
    private LocationSettings loc;

    @Autowired
    private JobRepo repo;

    @Autowired
    private Utils utils;

    public String compileNtest(CompileNTest cNt) {
        String variablesWithConsoleStore = utils.replaceWithConsoleStore(cNt.getAbsProcessor());
        cNt.setAbsProcessor(variablesWithConsoleStore);
        String name = cNt.getName();
        final StrSubstitutor sub = new StrSubstitutor(cNt.toMap(), "%(", ")");
        try {
            final String result = sub.replace(utils.createSingleVM());
            utils.createFile(loc.javaHome, result, name + ".java");
            utils.compile(loc.getJavaHome() + name + ".java", loc.getClassDumpLoc("demo"));
            final String output = utils.testRunLp(utils.loadClass(loc.getClassDumpLoc("demo"), name), cNt.getSample().split("\n"));
            return output;
        } catch (final Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public File getJar(final String host) throws Exception {
        List<Job> jobFilter = repo.findByhostNameAndActiveTrue(host);
        StringBuilder sb = new StringBuilder();
        StringBuilder sbWatch = new StringBuilder();
        String createSingleVM = utils.createSingleVM();
        Map<String, String> values = new HashMap<>();
        StrSubstitutor sub = null;
        for (Job job : jobFilter) {
            values = utils.parseScript(job.getDkPscript());
            values.put("name", job.getName());
            sub = new StrSubstitutor(values, "%(", ")");
            String result = sub.replace(createSingleVM);
            utils.createFile(loc.javaHome + host, result, job.getName() + ".java");
            sbWatch.append("new Watch(\"").append(job.getInputPath()).append("\",").append(job.getRegex() == null ? null : "\"" + job.getRegex() + "\"").append(",\"").append(loc.javaHome)
            .append(job.getName()).append("\")");
            sb.append("list.add(").append(sbWatch.toString()).append(");");
            sbWatch.delete(0, sbWatch.length());
        }
        values = new HashMap<>();
        values.put("list", sb.toString());
        sub = new StrSubstitutor(values, "%(", ")");
        String result = sub.replace(utils.folderEvent());
        utils.createFile(loc.javaHome + host, result, "FolderListener.java");
        utils.compile(loc.javaHome + host + "/", loc.getClassDumpLoc(host));
        utils.createJar(loc.getClassDumpLoc(host), loc.getJarDumpLocatiom(host) + "host.jar");

        return Paths.get(loc.getJarDumpLocatiom(host) + "/host.jar").toFile();
    }

}
