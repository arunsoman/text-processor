package com.flytxt.parser.processor;


import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainWatch {

	public static void watchDirectoryPath(Path path) {
		// Sanity check - Check if path is a folder
		try {
			Boolean isFolder = (Boolean) Files.getAttribute(path,
					"basic:isDirectory", NOFOLLOW_LINKS);
			if (!isFolder) {
				throw new IllegalArgumentException("Path: " + path + " is not a folder");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		log.info("Watching path: " + path);
		
		// We obtain the file system of the Path
		FileSystem fs = path.getFileSystem ();
		
		// We create the new WatchService using the new try() block
		try(WatchService service = FileSystems.getDefault().newWatchService()) {
			
			// We register the path to the service
			// We watch for creation events
			path.register(service, ENTRY_CREATE);
			Paths.get("/tmp").register(service, ENTRY_CREATE);
			
			// Start the infinite polling loop
			WatchKey key = null;
			while(true) {
				key = service.take();
				
				// Dequeueing events
				Kind<?> kind = null;
				for(WatchEvent<?> watchEvent : key.pollEvents()) {
					// Get the type of the event
					kind = watchEvent.kind();
					if (OVERFLOW == kind) {
						continue; //loop
					} else if (ENTRY_CREATE == kind) {
						// A new Path was created 
						Path newPath = ((WatchEvent<Path>) watchEvent).context();
						// Output
						log.info("New path created: " + newPath);
					}
				}
				
				if(!key.reset()) {
					break; //loop
				}
			}
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// Folder we are going to watch
		Path folder = Paths.get(System.getProperty("user.home"));
		log.info("...->"+System.getProperty("user.home"));
		watchDirectoryPath(folder);
	}
}