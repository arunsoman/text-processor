package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class CoOccur {
	private final static HashMap<Integer, Point> validChars = new HashMap<>();
	static {

		// "ddMMyyyy HH:mm:ss.S Z "
		// "01234567890123456789012345"
		validChars.put((int) 'd', new Point(2, 0,2));
		validChars.put((int) 'M', new Point(2, 2,2));
		validChars.put((int) 'y', new Point(4, 4,4));
		validChars.put((int) 'H', new Point(2, 9,2));
		validChars.put((int) 'm', new Point(2, 12,2));
		validChars.put((int) 's', new Point(2, 15,2));
		validChars.put((int) 'S', new Point(1, 18,1));
		validChars.put((int) 'Z', new Point(1, 19,5));
	}
	public static final int size = "dMyHmsSZ".length();
	private List<CharCnt> list = new ArrayList<CharCnt>(size);

	public CoOccur(String format) throws ParseException {
		String tStr = format.trim();
		int zIndex =tStr.indexOf('Z');
		if(zIndex+1 != tStr.length() && zIndex != -1 )
			throw new ParseException("Z should be @ last", zIndex);
		char[] charArray = format.toCharArray();
		char preChar = charArray[0];
		int cnt = 0;
		int loc = 0;
		int index = 0;
		for (char c : charArray) {
			if (c == preChar)
				cnt++;
			else {
				add(preChar, loc, cnt);
				preChar = c;
				cnt = 1;
				loc = index;
			}
			index++;
		}
		add(preChar, loc, cnt);
	}

	final private void add(char aChar, int loc, int cnt) throws ParseException {
		Point val = validChars.get((int) aChar);
		if (val == null) {
			return;
		}
		if (val.count != cnt) {
			throw new ParseException("expected count for " + aChar + " actual:" + val.count + " found:" + cnt, 0);
		}
		//System.out.println("char:"+aChar+ " @:"+loc+" n:"+cnt);
		list.add(new CharCnt(aChar, val.position, loc, val.chars));
	}

	final int[][] toPlan() {
		int[][] plan = new int[list.size()][3];
		CharCnt cc;
//System.out.println(":"+list.size());
		for (int i = 0; i < list.size(); i++) {
			cc = list.get(i);
			plan[i][0] = cc.srcLoc;
			plan[i][1] = cc.desLoc;
			plan[i][2] = cc.cnt;
		}
		return plan;
	}
}