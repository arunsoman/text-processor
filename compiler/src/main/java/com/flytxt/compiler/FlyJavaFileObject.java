package com.flytxt.compiler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

public class FlyJavaFileObject implements JavaFileObject{
	
	private URI uri;
	private String name;
	private String content;
	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	
	public FlyJavaFileObject(String fileName, String content) {
		this.name = name;
		this.content = content;
	}
	@Override
	public InputStream openInputStream() throws IOException {
		return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		return byteArrayOutputStream;
	}

	@Override
	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		return new StringReader(content);
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		CharSequence cs = content;
		return cs;
	}

	@Override
	public Writer openWriter() throws IOException {
		return new StringWriter();
	}

	@Override
	public long getLastModified() {
		return System.currentTimeMillis();
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public Kind getKind() {
		return Kind.SOURCE;
	}

	@Override
	public boolean isNameCompatible(String simpleName, Kind kind) {
		return true;
	}

	@Override
	public NestingKind getNestingKind() {
		return NestingKind.TOP_LEVEL;
	}

	@Override
	public Modifier getAccessLevel() {
		// TODO Auto-generated method stub
		return Modifier.PUBLIC;
	}

	@Override
	public URI toUri() {
		try {
			return new URI("./"+name);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}return null;
	}

	@Override
	public String getName() {
		return name;
	}

}
