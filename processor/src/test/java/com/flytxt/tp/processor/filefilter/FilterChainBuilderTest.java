package com.flytxt.tp.processor.filefilter;

import java.lang.reflect.Field;
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
public class FilterChainBuilderTest {

	FilterChainBuilder chainbuilder ;
	Map<String, String> filterValues ;
	@Before
	public void buildSetup(){
		chainbuilder = new FilterChainBuilder();	
		filterValues = new HashMap<>();
	}
	
	@Test
	public void test() {
		
		filterValues.put("Filter_Name1", "com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter,"
				+ "com.flytxt.tp.processor.filefilter.RegexFilter,"
				+ "com.flytxt.tp.processor.filefilter.RegexFilter");
		
		chainbuilder.setFilterNameMap(filterValues);
		setFilterParameterMap(chainbuilder);
		chainbuilder.build();
		
		FilterChain filterChain = chainbuilder.getFilterChainByName("Filter_Name1");		
		Assert.assertEquals("com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter",filterChain.getClass().getName());		
		Assert.assertEquals("com.flytxt.tp.processor.filefilter.RegexFilter",filterChain.nextLink.getClass().getName());		
		Assert.assertNotNull(chainbuilder.getFilterNameMap());
	}
	
	@Test
	public void test_EmptyFilter() {
		filterValues.put("Filter_Name1", "");
		setFilterParameterMap(chainbuilder);
		chainbuilder.setFilterNameMap(filterValues);
		chainbuilder.build();
		
		FilterChain filterChain = chainbuilder.getFilterChainByName("Filter_Name1");
		Assert.assertNull(filterChain);
	}
	
	@Test
	public void test_nullFilter() {
		filterValues.put("Filter_Name1", null);
		setFilterParameterMap(chainbuilder);
		chainbuilder.setFilterNameMap(filterValues);
		chainbuilder.build();
		
		FilterChain filterChain = chainbuilder.getFilterChainByName("Filter_Name1");
		Assert.assertNull(filterChain);
	}
	
	private void setFilterParameterMap(FilterChainBuilder chainbuilder) {
		try {
			
			FilterParameters filterParameters = new FilterParameters();
			Map<String, String> parameterMap = new HashMap<>();
			parameterMap.put("com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter", "0");
			parameterMap.put("com.flytxt.tp.processor.filefilter.RegexFilter", ".*[a-zA-Z]+.*");
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
