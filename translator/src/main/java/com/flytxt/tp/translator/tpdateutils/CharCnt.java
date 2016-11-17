package com.flytxt.tp.translator.tpdateutils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor
@ToString
class CharCnt{
   final char aChar;
   final int desLoc;
   final int srcLoc;
   final int cnt;
}
