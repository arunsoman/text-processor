package com.flytxt.tp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public String readLptemplate(String name) throws Exception {
		try {
			String valueOf = new String(findFile(name));
			return valueOf;
		} catch (Exception e) {
			throw e;
		}
	}

	public static String replaceWithConsoleStore(String absProcessor) {
		Pattern p = Pattern.compile("(new\\s)+(.+)(Store\\()", Pattern.MULTILINE);
		Matcher m = p.matcher(absProcessor);
		if (m.find())
			return m.replaceAll("$1 Console$3");
		return null;
	}

	private byte[] findFile(String name) throws URISyntaxException {
		InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(name);
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
			byte[] buffer = new byte[0xFFFF];

			for (int len; (len = resourceAsStream.read(buffer)) != -1;)
				os.write(buffer, 0, len);

			os.flush();

			return os.toByteArray();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
		// return getClass().getResource(loc[i]+name).toURI();

	}
}
