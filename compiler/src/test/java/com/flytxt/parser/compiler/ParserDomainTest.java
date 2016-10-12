package com.flytxt.parser.compiler;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserDomainTest {
	
@Autowired
private ParserDomain parserdomain;

@Test
public void testgetJars() throws Exception
{
	File file = parserdomain.getJar("demo");
	System.out.println(file.length());
	
	
}
}
