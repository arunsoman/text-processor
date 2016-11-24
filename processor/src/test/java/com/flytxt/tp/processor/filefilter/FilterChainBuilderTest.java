package com.flytxt.tp.processor.filefilter;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		
		filterValues.put("Filter_Name1", "com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter,com.flytxt.tp.processor.filefilter.RegexFilter,com.flytxt.tp.processor.filefilter.RegexFilter");
		chainbuilder.setFilterNameMap(filterValues);
		chainbuilder.build();
		
		FilterChain filterChain = chainbuilder.getFilterChainByName("Filter_Name1");		
		Assert.assertEquals("com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter",filterChain.getClass().getName());		
		Assert.assertEquals("com.flytxt.tp.processor.filefilter.RegexFilter",filterChain.nextLink.getClass().getName());		
		Assert.assertNotNull(chainbuilder.getFilterNameMap());
	}
	
	@Test
	public void test_EmptyFilter() {
		filterValues.put("Filter_Name1", "");
		chainbuilder.setFilterNameMap(filterValues);
		chainbuilder.build();
		
		FilterChain filterChain = chainbuilder.getFilterChainByName("Filter_Name1");
		Assert.assertNull(filterChain);
	}
	
	@Test
	public void test_nullFilter() {
		filterValues.put("Filter_Name1", null);
		chainbuilder.setFilterNameMap(filterValues);
		chainbuilder.build();
		
		FilterChain filterChain = chainbuilder.getFilterChainByName("Filter_Name1");
		Assert.assertNull(filterChain);
	}

}
