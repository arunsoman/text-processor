package com.flytxt.parser.marker;

public class TokenFactory {

    public static byte[] create(final String string) {
        return string.getBytes();
    }

    @SuppressWarnings("unused")
    private String toHex(final String s) {
        final char[] tt = s.replaceAll("'", "").trim().toCharArray();
        final byte[] h = { 0x1, 0x2 };
        final StringBuilder v = new StringBuilder("{");
        for (final char c : tt) {
            v.append(String.format("0x%02X", (int) c)).append(',');
        }
        v.deleteCharAt(v.length() - 1);
        v.append("}");
        return v.toString();
    }
}
