Feature: Date (ddmmyyyy HH:mm:ss ) operation in bytes  


Scenario Outline: After a given date 
    Given marker and tpdate class  
	When enter  "<date1>" after "<date2>" 
	Then result should be <status>	
	Examples: input values 
	  | date1 | date2 | status |
	  
Scenario Outline: Before a given date 
    Given marker and tpdate class  
	When enter  "<date1>" greate than "<date2>" 
	Then result should be <status>	
	Examples: input values 
	  | date1 | date2 | status |
	  
Scenario Outline: Date difference in milliseconds 
    Given marker and tpdate class  
	When enter "<date1>" and enter "<date2>" 
	Then result should be <value>	
	Examples: input values 
	  | date1 | date2 | value |