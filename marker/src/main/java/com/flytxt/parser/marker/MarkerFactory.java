package com.flytxt.parser.marker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public final class MarkerFactory {

	@Autowired
	@Qualifier("markerPool")
	@Getter
	@Setter
    private  FlyPool<Marker> markerPool;

	@Autowired
	@Qualifier("markerListPool")
	@Getter
	@Setter
    private  FlyPool<FlyList<Marker>> markerListPool;

	@Autowired
	@Qualifier("markerImmutablePool")
	@Getter
	@Setter
    private  FlyPool<ImmutableMarker> markerImmutablePool;

	@Autowired
	@Qualifier("markerImmutableListPool")
	@Getter
	@Setter
    private  FlyPool<FlyList<ImmutableMarker>> markerImmutableListPool;

    private int reused;

    private int created;

    private int reusedList;

    private int createdList;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int listSize;

    @Autowired
    @Getter @Setter private ApplicationContext appContext;
    
    @Autowired
    @Getter
    private CurrentObject currentObject;
    
    public int getListSize() {
        return listSize;
    }

    private Marker create(final int index, final int length) {
        Marker m = markerPool.peek();
        if (m == null) {
        	m = (Marker)appContext.getBean("marker");
            markerPool.add(m);
            created++;
            m.setData(currentObject);
        } else
            reused++;
        m.setLineAttribute(index, length);
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
            list = appContext.getBean(FlyList.class);
            list.set(listSize);
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

    private ImmutableMarker createImmutable(byte[] data, int index, int length) {
        ImmutableMarker m = markerImmutablePool.peek();
        if (m == null) {
        	m = (ImmutableMarker)appContext.getBean("immutableMarker");
            m.setData(data);
            markerImmutablePool.add(m);
            created++;
        } else
            reused++;
        m.index = index;
        m.length = length;
        return m;
    }
    
    public Marker createMarker(byte[] data, int index, int length) {
    	return (data == null)?create(index, length):createImmutable(data, index, length);
    }
}