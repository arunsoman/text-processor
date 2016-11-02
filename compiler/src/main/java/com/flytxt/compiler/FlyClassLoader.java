package com.flytxt.compiler;

import java.util.HashMap;
import java.util.Map;

public class FlyClassLoader extends ClassLoader {

    private static Map<String, CompiledCode> customCompiledCode = new HashMap<>();

    public FlyClassLoader(ClassLoader parent) {
        super(parent);
    }

    public void setCode(CompiledCode cc) {
        customCompiledCode.put(cc.getName(), cc);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.gc();
        CompiledCode cc = customCompiledCode.get(name);
        if (cc == null)
            return super.findClass(name);
        byte[] byteCode = cc.getByteCode();
        Class<?> classN = defineClass(name, byteCode, 0, byteCode.length);
        return classN;
    }

    protected byte[] getBytes(String name) throws ClassNotFoundException {
        CompiledCode cc = customCompiledCode.get(name);
        if (cc == null)
            return null;
        return cc.getByteCode();
    }

}
