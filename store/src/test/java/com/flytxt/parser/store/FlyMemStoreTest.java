package com.flytxt.parser.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.http.util.Asserts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.store.FlyMemStore;

import cucumber.runtime.junit.Assertions;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSpringApplciation.class })
public class FlyMemStoreTest {

	private FlyMemStore store;
	private MarkerFactory mf;
	private byte[] actual ;

	@Before
	public void before() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		store = new FlyMemStore();
		mf = new MarkerFactory();
		MessageDigest md1 = MessageDigest.getInstance("MD5");
		md1.update(Files.readAllBytes(Paths.get("app/test/testFile")));
		actual = md1.digest();
	}

	@Test
	public void testReadAndWrite() throws IOException, NoSuchAlgorithmException {
		File temp =File.createTempFile("testFile", null);
		temp.deleteOnExit();
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileOutputStream fos = new FileOutputStream(temp);
		try (BufferedReader reader = new BufferedReader(new FileReader("app/test/testFile"))) {
			String string = null;
			while ((string = reader.readLine()) != null) {
				String[] split = string.split(",");
				Marker[] markerArray = new Marker[split.length];
				int i = 0;
				for (String str : split) {
					Marker createMarker = mf.createMarker(str);
					markerArray[i++] = createMarker;
				}
				save(fos, markerArray);
			}
		}
		byte[] read = store.read();
		fos.write(read);
		fos.flush();
		fos.close();
		md.update(Files.readAllBytes(Paths.get(temp.getPath())));
		
		
		
		Assert.assertArrayEquals(md.digest(), actual);

	}

	private void save(FileOutputStream fos, Marker[] markerArray) throws IOException {
		try {
			store.write(markerArray);
		} catch (ArrayIndexOutOfBoundsException e) {
			byte[] read = store.read();
			fos.write(read);
			save(fos, markerArray);
		}
	}

}
