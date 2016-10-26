package com.flytxt.parser.translator;

import com.flytxt.parser.marker.ImmutableMarker;

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

    ImmutableMarker dm1 = new ImmutableMarker(delim1);

    ImmutableMarker dm2 = new ImmutableMarker(delim2);

    ImmutableMarker dm3 = new ImmutableMarker(delim3);

    ImmutableMarker dm4 = new ImmutableMarker(delim4);

}
