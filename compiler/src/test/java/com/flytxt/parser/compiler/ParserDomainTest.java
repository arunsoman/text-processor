package com.flytxt.parser.compiler;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringRunner.class)
// @SpringBootTest
public class ParserDomainTest {

    @Autowired
    private ParserDomain parserdomain;

    // @Test
    public void testgetJars() throws Exception {
        File file = parserdomain.getJar("demo");
        System.out.println(file.length());

    }
}
