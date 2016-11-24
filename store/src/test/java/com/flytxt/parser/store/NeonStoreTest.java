package com.flytxt.parser.store;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.store.NeonStore;


public class NeonStoreTest {

	@Autowired
	private NeonStore store;
	private MarkerFactory mf;

	@Before
	public void before() throws FileNotFoundException, IOException, InterruptedException {
		mf = new MarkerFactory();

	}

	@Test
	public void test() throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader("app/test/testFile"));
		String string = null;
		while ((string = reader.readLine()) != null) {
			final String[] split = string.split(",");
			final Marker[] markerArray = new Marker[split.length];
			int i = 0;
			for (final String str : split) {
				final Marker createMarker = mf.createMarker(str);
				markerArray[i++] = createMarker;

			}
			store.save(string.getBytes(), "test", markerArray);
		}
		reader.close();
	}

	@After
	public void after() throws IOException {
		store.done();
	}
}
