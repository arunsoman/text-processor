package com.flytxt.tp.processor.filefilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * File Filter implementation.
 */
public class FlyFileFilter implements DirectoryStream.Filter<Path>{

	/** filter chain builder for getting the filter chain  */
	@Autowired
	private FilterChainBuilder builder;
	
	/** Filter chain */
	private FilterChain filterChain;
	 
	/** For Keeping the filter applied File list*/
	private File [] filteredFiles ;
	
	/** Logger */
	private final Logger logger = LoggerFactory.getLogger(FlyFileFilter.class);
	
	
	
	/**
	 * 
	 * @param parentFolder
	 * @param filterName
	 */
	public void set(String parentFolder,String filterName) {
		this.filterChain = builder.getFilterChainByName(filterName);
		filteredFiles = this.doFilter(parentFolder);				
	}


	/**
	 * Iterate over all the filtered file, The input path is match with any 
	 * of these file method will return true. 
	 * 
	 * If there is no filter is configured,  then the method return true . 	 * 
	 * All the other scenarios method return false 
	 */
	@Override
	public boolean accept(Path pathname) {		
		if(null!=filteredFiles){
			for(File file:filteredFiles){
				if(null!=file){
					if(pathname.getFileName().toString().equals(file.getName())){
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}
	
	

	/**
	 * Return all the files in the given directory 
	 * @param parentFolder : Directory Path  as String 
	 */
	private File[] getFiles(String parentFolder) {
		try{			
			DirectoryStream<Path> directoryStream =  Files.newDirectoryStream(Paths.get(parentFolder));	
			List<File> files = new ArrayList<>(); 
			for(Path path:  directoryStream){
				files.add(path.toFile());
			}
			return  files.toArray(new File[0] );
		} catch (IOException e) {
			logger.error("Unable to perform the Directory read " + e.getMessage());
		}
		return null;
	}
	

	/**
	 *  For applying the filter.
	 * @return File array which apply the filter.
	 */
	private File [] doFilter(String parentFolder){		
		File[] files = getFiles(parentFolder);
		if(null!= filterChain && null!=files){  
			return filterChain.canProcess(files);				
		}
		return null;
	}
}
