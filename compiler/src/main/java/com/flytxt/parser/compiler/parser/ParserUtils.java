package com.flytxt.parser.compiler.parser;

public class ParserUtils {

    protected StringBuilder code = new StringBuilder();

    protected int maxVal = 0;

    protected String getDelim(final String s) {
        return s.substring(s.indexOf('(') + 1, s.length() - 1);
    }

    protected String getValue(final String s) {

        final String val = s.substring(s.indexOf('(') + 1, s.length() - 1);
        try {
            final int valI = Integer.parseInt(val);
            if (valI > maxVal) {
                maxVal = valI;
            }
            System.out.println("Number:" + val);
        } catch (final NumberFormatException e) {
            System.out.println(val);
        }
        return val;
    }
}