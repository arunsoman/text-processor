package com.flytxt.parser.processor;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.processor.FolderEventListener.Watch;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "compiler")
@Data
public class ProxyScripts {

    public String getScript;

    public String getJar;

    public String remoteHost;

    public String hostName;

    private List<LineProcessor> lps;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class<?> folderListener;

    public List<LineProcessor> getLPInstance() {
        return lps;
    }

    @PostConstruct
    public void init() throws Exception {
        final URL url = new URL(remoteHost + getJar + "?host=" + hostName);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        // final URL url = new URL("file:///tmp/jar/demo/demo.jar");
        try (JarInputStream jIs = new JarInputStream(url.openStream())) {
            ZipEntry zipEntry;
            String aClass;
            lps = new ArrayList<>();

            try (URLClassLoader loader = new URLClassLoader(new URL[] { url }, contextClassLoader)) {
                while ((zipEntry = jIs.getNextEntry()) != null)
                    if (!zipEntry.isDirectory()) {
                        aClass = zipEntry.getName().replaceAll("/", ".");
                        aClass = aClass.substring(0, aClass.length() - ".class".length());
                        logger.debug("loading class:" + aClass);
                        // @SuppressWarnings("unchecked")
                        if (loader.loadClass(aClass).getName().contains("FolderListener"))
                            folderListener = loader.loadClass(aClass);
                        else {
                            @SuppressWarnings("unchecked")
                            final Class<LineProcessor> loadClass = (Class<LineProcessor>) loader.loadClass(aClass);
                            lps.add(loadClass.newInstance());
                        }
                    }
                return;
            }
        } catch (final Exception e) {
            logger.error("can't load classes ", e);
            throw e;
        }
    }

    class FolderWatch {
    }

    @SuppressWarnings("unchecked")
    public List<com.flytxt.parser.processor.FolderEventListener.Watch> getFolderWatch() {
        try {
            return (List<Watch>) folderListener.getMethod("getList").invoke(folderListener.newInstance());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}