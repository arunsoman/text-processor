package com.flytxt.tp.translator;

import com.flytxt.tp.marker.ImmutableMarker;

public interface TpConstant {

    byte negative = (byte) '-';

    byte start = 0x30;

    byte end = 0x39;

    byte trueToken = (byte) 'T';

    byte falseToken = (byte) 'F';

    byte exp = (byte) ((byte) 'e' - start);

    byte dot = (byte) (0x2e - start);

    ImmutableMarker booleanTrueMarker = new ImmutableMarker("T".getBytes());

    ImmutableMarker booleanFalseMarker = new ImmutableMarker("F".getBytes());

    byte[] delim1 = { (byte) 0x01 };

    byte[] delim2 = { (byte) 0x02 };

    byte[] delim3 = { (byte) 0x04 };

    byte[] delim4 = { (byte) 0x06 };

    ImmutableMarker INTERDATATYPE = new ImmutableMarker(delim1);

    ImmutableMarker INTRADATATYPE = new ImmutableMarker(delim2);

    ImmutableMarker SEPARATOR = new ImmutableMarker(delim3);

    ImmutableMarker VALUESEPARATOR = new ImmutableMarker(delim4);

}
