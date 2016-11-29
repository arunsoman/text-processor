package com.flytxt.tp.fileutils.loader;

import com.flytxt.tp.lookup.Lookup;

public interface Loader <T>{
	void load(Lookup<T> lookup);
}
