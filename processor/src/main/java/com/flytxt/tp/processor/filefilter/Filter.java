package com.flytxt.tp.processor.filefilter;

import java.util.Map;
/**
 * Filter ENUM  is used to construct the different type of filters on the basis of the 
 * parameters configured in the appilcaion.yaml. Attribute values expected to come as map.
 * 
 * @author shiju.john
 */
public enum Filter {
	
	LMW_FILTER("com.flytxt.tp.processor.filefilter.LastModifiedWindowFilter") {
		@Override
		public FilterChain getFilterInstance(Map<String, String> filterParameters) {
			WindowFilter filter = (WindowFilter)getInstance();
			String value  = getParameter(filterParameters,this.getFilterClass());
			filter.windowUnit = Long.valueOf((null!=value?value:"0"));			
			return filter;
		}

		@Override
		public FilterChain getInstance() {			
			return new LastModifiedWindowFilter();
		}		
	},
	
	FIFO_FILTER("com.flytxt.tp.processor.filefilter.FifoFilter") {
		@Override
		public FilterChain getFilterInstance(Map<String, String> filterParameters) {
			FifoFilter filter = (FifoFilter)getInstance();
			//currently no argument is associated with FifoFilter				
			return filter;
		}

		@Override
		public FilterChain getInstance() {
			return new FifoFilter();
		}
	},
	
	REGX_FILTER("com.flytxt.tp.processor.filefilter.RegexFilter") {
		@Override
		public FilterChain getFilterInstance(Map<String, String> filterParameters) {
			RegexFilter filter = (RegexFilter)getInstance();
			String value  = getParameter(filterParameters,this.getFilterClass());	
			filter.setRegex(value);
			return filter;
		}

		@Override
		public FilterChain getInstance() {
			return new RegexFilter();
		}
	};
	
	/** Filter class name as enum - key */
	private String filterClass;	
	
	/**
	 * For getting the new instance which will not set the Parameter value 
	 * @return corresponding Filter object.
	 */
	public abstract FilterChain getInstance();
	
	
	/**
	 * For getting the new instance which will set the Parameter value
	 * 
	 * @param filterParameters : Map<String,String>
	 * 			key String :  is the filter class name configured in the application.yaml under the prefix filterArgs.
	 * 			value String : is the parameter values configured in the application.yaml. Multiple Parameters are 
	 * 						   expected to configure as comma separated 
	 * 
	 * @return corresponding FilterChain object
	 */
	public abstract FilterChain getFilterInstance(Map<String, String> filterParameters);
	
	
	/**
	 * Return the 
	 * @param filterParameters
	 * @param filterClass  : FQN of Filter class
	 * @return
	 */
	protected String getParameter(Map<String, String> filterParameters, String filterClass) {
		return filterParameters.get(filterClass);		
	}

	
	
	/**
	 * Constructor
	 * @param filterClass
	 */
	private Filter(String filterClass){		
		this.setFilterClass(filterClass);
	}
	
	

	/**  @return the filterClass */
	public String getFilterClass() {
		return filterClass;
	}

	/** @param filterClass the filterClass to set */
	public void setFilterClass(String filterClass) {
		this.filterClass = filterClass;
	}
	
	
	/**
	 * Identify the configured filter class 
	 * @param filterClassName
	 * @return Corresponding enum object
	 */
	public static Filter value(String filterClassName) {
		for(Filter filter : Filter.values()){
			if(filter.getFilterClass().equals(filterClassName)){
				return filter;
			}
		}
		return null;
	}

}
