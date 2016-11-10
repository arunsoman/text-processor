package com.flytxt.tp.translator;

public class TpLogic implements TpConstant{
	private final int ff = (falseToken*2);
	private final int tt = (trueToken*2);
	public boolean and(boolean m1, boolean m2){
        return m1&m2;
    }
    
    public boolean or(boolean m1, boolean m2){
        return m1|m2;
    }
    
    public boolean not(boolean m1){
        return !m1;
    }
}
