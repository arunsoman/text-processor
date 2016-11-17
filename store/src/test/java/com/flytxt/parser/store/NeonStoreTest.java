package com.flytxt.parser.store;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.store.NeonStore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestSpringApplciation.class})
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
		BufferedReader reader = new BufferedReader(new FileReader("app/test/testFile"));
		String string = null;
		while ((string = reader.readLine()) != null) {
			String[] split = string.split(",");
			Marker[] markerArray = new Marker[split.length];
			int i = 0;
			for (String str : split) {
				Marker createMarker = mf.createMarker(str);
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
