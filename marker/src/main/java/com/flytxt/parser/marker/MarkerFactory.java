package com.flytxt.parser.marker;

public final class MarkerFactory {

	private final FlyPool<Marker> markerPool = new FlyPool<Marker>();
	private final FlyPool<ImmutableMarker> markerImmutablePool = new FlyPool<ImmutableMarker>();
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
		} else
			m.setLineAttribute(index, length);
		return m;
	}
}