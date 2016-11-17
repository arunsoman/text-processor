package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class CoOccureTest {
	public void testyyyyMMddGatHHmmss() {
		String str = "yyyy.MM.dd G 'at' HH:mm:ss";
		try {
			CoOccur coOccur = new CoOccur(str);
			int[][] plan = coOccur.toPlan();
			System.out.println(Arrays.deepToString(plan));
			int[][] expected = new int[][] { { 0, 4, 4 }, { 5, 2, 2 }, { 8, 0, 2 }, { 18, 9, 2 }, { 21, 12, 2 },
					{ 24, 15, 2 } };
			Assert.assertArrayEquals(expected, plan);
		} catch (ParseException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testMMddGatHHmmssyyyy() {
		String str = " .MM.dd G 'at' HH:mm:ss yyyy";
		try {
			CoOccur coOccur = new CoOccur(str);
			int[][] plan = coOccur.toPlan();
			System.out.println(Arrays.deepToString(plan));
			int[][] expected = new int[][] {{2, 2, 2}, {5, 0, 2}, {15, 9, 2}, {18, 12, 2}, {21, 15, 2}, {24, 4, 4}};
			Assert.assertArrayEquals(expected, plan);
		} catch (ParseException e) {
			Assert.fail();
		}

	}
	
	
}