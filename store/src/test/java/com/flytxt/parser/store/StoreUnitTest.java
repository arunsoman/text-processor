package com.flytxt.parser.store;

public class StoreUnitTest {
/*
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
      //      /*
            MappedBusReader reader = new MappedBusReader("/tmp/test", 100000L, 32);
            MarkerSerializer mserial = new MarkerSerializer();
            reader.open();
            if (reader.readType() == MarkerSerializer.TYPE)
                reader.readMessage(mserial);
                
            System.out.println(mserial);
            reader.close();
        //    
        } catch (final IOException e) {
            fail(e.getMessage());
        } finally {
            File testFile = new File("/tmp/test");
            testFile.delete();

        }
    }
    */
}
