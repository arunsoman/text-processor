package com.flytxt.tp.processor.filefilter;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * For setting the initialization Parameters of different Filters.
 * 
 * Find the below Sample application.yaml file structure <br><br>
 
    filterArgs:			<br>
  	&nbsp argMap:		<br>
    
    &nbsp &nbsp		filter_name_1:		<br>
    &nbsp &nbsp	&nbsp	com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter: 1000	<br>
    &nbsp &nbsp &nbsp	com.flytxt.tp.processor.filefilter.RegexFilter: .*[a-zA-Z]+.*		<br>
    
    &nbsp &nbsp	   filter_name_2:		<br>
    &nbsp &nbsp	&nbsp	com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter: 1000	<br>
    &nbsp &nbsp &nbsp	com.flytxt.tp.processor.filefilter.RegexFilter: .*[a-zA-Z]+.*		<br>
 
 * The above structure will keep in a Map<String,Map<String,String>.
 * 
 * The outer map Key will be the Filter name (JOB name) and value is another map.
 * The inner map Key is the filter class name (Which should be the class name configured against the above filter name) 
 * and value will be the runtime initialization parameters, Multiple parameters should be separated by commas.
 *  
 * @author shiju.john
 *
 */
@ConfigurationProperties(prefix = "filterArgs")
public class FilterParameters {
	
	/**  Read the filter class input arguments from Application.yaml  */
	private Map<String,Map<String,String>> argMap;
		

	/**
	 * @return the argMap
	 */
	public Map<String,Map<String,String>> getArgMap() {
		return argMap;
	}

	/**
	 * @param argMap the argMap to set
	 */
	public void setArgMap(Map<String,Map<String,String>> argMap) {
		this.argMap = argMap;
	}

	

}
