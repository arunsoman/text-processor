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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.store.FlyMemStore;
import com.flytxt.tp.store.MemStrorePool;


public class FlyMemStoreTest {

	private MemStrorePool pool;
	private MarkerFactory mf;
	private byte[] actual ;
	private FlyMemStore memStore ;

	@Before
	public void before() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		pool =MemStrorePool.getSingletonInstance(1);
		memStore= pool.getMemStore("/usr/local");
		mf = new MarkerFactory();
		final MessageDigest md1 = MessageDigest.getInstance("MD5");
		md1.update(Files.readAllBytes(Paths.get("app/test/testFile")));
		actual = md1.digest();
	}



	@Test
	public void testReadAndWrite() throws IOException, NoSuchAlgorithmException {
		final File temp =File.createTempFile("testFile", null);
		temp.deleteOnExit();
		final FlyMemStore memStore = pool.getMemStore("/usr/local");
		final MessageDigest md = MessageDigest.getInstance("MD5");
		final FileOutputStream fos = new FileOutputStream(temp);
		try (BufferedReader reader = new BufferedReader(new FileReader("app/test/testFile"))) {
			String string = null;
			while ((string = reader.readLine()) != null) {
				final String[] split = string.split(",");
				final Marker[] markerArray = new Marker[split.length];
				int i = 0;
				for (final String str : split) {
					final Marker createMarker = mf.createMarker(str);
					markerArray[i++] = createMarker;
				}
				save(fos, markerArray);
			}
		}
		final byte[] read = memStore.read();
		if(read !=null){
			fos.write(read);
			fos.flush();
			fos.close();
			md.update(Files.readAllBytes(Paths.get(temp.getPath())));
			Assert.assertArrayEquals(md.digest(), actual);
		}

	}

	private void save(final FileOutputStream fos, final Marker[] markerArray) throws IOException {
		try {
			memStore.write(markerArray);
		} catch (final ArrayIndexOutOfBoundsException e) {
			final byte[] read = memStore.read();
			fos.write(read);
			save(fos, markerArray);
		}
	}

}
