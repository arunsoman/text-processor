package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;


class CoOccur{
	private static HashMap<Integer, Integer> validChars = new HashMap<>();
    static{
        validChars.put((int)'d', 2);
        validChars.put((int)'M', 2);
        validChars.put((int)'y', 4);
        validChars.put((int)'H', 2);
        validChars.put((int)'m', 2);
        validChars.put((int)'s', 2);
        validChars.put((int)'S', 1);
        validChars.put((int)'/', -2);
        validChars.put((int)'-', -2);
        validChars.put((int)':', -2);
        validChars.put((int)' ', -2);
    }
    
    private CharCnt[] list = new CharCnt[6];
    private int index;
    
    final void add(char aChar, int loc, int cnt) throws ParseException{
        Integer val = validChars.get((int)aChar);
        int desLoc =0;
        if(val == null ||(val != cnt&& val !=-2 )){
            throw new ParseException(null, 0);
        } else
            //"ddMMyyyy HH:mm:ss.S"
            //"0123456789012345678"
            switch(aChar){
            case 'd': desLoc = 0;break;
            case 'M': desLoc = 2;break;
            case 'y': desLoc = 4;break;
            case 'H': desLoc = 9;break;
            case 'm': desLoc = 12;break;
            case 's': desLoc = 15;break;
            case 'S': desLoc = 18;break;
            default: return;
            }
        list[index++] = new CharCnt(aChar, desLoc, loc, cnt);
    }
    
    final int[][] toPlan(){
        int[][] plan = new int[6][3];
        CharCnt cc;
        for(int i = 0; i <6;  i++){
            cc = list[i];
            if(cc == null) continue;
            plan[i][0] = cc.loc;
            plan[i][1] = cc.desLoc;
            plan[i][2] = cc.cnt;
        }
        return plan;
    }
    
    @Override
    public String toString() {
        return String.format("CoOccur [list=\n%s]", Arrays.toString(list));
    }
}