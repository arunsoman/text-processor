package com.flytxt.tp.processor.filefilter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author shiju.john
 *
 */
public class FlyFileFilterTest {
	
	FlyFileFilter fileFilter = null;
	@Before
	public void init(){
		fileFilter = new FlyFileFilter();
		
		FilterChainBuilder chainbuilder = new FilterChainBuilder();	
		setFilterParameterMap(chainbuilder);
		Map<String, String> filterValues = new HashMap<>();
		filterValues.put("Filter_Name1", ""
				+ "com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter,"
				+ "com.flytxt.tp.processor.filefilter.RegexFilter,");
		chainbuilder.setFilterNameMap(filterValues);
		chainbuilder.build();
		
		Field chainBuilderField;
		try {
			chainBuilderField = FlyFileFilter.class.getDeclaredField("builder");
			chainBuilderField.setAccessible(true);
			chainBuilderField.set(fileFilter, chainbuilder);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
			
	}	
	
		
	private void setFilterParameterMap(FilterChainBuilder chainbuilder) {
		try {
			
			FilterParameters filterParameters = new FilterParameters();
			Map<String, String> parameterMap = new HashMap<>();
			parameterMap.put("com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter", "0");
			parameterMap.put("com.flytxt.tp.processor.filefilter.RegexFilter", ".*\\.txt");
			Map<String,Map<String,String>> filterNamedMap =  new HashMap<>();
			filterNamedMap.put("Filter_Name1", parameterMap);
			filterParameters.setArgMap(filterNamedMap);			
			
			Field filterParametersField = FilterChainBuilder.class.getDeclaredField("filterParameters");
			filterParametersField.setAccessible(true);
			
			filterParametersField.set(chainbuilder, filterParameters);
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}	
	}
	
	

	@Test
	public void testSet(){
		fileFilter.set(".","Filter_Name1");			
		FilterChain filterChain = getFilterChain();
		Assert.assertNotNull(filterChain);
		Assert.assertNotNull(filterChain.nextLink);
	}
	
	@Test
	public void testDirectoryScan(){
		
		File file = new File("testdir"+File.separator+"TestFile1.txt");
		File file1 = new File("testdir"+File.separator+"TestFile2.csv");
		File dir =null;		
		try {
			
			String pathString = file1.getAbsolutePath().substring(0,file1.getAbsolutePath().lastIndexOf(File.separator));
			dir = new File(pathString);
			dir.mkdir();
			file.createNewFile();
			file1.createNewFile();
			fileFilter.set(pathString,"Filter_Name1");	
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(pathString),fileFilter);
			 for (final Path path : directoryStream) {
				 Assert.assertNotEquals(file1.getName(),path.getFileName().toString());
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}finally {			
			file1.delete();
			file.delete();
			dir.delete();
		}
		
	}


	private FilterChain getFilterChain() {
		Field filterParametersField;
		try {
			filterParametersField = FlyFileFilter.class.getDeclaredField("filterChain");
			filterParametersField.setAccessible(true);			
			return (FilterChain)filterParametersField.get(fileFilter);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		
	}


}
