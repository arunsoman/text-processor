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


@Ignore
public class FlyMemStoreTest {
	Random myRandom = new Random();
	MarkerFactory mf = new MarkerFactory();
	Marker newLine = mf.createMarker("\n");

	private Marker[] generateMarkers() {
		Marker[] mArray = new Marker[10];
		for (Marker aMarker : mArray) {
			aMarker = mf.createMarker(makeString());
		}
		mArray[mArray.length - 1] = newLine;
		return mArray;
	}

	private byte[] toBytes(Marker... markers) {
		ByteArrayOutputStream bOs = new ByteArrayOutputStream();

		for (Marker aMarker : markers) {
			bOs.write(aMarker.getData(), aMarker.index, aMarker.length);
		}
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
	public void unitTestW1R1() {
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
	public void unitTestWRMany() {
		for (int i = 0; i < 10; i++) {
			Marker[] ms = generateMarkers();
			byte[] data = toBytes(ms);
			mStore.write(ms);
			byte[] d2 = mStore.read();
			Assert.assertArrayEquals(data, d2);
		}

	}

}
