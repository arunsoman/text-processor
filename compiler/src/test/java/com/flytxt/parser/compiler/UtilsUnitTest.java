package com.flytxt.parser.compiler;

import static org.junit.Assert.fail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.jar.JarOutputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsUnitTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testCompileGivenScript() throws Exception {
        final Utils utils = new Utils();
        final String scr = "src/test/resources/Script.pl";
        System.out.println(System.getProperty("user.dir"));
        final String java = utils.createJavaContent(scr);
        final String createFile = utils.createFile("/tmp/java/demo/", java, "Script.java");
        utils.complie(createFile, "/tmp/java/demo");
    }

    @Test
    public void testBadClass() {
        final Utils utils = new Utils();
        final String src = "src/test/resources/compiler/Bad.java";
        final String dest = "/tmp/";
        String res;
        try {
            res = utils.complie(src, dest);
        } catch (final Exception e) {
            return;
        }
        fail("Should have produced  error text");
    }

    @Test
    public void testGoodClass() throws Exception {
        final Utils utils = new Utils();
        final String src = "/tmp/java/demo/Script.java";
        final String dest = "/tmp/classes";
        final String res = utils.complie(src, dest);
        if (res != null) {
            logger.debug(res);
            fail("should not have produced  error text");
        }
    }

    @Test
    public void testDirListing() {
        final String loc = "/home/athul/git/text-processor/parser/target/classes";
        final Utils utils = new Utils();
        try {
            final FileOutputStream fout = new FileOutputStream("/tmp/test.jar");
            final JarOutputStream jarOut = new JarOutputStream(fout);
            utils.listFiles(Paths.get(loc), jarOut, loc.length() + 1, true);
            jarOut.close();
            fout.close();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
