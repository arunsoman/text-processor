package com.flytxt.tp.translator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import com.flytxt.tp.marker.Marker;
public class TpDateUtil {

    /**
     *
Letter	Date or Time Component	Presentation	Examples
G	Era designator	Text	AD
y	Year	Year	1996; 96
Y	Week year	Year	2009; 09
M	Month in year	Month	July; Jul; 07
w	Week in year	Number	27
W	Week in month	Number	2
D	Day in year	Number	189
d	Day in month	Number	10
F	Day of week in month	Number	2
E	Day name in week	Text	Tuesday; Tue
u	Day number of week (1 = Monday, ..., 7 = Sunday)	Number	1
a	Am/pm marker	Text	PM
H	Hour in day (0-23)	Number	0
k	Hour in day (1-24)	Number	24
K	Hour in am/pm (0-11)	Number	0
h	Hour in am/pm (1-12)	Number	12
m	Minute in hour	Number	30
s	Second in minute	Number	55
S	Millisecond	Number	978
z	Time zone	General time zone	Pacific Standard Time; PST; GMT-08:00
Z	Time zone	RFC 822 time zone	-0800
X	Time zone	ISO 8601 time zone	-08; -0800; -08:00
     */

    public static final String flyDateFormat = "ddMMyyyy HH:mm:ss.S";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(flyDateFormat);
    public static final int flyDateFormatSize = flyDateFormat.length();
    public static HashMap<Integer, Integer> validChars = new HashMap<>();
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
    class Translator{
        int[][]plan;
        SimpleDateFormat srcFmt;
        public Translator(int[][] plan) {
            super();
            this.plan = plan;
        }
        public Translator(SimpleDateFormat srcFmt) {
            super();
            this.srcFmt = srcFmt;
        }
        public byte[] translate(Marker src, byte[] des) throws ParseException{
            return (srcFmt == null)?translate(src, des, plan):translate(src, des, srcFmt);
        }
        private byte[] translate(Marker src, byte[] des,SimpleDateFormat srcfmt) throws ParseException{
            return sdf.format(srcfmt.parse(new String(src.getData(), src.index, src.length))).getBytes();
        }
        private byte[] translate(Marker srcM, byte[] des,int[][]plan){
            if(des == null|| des.length != flyDateFormatSize)
                des = new byte[flyDateFormatSize];
            byte[] src = new byte[srcM.length];
            System.arraycopy(srcM.getData(), srcM.index, src, 0, srcM.length);
            for(int i=0; i < 6; i++)
                System.arraycopy(src, plan[i][0], des, plan[i][1], plan[i][2]);
            return des;
        }
    }
    class CoOccur{
        CharCnt[] list = new CharCnt[6];
        int index;
        void add(char aChar, int loc, int cnt) throws ParseException{
            Integer val = validChars.get((int)aChar);
            int desLoc =0;
            if(val == null ||(val != cnt&& val !=-2 )){
                System.out.println("not found : "+aChar);
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
        int[][] toPlan(){
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
        class CharCnt{
            char aChar;
            int desLoc;
            int loc;
            int cnt;
            public CharCnt(char aChar, int desLoc, int loc, int cnt) {
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
    }

    public Translator Formater(String format){
        char[] charArray = format.toCharArray();
        CoOccur coOccur = new CoOccur();
        char preChar = charArray[0];
        int cnt =0;
        int loc =0;
        int index =0;
        try{
            for(char c: charArray){

                if(c == preChar)
                    cnt++;
                else{
                    coOccur.add(preChar,loc,cnt);
                    preChar = c;
                    cnt = 1;
                    loc = index;
                }
                index++;
            }
            coOccur.add(preChar,loc,cnt);
        }catch (Exception e) {
            return new Translator(new SimpleDateFormat(format));
        }
        return new Translator(coOccur.toPlan());
    }

    public Date parse(String string) throws ParseException {
        return sdf.parse(string);
    }
}
