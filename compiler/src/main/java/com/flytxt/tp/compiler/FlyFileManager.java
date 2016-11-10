package com.flytxt.tp.compiler;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

public class FlyFileManager  extends ForwardingJavaFileManager<JavaFileManager>{
	private CompiledCode compiledCode;
    private FlyClassLoader cl;

    protected FlyFileManager(JavaFileManager  fileManager, CompiledCode compiledCode, FlyClassLoader cl) {
        super(fileManager);
        this.compiledCode = compiledCode;
        this.cl = cl;
        this.cl.setCode(compiledCode);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(FlyFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        return compiledCode;
    }

    @Override
    public ClassLoader getClassLoader(FlyFileManager.Location location) {
        return cl;
    }
}
