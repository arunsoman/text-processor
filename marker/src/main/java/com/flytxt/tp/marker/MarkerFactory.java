package com.flytxt.tp.marker;

import lombok.Getter;

public final class MarkerFactory {

	private final FlyPool<Marker> markerPool = new FlyPool<Marker>();

	@Getter
	private CurrentObject currentObject = new CurrentObject();

	private Marker lineMarker;

	public Marker createMarker(int val) {
		return create(Marker.longDataType, val, 0, null, 0, 0);
	}

	public Marker createMarker(long val) {
		return create(Marker.longDataType, val, 0, null, 0, 0);
	}

	public Marker createMarker(double val) {
		return create(Marker.doubleDataType, 0, val, null, 0, 0);
	}

	public Marker createMarker(String str) {
		byte[] data = str.getBytes();
		return create(Marker.localDataType, 0, 0, data, 0, data.length);
	}

	public Marker createMarker(byte[] data, int index, int length) {
		return create((data == null) ? Marker.lineDataType : Marker.localDataType,
				0, 0, data, index, length);
	}

	public void reclaim() {
		markerPool.reset();
	}

	public Marker getLineMarker() {
		lineMarker = (lineMarker == null ? new Marker(currentObject) : lineMarker);
		lineMarker.set(currentObject.getIndex(), currentObject.getLength());
		return lineMarker;
	}

	private Marker create(int dataType, long lvalue, double dvalue, byte[] data, final int index, final int length) {
		Marker m = markerPool.peek();
		if (m == null) {
			m = from(dataType, lvalue, dvalue, data, index, length);
			markerPool.add(m);
		} else {
			m.reset();
			set(m, dataType, lvalue, dvalue, data, index, length);
		}
		return m;
	}

	private void set(Marker m, int dataType, long lvalue, double dvalue, byte[] data, int index, int length) {
		switch (dataType) {
		case Marker.doubleDataType:
			m.set(dvalue);
			break;
		case Marker.longDataType:
			m.set(lvalue);
			break;
		case Marker.lineDataType:
			m.index = index;
			m.length = length;
			break;
		case Marker.localDataType:
			m.set(data, index, length);
			break;
		default:
			throw new RuntimeException(dataType + " not valid data type");
		}
	}

	private Marker from(int dataType, long lvalue, double dvalue, byte[] data, int index, int length) {
		switch (dataType) {
		case Marker.doubleDataType:
			return new Marker(dvalue);
		case Marker.longDataType:
			return new Marker(lvalue);
		case Marker.lineDataType:
			Marker m = new Marker(currentObject);
			m.index = index;
			m.length = length;
			return m;
		case Marker.localDataType:
			return new Marker(data, index, length);
		default:
			throw new RuntimeException(dataType + " not valid data type");
		}
	}

	int getMarkerPoolSize() {
		return markerPool.getSize();
	}

	public CurrentObject getCurrentObject() {
		return currentObject;
	}
}