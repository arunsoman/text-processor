package com.flytxt.tp.translator;

import org.junit.Test;

import junit.framework.Assert;

public class TpLogicTest {
	boolean t  = true;
	boolean f = false;
	TpLogic tpL = new TpLogic();

	@Test
	public void testAndTT(){
		Assert.assertEquals(true, tpL.and(t, t));
	}
	
	@Test
	public void testAndTF(){
		Assert.assertEquals(false, tpL.and(t, f));
	}
	
	@Test
	public void testAndFF(){
		Assert.assertEquals(false, tpL.and(f,f));
	}
	
	@Test
	public void testorTT(){
		Assert.assertEquals(true, tpL.or(t, t));
	}
	
	@Test
	public void testorTF(){
		Assert.assertEquals(true, tpL.or(t, f));
	}
	
	@Test
	public void testorFF(){
		Assert.assertEquals(false, tpL.or(f,f));
	}
	
	@Test
	public void testNotF(){
		Assert.assertEquals(true, tpL.not(f));
	}
	

	@Test
	public void testNotT(){
		Assert.assertEquals(false, tpL.not(t));
	}
}
