package com.flytxt.parser.processor;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.flytxt.parser.marker.LineProcessor;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "compiler")
@Data
public class ProxyScripts {

    public String getScript;

    public String getJar;

    public String remoteHost;

    public String hostName;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<LineProcessor> getLPInstance() throws Exception {
        // URL url = new URL(remoteHost+getJar+"?host="+hostName);
    	ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    	final URL url = new URL("file:///tmp/jar/demo/demo.jar");
        try (JarInputStream jIs = new JarInputStream(url.openStream())) {
            ZipEntry zipEntry;
            String aClass;
            final List<LineProcessor> lps = new ArrayList<LineProcessor>();
          
            try (URLClassLoader loader = new URLClassLoader(new URL[] { url },contextClassLoader)) {
                while ((zipEntry = jIs.getNextEntry()) != null) {
                    if (!zipEntry.isDirectory()) {
                        aClass = zipEntry.getName().replaceAll("/", ".");
                        aClass = aClass.substring(0, aClass.length() - ".class".length());
                        logger.debug("loading class:" + aClass);
                        @SuppressWarnings("unchecked")
                        final Class<LineProcessor> loadClass = (Class<LineProcessor>) loader.loadClass(aClass);
                        lps.add(loadClass.newInstance());
                    }
                }
                return lps;
            }
        } catch (final Exception e) {
            logger.error("can't load classes ", e);
            throw e;
        }
    }
}