package com.flytxt.tp.store;

import java.io.IOException;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerSerDer;
import com.flytxt.tp.store.chronicle.ChronicleWriter;
import com.flytxt.tp.store.chronicle.StreamData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamStore implements Store {

    private MarkerSerDer serDer = new MarkerSerDer();

    private ChronicleWriter<StreamData> queueWriter;

    private StreamData streamData = new StreamData();

    private String baseDir = "/tmp/tx-processor";

    private String fileName = "";

    public StreamStore(String baseDir, String... args) {
        super();
        this.baseDir = baseDir.endsWith("/") ? baseDir : baseDir + "/";
        this.queueWriter = new ChronicleWriter<>(this.baseDir);
        queueWriter.init();
    }

    @Override
    public void set(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public synchronized void save(byte[] data, String fileName, Marker... markers) throws IOException {
        byte[] serData = serDer.toBytes(markers);
        streamData.setBytes(serData);
        queueWriter.write(streamData);
    }

    @Override
    public String done() throws IOException {
        log.info("[StreamStore|done|File processed: {}]", fileName);
        return null;
    }

	@Override
	public void preDestroy() {
		// TODO Auto-generated method stub
		
	}
}
