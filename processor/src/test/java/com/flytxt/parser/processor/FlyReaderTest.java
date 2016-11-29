package com.flytxt.parser.processor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.flytxt.tp.processor.FlyReader;
import com.flytxt.tp.processor.LineProcessor;
import com.flytxt.tp.processor.LineProcessorImpl;
import com.flytxt.tp.processor.filefilter.FilterChainBuilder;
import com.flytxt.tp.processor.filefilter.FilterParameters;
import com.flytxt.tp.processor.filefilter.FlyFileFilter;

import test.TestScript;

public class FlyReaderTest {
	private static final String DATA_FILE_PATH ="src"+File.separator+"test"+File.separator+"resources"+File.separator+"test-data";
	FlyReader fr = new FlyReader();
	Method processFile;
	java.nio.file.Path path;
	@Before
	public void init(){
		final LineProcessor lp = new TestScript();
		lp.getMf().getCurrentObject().init("", "");
		lp.init("TestScript", System.currentTimeMillis());
		fr.set("DummyFolder", lp, null);
		try {
			processFile = fr.getClass().getDeclaredMethod("processFile", Path.class);
			processFile.setAccessible(true);

			final ClassLoader classLoader = getClass().getClassLoader();
			final File file = new File(classLoader.getResource("test-data").getFile());
			path = file.toPath().toRealPath(LinkOption.NOFOLLOW_LINKS);
		
		} catch (NoSuchMethodException | SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test(){
		try {
			processFile.invoke(fr, path);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_call(){		
		try {
			
			createTestData();			
			fr.set(LineProcessorImpl.SOURCE_PTH, new LineProcessorImpl(), getFilter());
			startProcess();					
			stopProcess();	
			Thread.sleep(10000);
		
		} catch (Exception e) {
			org.junit.Assert.fail(e.getMessage());
		}
		
	}
	
	private void stopProcess() {
		new Thread(new Runnable() {					
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					fr.preDestroy();
					Boolean stpValue = (Boolean)getFiledValue("stopRequested");
					Assert.assertTrue(stpValue);					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
			
		}).start();		
		
	}
	
	
	private Object getFiledValue(String filedName) {
		try {
			Field field  = fr.getClass().getDeclaredField(filedName);
			field.setAccessible(true);
			return field.get(fr);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			Assert.fail(e.getMessage());
			return null;
		}		
	}
	
	
	private void startProcess() {
		 new Thread(new Runnable() {					
			@Override
			public void run() {
				try {
					fr.call();					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}).start();		
	}

	private void createTestData() {
		File directory =  new File(LineProcessorImpl.SOURCE_PTH);
		if(!directory.exists()){
			directory.mkdirs();
		}
		
		String sourcePath = LineProcessorImpl.SOURCE_PTH +File.separator+"test-data";
		File file = new File(sourcePath);
		if(!file.exists()){
			try {
				Files.copy(Paths.get(DATA_FILE_PATH), Paths.get(sourcePath), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				org.junit.Assert.fail("Unable to copy test data");
			}
		}
	}
	
	
	
	
	/**
	 * 
	 * @return
	 */
	public FlyFileFilter getFilter(){
		
		FlyFileFilter fileFilter = new FlyFileFilter();		
		FilterChainBuilder chainbuilder = new FilterChainBuilder();	
		setFilterParameterMap(chainbuilder);
		Map<String, String> filterValues = new HashMap<>();
		filterValues.put("Filter_Name1", ""
				+ "com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter,"
				+ "com.flytxt.tp.processor.filefilter.RegexFilter,"
				+ "com.flytxt.tp.processor.filefilter.FifoFilter");
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
		fileFilter.set(LineProcessorImpl.SOURCE_PTH,"Filter_Name1");			
		return 	fileFilter;
	}	
	
	/**	
	 * Filter Parameters 
	 * @param chainbuilder
	 */
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

}
