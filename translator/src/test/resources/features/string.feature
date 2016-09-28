Feature: String operation in bytes 

Scenario Outline: String starts with a 
	Given marker and tpstring class 
	When enter <string1> strats with <string2> 
	Then return "<result>"
	Examples: input values 
		| string1 | string2 | result |
		
Scenario Outline: Convert to upper case string
	Given marker and tpstring class 
	When enter a lower case <string>
	Then return "<result>"
	Examples: input values 
		| string | result |
		
Scenario Outline: Convert to lower case string
	Given marker and tpstring class 
	When enter a upper case <string>
	Then return "<result>"
	Examples: input values 
		| string | result |

Scenario Outline: Convert to title case string
	Given marker and tpstring class 
	When enter a "<string>" with first letter in lower case  
	Then return "<result>"
	Examples: input values 
		| string | result |
		
Scenario Outline: Left trim a string
	Given marker and tpstring class 
	When enter a "<string>" with whitespace in beginning of the string   
	Then return "<result>"
	Examples: input values 
		| string | result |
		
Scenario Outline: Right trim a string
	Given marker and tpstring class 
	When enter a "<string>" with whitespace in end of the string   
	Then return "<result>"
	Examples: input values 
		| string | result |
		
		
Scenario Outline: Trim a string
	Given marker and tpstring class 
	When enter a "<string>" with whitespace in beginning or end of the string   
	Then return "<result>"
	Examples: input values 
		| string | result |
		
Scenario Outline: Substring search
	Given marker and tpstring class 
	When enter a "<string>" and another "<substring>"   
	Then return "<result>"
	Examples: input values 
		| string | substring |result |
		
Scenario Outline: Substring search ignore case
	Given marker and tpstring class 
	When enter a "<string>" and another "<substring>" in uppercase or lowercase  
	Then return "<result>"
	Examples: input values 
		| string | substring |result |