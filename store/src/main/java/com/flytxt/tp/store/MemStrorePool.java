package com.flytxt.tp.store;

import java.io.IOException;

public final class MemStrorePool {

	private final ThreadLocal<FlyMemStore> localMemStore =  ThreadLocal.withInitial(this::newFlyMemStore);

	private static volatile MemStrorePool instance ;

	
	protected MemStrorePool(){
	}

	public static synchronized MemStrorePool getSingletonInstance(){
		if(instance ==null)
		instance= new MemStrorePool();
		return instance;
	}

	
	public FlyMemStore getMemStore(final String folderName) throws IOException{
		final FlyMemStore store=localMemStore.get();
		store.registerMe(folderName);
		return	store;
	}



	private FlyMemStore newFlyMemStore() {
		try {
			return  FlyMemStore.newInstance();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

	}

	
	public void destroy() throws IOException{
		FlyMemStore.close();
	}



}
