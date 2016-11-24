package com.flytxt.tp.store;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.store.FlyMemStore;

public class FlyMemStoreTest {
	Random myRandom = new Random();
	MarkerFactory mf = new MarkerFactory();

	private Marker[] generateMarkers() {
		final Marker[] mArray = new Marker[10];
		int i = 0;
		for (final Marker aMarker : mArray) {
			mArray[i++] = mf.createMarker(makeString());
		}

		return mArray;
	}

	private byte[] toBytes(final Marker... markers) throws IOException {
		final ByteArrayOutputStream bOs = new ByteArrayOutputStream();
		boolean ifComma = false;
		for (final Marker aMarker : markers) {
			if (ifComma) {
				bOs.write(",".getBytes());
			}
			bOs.write(aMarker.getData(), aMarker.index, aMarker.length);
			ifComma = true;
		}

		bOs.write("\n".getBytes());
		return bOs.toByteArray();
	}

	private String makeString() {
		final String str = new String(new byte[] { (byte) (myRandom.nextInt(26) + 'A'), (byte) (myRandom.nextInt(26) + 'a'),
				(byte) (myRandom.nextInt(26) + 'a') });
		return str;
	}

	FlyMemStore mStore;

	@Before
	public void init() throws FileNotFoundException, IOException {

		mStore = MemStrorePool.getSingletonInstance(1).getMemStore("/test/folder");
	}

	@Test
	public void unitTestW1R1() throws IOException {
		final Marker[] ms = generateMarkers();
		final byte[] data = toBytes(ms);
		mStore.write(ms);
		final byte[] d2 = mStore.read();
		Assert.assertArrayEquals(data, d2);

	}

	@Test
	public void unitTestWManayR1() {
		try {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			for (int i = 0; i < 10; i++) {
				final Marker[] ms = generateMarkers();
				final byte[] data = toBytes(ms);
				mStore.write(ms);
				bos.write(data);
			}
			final byte[] d2 = mStore.read();
			final byte[] actual = bos.toByteArray();
			Assert.assertArrayEquals(actual, d2);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void unitTestWRMany() throws IOException {
		for (int i = 0; i < 10; i++) {
			final Marker[] ms = generateMarkers();
			final byte[] data = toBytes(ms);
			mStore.write(ms);
			final byte[] d2 = mStore.read();
			Assert.assertArrayEquals(data, d2);
		}

	}

}
