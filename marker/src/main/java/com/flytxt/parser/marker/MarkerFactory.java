package com.flytxt.parser.marker;

public final class MarkerFactory {

	private final FlyPool<Router, int[]> routerPool = new FlyPool<Router, int[]>();
	private final FlyPool<Marker, byte[]> markerPool = new FlyPool<Marker, byte[]>();
	private final FlyPool<ImmutableMarker, byte[]> markerImmutablePool = new FlyPool<ImmutableMarker, byte[]>();
	private CurrentObject currentObject;

	public Marker createMarker(byte[] data, int index, int length) {
		return (data == null) ? create(index, length) : createImmutable(data, index, length);
	}

	public void reclaim() {
		markerPool.reset();
		markerImmutablePool.reset();
	}

	public void init(CurrentObject currentObject) {
		this.currentObject = currentObject;
	}

	public Marker getLineMarker(){
		return new Marker(currentObject);
	}
	
	private ImmutableMarker createImmutable(byte[] data, int index, int length) {
		ImmutableMarker m = markerImmutablePool.peek();
		if (m == null) {
			m = new ImmutableMarker(data);
			markerImmutablePool.add(m);
		} else
			m.index = index;
		m.length = length;
		return m;
	}

	private Marker create(final int index, final int length) {
		Marker m = markerPool.peek();
		if (m == null) {
			m = new Marker(currentObject);
			markerPool.add(m);
		} 
		m.setLineAttribute(index, length);
		return m;
	}
	
	public Router findRouter(int[] order){
		Router r =routerPool.find(order);
		if(r == null){
			r = new Router();
			r.set(order);
			routerPool.add(r);
		}
		return r;
	}
}