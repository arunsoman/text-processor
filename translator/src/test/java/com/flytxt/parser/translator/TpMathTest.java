package com.flytxt.parser.translator;

import java.nio.ByteBuffer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TpMathTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TpMathTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TpMathTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	byte[] bytes = ByteBuffer.allocate(8).putLong(23467766).array();
    	System.out.println(new String(bytes));
        assertTrue( true );
    }
}
