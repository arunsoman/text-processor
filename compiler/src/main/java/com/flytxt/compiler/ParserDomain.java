package com.flytxt.compiler;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

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

    public String compileNtest(final String name, final String init, String absProcessor, final String extract, final String transformation, final String store, final String type, // single,hybrid
            final String sampleData) {
        absProcessor = utils.replaceWithConsoleStore(absProcessor);

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

    public File getJar(final String host) throws Exception {

        List<Job> jobFilter = openConnection();
        // repo.findByhostNameAndActive(host, 1);
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
        utils.complie(loc.javaHome + host + "/", loc.getClassDumpLoc(host));
        utils.createJar(loc.getClassDumpLoc(host), loc.getJarDumpLocatiom(host) + "host.jar");

        return Paths.get(loc.getJarDumpLocatiom(host) + "/host.jar").toFile();
    }

    public List<Job> openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.14.60:3306/tpe", "rte", "bullet");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from job_dk2 where active=1");
            List<Job> jobList = new ArrayList<>();
            while (rs.next()) {
                Job job = new Job();
                job.setDkPscript(rs.getString(16));
                job.setName(rs.getString(2));
                job.setInputPath(rs.getString(8));
                job.setRegex(rs.getString(9));
                jobList.add(job);
            }
            con.close();
            return jobList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}