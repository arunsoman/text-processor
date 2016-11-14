package com.flytxt.tp.marker;

public interface ConstantMarker {

    byte negative = (byte) '-';

    byte start = 0x30;

    byte end = 0x39;

    byte trueToken = (byte) 'T';

    byte falseToken = (byte) 'F';

    byte exp = (byte) ((byte) 'e' - start);

    byte dot = (byte) (0x2e - start);

    Marker booleanTrueMarker = new Marker("T".getBytes(),0,1);

    Marker booleanFalseMarker = new Marker("F".getBytes(),0,1);

    byte[] delim1 = { (byte) 0x01 };

    byte[] delim2 = { (byte) 0x02 };

    byte[] delim3 = { (byte) 0x04 };

    byte[] delim4 = { (byte) 0x06 };

    Marker INTERDATATYPE = new Marker(delim1,0,1);

    Marker INTRADATATYPE = new Marker(delim2,0,1);

    Marker SEPARATOR = new Marker(delim3,0,1);

    Marker VALUESEPARATOR = new Marker(delim4,0,1);
    
    Marker mnull = new Marker(new byte[0],0,0);

}
