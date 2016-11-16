package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.flytxt.tp.marker.Marker;

public class TpDateUtil {

	/**
	 *
	 * Letter Date or Time Component Presentation Examples G Era designator Text
	 * AD y Year Year 1996; 96 Y Week year Year 2009; 09 M Month in year Month
	 * July; Jul; 07 w Week in year Number 27 W Week in month Number 2 D Day in
	 * year Number 189 d Day in month Number 10 F Day of week in month Number 2
	 * E Day name in week Text Tuesday; Tue u Day number of week (1 = Monday,
	 * ..., 7 = Sunday) Number 1 a Am/pm marker Text PM H Hour in day (0-23)
	 * Number 0 k Hour in day (1-24) Number 24 K Hour in am/pm (0-11) Number 0 h
	 * Hour in am/pm (1-12) Number 12 m Minute in hour Number 30 s Second in
	 * minute Number 55 S Millisecond Number 978 z Time zone General time zone
	 * Pacific Standard Time; PST; GMT-08:00 Z Time zone RFC 822 time zone -0800
	 * X Time zone ISO 8601 time zone -08; -0800; -08:00
	 */

	private static HashMap<String, Translator> planMap = new HashMap<String, Translator>();

	public byte[] toDateBytes(Marker m, String format) throws ParseException {

		Translator translator = planMap.get(format);
		if (translator == null) {
			translator = formater(format);
			planMap.put(format, translator);
		}
		byte[] translate = translator.translate(m, null);
		return translate;
	}

	public Translator formater(String format) {
		char[] charArray = format.toCharArray();
		CoOccur coOccur = new CoOccur();
		char preChar = charArray[0];
		int cnt = 0;
		int loc = 0;
		int index = 0;
		try {
			for (char c : charArray) {

				if (c == preChar)
					cnt++;
				else {
					coOccur.add(preChar, loc, cnt);
					preChar = c;
					cnt = 1;
					loc = index;
				}
				index++;
			}
			coOccur.add(preChar, loc, cnt);
		} catch (Exception e) {
			return new Translator(new SimpleDateFormat(format));
		}
		return new Translator(coOccur.toPlan());
	}

	public Date parse(String string) throws ParseException {
		return Translator.parse(string);
	}
}
