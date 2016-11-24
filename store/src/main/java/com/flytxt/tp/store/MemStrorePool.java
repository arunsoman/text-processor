package com.flytxt.tp.store;

import java.io.IOException;

public final class MemStrorePool {

	private final ThreadLocal<FlyMemStore> localMemStore =  ThreadLocal.withInitial(this::newFlyMemStore);

	private static volatile MemStrorePool instance ;

	private final int initMaxInstance;

	private int currentTotalInstance;

	protected MemStrorePool(final int count){
		initMaxInstance=count;
	}

	public static synchronized MemStrorePool getSingletonInstance(final int instancecount){
		instance= new MemStrorePool(instancecount);
		return instance;
	}

	protected static MemStrorePool getInstance(){
		if(instance ==null) {
			throw new RuntimeException("Store not initialised yet");
		}
		return instance;
	}

	public FlyMemStore getMemStore(final String folderName) throws IOException{
		final FlyMemStore store=localMemStore.get();
		if(++currentTotalInstance<=initMaxInstance) {
			store.registerMe(folderName,currentTotalInstance,initMaxInstance);
		}else{
			store.reAllocate(folderName);
		}
		return	store;
	}



	private FlyMemStore newFlyMemStore() {
		try {
			return  FlyMemStore.newInstance();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

	}




}
