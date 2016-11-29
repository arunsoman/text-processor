package com.flytxt.tp.fileutils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

public interface Channel extends  AutoCloseable {
	BufferedReader open(String fileName) throws FileNotFoundException;
}
