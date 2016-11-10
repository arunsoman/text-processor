package com.flytxt.tp.translator.feature.transformer;

import cucumber.api.Transformer;

public class StringTransform extends Transformer<StringHelper> {

	@Override
	public StringHelper transform(String arg0) {
		StringBuilder sb = new StringBuilder(arg0);
		if(arg0.startsWith("'")){
			sb.deleteCharAt(0);
		}
		if(arg0.endsWith("'")){
			sb.deleteCharAt(sb.length()-1);
		}
		return new StringHelper(sb.toString());
	}

}
