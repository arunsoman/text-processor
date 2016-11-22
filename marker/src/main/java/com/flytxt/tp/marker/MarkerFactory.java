package com.flytxt.tp.marker;

import lombok.Getter;

public final class MarkerFactory {

	private final FlyPool<Marker> markerPool = new FlyPool<Marker>();

	@Getter
	private CurrentObject currentObject = new CurrentObject();

	private Marker lineMarker;

	public Marker createMarker(int val) {
		return createMarker(String.valueOf(val));
	}
	public Marker createMarker(long val) {
		return createMarker(String.valueOf(val));
	}
	public Marker createMarker(double val) {
		return createMarker(String.valueOf(val));
	}
	public Marker createMarker(String str) {
		byte[] data = str.getBytes();
		return createMarker(data, 0, data.length);
	}

	public Marker createMarker(byte[] data, int index, int length) {
		return (data == null) ? create(index, length) : createImmutable(data, index, length);
	}

	public void reclaim() {
		markerPool.reset();
	}

	public Marker getLineMarker() {
		lineMarker = (lineMarker == null ? new Marker(currentObject) : lineMarker);
		lineMarker.setLineAttribute(currentObject.getIndex(), currentObject.getLength());
		return lineMarker;
	}

	private Marker createImmutable(byte[] data, int index, int length) {
		Marker m = markerPool.peek();
		if (m == null) {
			m = new Marker(data, index, length);
			markerPool.add(m);
		}
		m.setData(data, index, length);
		return m;
	}

	private Marker create(final int index, final int length) {
		Marker m = markerPool.peek();
		if (m == null) {
			m = new Marker(currentObject);
			markerPool.add(m);
		}
		m.setData(null,index, length);
		return m;
	}

	int getMarkerPoolSize() {
		return markerPool.getSize();
	}

	public CurrentObject getCurrentObject() {
		return currentObject;
	}
}