package com.flytxt.tp.translator.tpdateutils;

class CharCnt{
    char aChar;
    int desLoc;
    int loc;
    int cnt;
     CharCnt(char aChar, int desLoc, int loc, int cnt) {
        super();
        this.aChar = aChar;
        this.desLoc = desLoc;
        this.cnt = cnt;
        this.loc = loc;
    }
    @Override
    public String toString() {
        return String.format("CC [char=%s, desLoc=%s, loc=%s, cnt=%s]\n", aChar, desLoc, loc, cnt);
    }

}
