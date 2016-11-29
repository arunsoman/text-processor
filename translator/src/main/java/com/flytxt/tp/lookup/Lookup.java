package com.flytxt.tp.lookup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.flytxt.tp.marker.MarkerFactory;

public abstract class Lookup<T> {

    protected String fileName;
    protected MarkerFactory mf;


    public abstract void load(final byte[] key, final T val);

    public abstract void bake();

    public abstract T get(byte[] bytes);
}
