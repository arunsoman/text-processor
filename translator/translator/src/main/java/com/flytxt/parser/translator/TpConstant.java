package com.flytxt.parser.translator;

public interface TpConstant {

	byte negative = (byte)'-';
	byte start = 0x30;
	byte end = 0x39;
	byte exp = (byte) ((byte)'e' -start);
	byte dot = (byte) (0x2e -start);
}
