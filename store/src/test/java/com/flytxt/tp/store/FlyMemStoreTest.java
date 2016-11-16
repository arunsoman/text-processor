package com.flytxt.tp.store;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.store.FlyMemStore;

public class FlyMemStoreTest {
	Random myRandom = new Random();
	MarkerFactory mf = new MarkerFactory();

	private Marker[] generateMarkers() {
		Marker[] mArray = new Marker[10];
		int i = 0;
		for (Marker aMarker : mArray) {
			mArray[i++] = mf.createMarker(makeString());
		}

		return mArray;
	}

	private byte[] toBytes(Marker... markers) throws IOException {
		ByteArrayOutputStream bOs = new ByteArrayOutputStream();
		boolean ifComma = false;
		for (Marker aMarker : markers) {
			if (ifComma)
				bOs.write(",".getBytes());
			bOs.write(aMarker.getData(), aMarker.index, aMarker.length);
			ifComma = true;
		}

		bOs.write("\n".getBytes());
		return bOs.toByteArray();
	}

	private String makeString() {
		String str = new String((new byte[] { (byte) (myRandom.nextInt(26) + 'A'), (byte) (myRandom.nextInt(26) + 'a'),
				(byte) (myRandom.nextInt(26) + 'a') }));
		return str;
	}

	FlyMemStore mStore;

	@Before
	public void init() throws FileNotFoundException, IOException {
		mStore = FlyMemStore.getSingletonInstance();
	}

	@Test
	public void unitTestW1R1() throws IOException {
		Marker[] ms = generateMarkers();
		byte[] data = toBytes(ms);
		mStore.write(ms);
		byte[] d2 = mStore.read();
		Assert.assertArrayEquals(data, d2);

	}

	@Test
	public void unitTestWManayR1() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			for (int i = 0; i < 10; i++) {
				Marker[] ms = generateMarkers();
				byte[] data = toBytes(ms);
				mStore.write(ms);
				bos.write(data);
			}
			byte[] d2 = mStore.read();
			byte[] actual = bos.toByteArray();
			Assert.assertArrayEquals(actual, d2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void unitTestWRMany() throws IOException {
		for (int i = 0; i < 10; i++) {
			Marker[] ms = generateMarkers();
			byte[] data = toBytes(ms);
			mStore.write(ms);
			byte[] d2 = mStore.read();
			Assert.assertArrayEquals(data, d2);
		}

	}

}
