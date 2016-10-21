package com.flytxt.parser.marker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarkerFactory {

    private final FlyPool<Marker> markerPool = new FlyPool<>();

    private final FlyPool<FlyList<Marker>> markerListPool = new FlyPool<>();

    private final FlyPool<ImmutableMarker> markerImmutablePool = new FlyPool<>();

    private final FlyPool<FlyList<ImmutableMarker>> markerImmutableListPool = new FlyPool<>();

    private int reused;

    private int created;

    private int reusedList;

    private int createdList;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int listSize;

    public int getListSize() {
        return listSize;
    }

    public Marker create(final int index, final int length) {
        Marker m = markerPool.peek();
        if (m == null) {
            m = new Marker();
            markerPool.add(m);
            created++;
        } else
            reused++;
        m.index = index;
        m.length = length;
        return m;
    }

    public void reclaim() {
        markerPool.reset();
        markerListPool.reset();
        markerImmutablePool.reset();
        markerImmutableListPool.reset();
    }

    public void printStat() {
        logger.debug("Markers reused: " + reused + " created: " + created);
        logger.debug("List reused: " + reusedList + " created:" + createdList);
    }

    public FlyList<Marker> getArrayList() {
        FlyList<Marker> list = markerListPool.peek();
        if (list == null) {
            list = new FlyList<>(listSize);
            markerListPool.add(list);
            createdList++;
        } else {
            list.clear();
            reusedList++;
        }
        return list;
    }

    public void setMaxListSize(final int maxListSize) {
        listSize = maxListSize;
    }

    public ImmutableMarker createImmutable(byte[] data, int index, int length) {
        ImmutableMarker m = markerImmutablePool.peek();
        if (m == null) {
            m = new ImmutableMarker(data);
            markerImmutablePool.add(m);
            created++;
        } else
            reused++;
        m.index = index;
        m.length = length;
        return m;
    }
}