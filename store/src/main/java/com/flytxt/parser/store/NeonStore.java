package com.flytxt.parser.store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.Semaphore;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.data.hadoop.store.output.OutputStreamWriter;
import org.springframework.data.hadoop.store.strategy.naming.RollingFileNamingStrategy;

import com.flytxt.parser.marker.Marker;

public class NeonStore implements Store {

    // @Override
    // public void set(String fileName) {
    // // TODO Auto-generated method stub
    //
    // }
    //
    // @Override
    // public void save(byte[] data, String fileName, Marker... markers) throws IOException {
    // // TODO Auto-generated method stub
    //
    // }
    //
    // @Override
    // public String done() throws IOException {
    // // TODO Auto-generated method stub
    // return null;
    // }

    private MappedByteBuffer out;

    // read and write indexes are stored in this buffer in ((int)readIndex, (int)writeIndex) format
    private MappedByteBuffer meta;

    private Semaphore semaphore = new Semaphore(1);

    private OutputStreamWriter writer;

    private static final int bufSize = 128 * 1024 * 1024;

    private byte[] data = new byte[bufSize];

    private UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");

    @SuppressWarnings("resource")
    public NeonStore(String folderName, String... headers) throws FileNotFoundException, IOException {
        out = new RandomAccessFile("hadoopData.dat", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, bufSize);
        meta = new RandomAccessFile("hadoopMeta.dat", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 8);

        Path path = new Path("/tmp/output");
        Configuration config = new Configuration();
        // Hadoop configurations go here
        config.addResource(new Path("/tmp/hdfs-site.xml"));
        config.addResource(new Path("/tmp/core-site.xml"));
        RollingFileNamingStrategy fileNamingStrategy = new RollingFileNamingStrategy().createInstance();

        writer = new OutputStreamWriter(config, path, null);
        writer.setFileNamingStrategy(fileNamingStrategy); // rollingStrategy to be tested}

    }

    @Override
    public void save(byte[] data, String fileName, Marker... markers) throws IOException {
        try {
            semaphore.acquire();
            if (out.position() + data.length > bufSize) // checking for bufSize boundary
                writeToHdfs();
            out.put(data);
            meta.putInt(4, out.position()); // update writeIndex in meta
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void writeToHdfs() throws IOException {
        int lastReadIndex = meta.getInt(0), lastWriteIndex = meta.getInt(4);
        out.get(data, lastReadIndex, lastWriteIndex - lastReadIndex);
        try {
            ugi.doAs(new PrivilegedExceptionAction<Void>() {

                @Override
                public Void run() throws Exception {
                    data = "test this thing".getBytes();
                    writer.write(data);
                    writer.close();
                    return null;
                }
            });
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        meta.putInt(0, lastWriteIndex + 1); // update readIndex in meta
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

    public static void main(String[] args) {
        try {
            NeonStore ns = new NeonStore("", "");
            ns.writeToHdfs();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
