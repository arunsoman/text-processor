package com.flytxt.tp.processor.filefilter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * For building the File filter chain, 
 * @author shiju.john
 *
 */
@ConfigurationProperties(prefix = "filters")
public class FilterChainBuilder {
	
	/** Filter Parameters configuration against each Filter class*/
	@Autowired
	private FilterParameters filterParameters;
	
	/**  Configured file filters from Application.yaml  */
	private Map<String,String> filterNameMap;
	
	
	
	/** filter chain Map . Contain multiple filter chains */
	private Map<String,FilterChain> filterChainMap = null;
	
	
	
	/**   Filter separator in apllication.yaml  */	 
	private final static String FILTER_CLASS_SEPARATOR ="," ;
	
	/** Logger */
	private final Logger logger = LoggerFactory.getLogger(FilterChainBuilder.class);
	
	
	/**
	 * For building the File filter chain 
	 * Build the Filter chain and kept inside the Map filterChainMap 
	 * The key will be the filter Name and value will be the constructed  FilterChain object
	 * 
	 */
	@PostConstruct
	public void build(){		
		if(null!=getFilterNameMap() && getFilterNameMap().size()>0){
			filterChainMap = new HashMap<>(getFilterNameMap().size());
			Set<Entry<String, String>> entries =  getFilterNameMap().entrySet();
			for(Entry<String, String> entry : entries){
				filterChainMap.put(entry.getKey(), getFilterChain(entry.getValue(),entry.getKey()));
			}		
		}		
	}
	
	/**
	 * For creating the filterChain implementation class instance 
	 * 
	 * @param fileFilterClassName : FilterChain implementation Class name 
	 * @param filterName 
	 * @return instance of the given class name
	 */
	private FilterChain getFilterInstance(String fileFilterClassName, String filterName) {
		Filter filter = Filter.value(fileFilterClassName);
		if(null!=filter) {
			if(null!=filterParameters.getArgMap()){
				return filter.getFilterInstance(filterParameters.getArgMap().get(filterName));
			}else{
				return filter.getInstance();
			}
		}else{
			logger.error("Unable to create the instance of the file filter : "+fileFilterClassName + 
					" Please check the Filter ENUM is properlly implemented." );
			return null;
		}			
	}


	/** 
	 * COnvert the given  filters to the corresponding filter class.
	 * @param filterName 
	 * @return the first filterChain node 
	 */
	private FilterChain getFilterChain(String fileFilters, String filterName) {
		FilterChain filterChain = null,firstNode = null;		
		if(null!=fileFilters){
			String [] fileFilterArray  = fileFilters.split(FILTER_CLASS_SEPARATOR);
			for(String fileFilter:fileFilterArray){				
				if(null==filterChain){
					filterChain = getFilterInstance(fileFilter,filterName);
					firstNode = filterChain;
				}else{
					filterChain.nextLink = getFilterInstance(fileFilter,filterName);
					filterChain = filterChain.nextLink;
				}				
			}
		}
		return firstNode;
	}

	
	
	/**
	 * 
	 * @param filterName
	 * @return
	 */
	public FilterChain getFilterChainByName(String filterName) {
		if(null!=filterChainMap&& filterChainMap.size()>0){
			return filterChainMap.get(filterName);
		}
		return null;
	}

	/**
	 * @return the filterNameMap
	 */
	public Map<String,String> getFilterNameMap() {
		return filterNameMap;
	}

	/**
	 * @param filterNameMap the filterNameMap to set
	 */
	public void setFilterNameMap(Map<String,String> filterNameMap) {
		this.filterNameMap = filterNameMap;
	}
}
