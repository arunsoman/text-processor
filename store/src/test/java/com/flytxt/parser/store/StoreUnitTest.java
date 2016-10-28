package com.flytxt.parser.store;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerDefaultConfig;
import com.flytxt.parser.marker.MarkerFactory;

import lombok.Getter;
import lombok.Setter;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={
		MarkerDefaultConfig.class,
		StoreDefaultConfig.class
})
public class StoreUnitTest {

	@Autowired
	@Getter @Setter
	MarkerFactory markerFactory;
	
    
    @Test
    public void test() {
        final String aon = "aon";
        final String age = "age";
        final Store store = new LocalFileStore();
        
        store.set("/tmp/out/","sample.txt", aon, age);
        final String str = "10,twenty";
        final byte[] strB = str.getBytes();
        markerFactory.setMaxListSize(str.split(",").length);
        final Marker line = markerFactory.createMarker(strB,0, strB.length - 1);
        final List<Marker> ms = line.splitAndGetMarkerList((",").getBytes(), markerFactory);
        final Marker aonM = ms.get(1);
        final Marker ageM = ms.get(2);
        try {
            store.save(strB, "testFile", aonM, ageM);
            store.done();
        } catch (final IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void streamStoreTest() {
        final String aon = "aon";
        final String age = "age";
        final Store store = new StreamStore();
        store.set("/tmp/test","streamStore.txt", aon, age);
        final String str = "10,twenty";
        final byte[] strB = str.getBytes();
        markerFactory.setMaxListSize(str.split(",").length);
        final Marker line = markerFactory.createMarker(strB,0, strB.length - 1);
        final List<Marker> ms = line.splitAndGetMarkerList( ",".getBytes(), markerFactory);
        final Marker aonM = ms.get(1);
        final Marker ageM = ms.get(2);
        try {
            store.save(strB, "testFile", aonM, ageM);
            store.done();
            /*
            MappedBusReader reader = new MappedBusReader("/tmp/test", 100000L, 32);
            MarkerSerializer mserial = new MarkerSerializer();
            reader.open();
            if (reader.readType() == MarkerSerializer.TYPE)
                reader.readMessage(mserial);
                
            System.out.println(mserial);
            reader.close();
            */
        } catch (final IOException e) {
            fail(e.getMessage());
        } finally {
            File testFile = new File("/tmp/test");
            testFile.delete();

        }
    }
}
