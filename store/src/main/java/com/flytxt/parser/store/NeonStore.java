package com.flytxt.parser.store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Semaphore;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.springframework.data.hadoop.store.output.OutputStreamWriter;
import org.springframework.data.hadoop.store.strategy.naming.RollingFileNamingStrategy;

import com.flytxt.parser.marker.Marker;

public class NeonStore implements Store {

    private MappedByteBuffer out;

    private MappedByteBuffer meta;

    private Semaphore semaphore = new Semaphore(1);

    private OutputStreamWriter writer;

    public NeonStore(String folderName, String... headers) throws FileNotFoundException, IOException {
        out = new RandomAccessFile("hadoopData.dat", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 128 * 1024 * 1024);
        meta = new RandomAccessFile("hadoopMeta.dat", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 8);

        Path path = new Path("/tmp/output");
        Configuration config = new Configuration();
        RollingFileNamingStrategy fileNamingStrategy = new RollingFileNamingStrategy().createInstance();

        writer = new OutputStreamWriter(config, path, null);
        writer.setFileNamingStrategy(fileNamingStrategy);
    }

    @Override
    public void save(byte[] data, String fileName, Marker... markers) throws IOException {
        try {
            semaphore.acquire();
            int lastWrite = getLastWriteIndex();
            out.put(data);
            writeToHdfs(0, lastWrite);
            updateLastWrite(lastWrite + data.length);
            semaphore.release();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void writeToHdfs(int startIndex, int lastIndex) throws IOException {
        byte[] data = new byte[128 * 1024 * 1024];
        out.get(data, startIndex, lastIndex - startIndex);
        writer.write(data);
    }

    private void updateLastWrite(int i) {
        meta.putInt(4, i);
    }

    private int getLastWriteIndex() {
        return meta.getInt(0);
    }

    @Override
    public void set(String fileName) {
        // TODO Auto-generated method stub
    }

    @Override
    public String done() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
