package com.flytxt.tp.translator.tpdateutils;

import org.junit.Assert;
import org.junit.Test;

public class CharCntTest {

	@Test
	public void testCharCnt() {
		CharCnt charCnt = new CharCnt('.', 1, 4, 5);
		CharCnt charCnt1 = new CharCnt('.', 1, 4, 5);
		Assert.assertEquals(charCnt, charCnt1);
	}

	@Test
	public void testCharCntNotEq() {
		CharCnt charCnt = new CharCnt('.', 1, 4, 5);
		CharCnt charCnt1 = new CharCnt('+', 1, 4, 5);
		Assert.assertNotEquals(charCnt, charCnt1);
	}
}
