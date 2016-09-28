Feature: Math operation in bytes 

Scenario Outline: Check number is less than another number 
	Given marker and math class 
	When enter <number1> less than <number2> 
	Then result should be <status>
	Examples: input values 
		| number1 | number2 | status|
		|  "78.9"     |  "96"     | 
		 | "-99.78" | "-98.78" |
		|  "12"     |  "14"     | 
		|  "16"     |  "18"     | 
		|  "36"     |  "56"     | 
		


Scenario Outline: Check number is less than or equal to another number
    Given marker and math class  
	When enter <number1> less than equal <number2> 
	Then result should be <status>
	Examples: input values 
		| number1 | number2 | status|
		  
		
Scenario Outline: Absolute value of a number
    Given marker and math class  
	When enter a negative "<number1>"
	Then result should be <status>	
	Examples: input values 
	  | number1 | status |
	  

	  
	  
Scenario Outline: Check number is greater than or equal to another number
    Given marker and math class  
	When enter <number1> greater than equal <number2> 
	Then result should be <status>	
	Examples: input values 
	  | number1 | status |
	  
	  
Scenario Outline: Check number is greater than another number
    Given marker and math class  
	When enter <number1> greater than <number2> 
	Then result should be <status>	
	Examples: input values 
	  | number1 | status |
	  

Scenario Outline: Substract long number 
    Given marker and math class  
	When enter <number1> <number2> for substraction
	Then result should be <subs>	
	Examples: input values 
	  | number1 | number2 | subs |
	  
	  
Scenario Outline: Substract float number 
    Given marker and math class  
	When enter float <number1> <number2> for substraction
	Then result should be <subs>	
	Examples: input values 
	  | number1 | number2 | subs |
	  
	  
Scenario Outline: Add long number 
    Given marker and math class  
	When enter <number1> <number2> for addition
	Then result should be <total>	
	Examples: input values 
	  | number1 | number2 | total |
	  
	  
Scenario Outline: Add float number 
    Given marker and math class  
	When enter float <number1> <number2> for addition 
	Then result should be <subs>	
	Examples: input values 
	  | number1 | number2 | total |
	  

Scenario Outline: Add character byte array with a number 
    Given marker and math class  
	When enter a string "<number1>" and a <number2> for addition
	Then result should be <total>	
	Examples: input values 
	  | number1 | number2 | total |
	  
	  
Scenario Outline: Substract character byte array with a number 
    Given marker and math class  
	When enter a string "<number1>" and a <number2> for substraction 
	Then result should be <total>	
	Examples: input values 
	  | number1 | number2 | total |
	  
Scenario Outline: Substract character byte array with a number 
    Given marker and math class  
	When enter a string "<number1>" and a <number2> for substraction 
	Then result should be <total>	
	Examples: input values 
	  | number1 | number2 | total |
	  
Scenario Outline: find the ceil of a number
    Given marker and math class  
	When enter a "<number1>"  to find ceil
	Then result should be <value>	
	Examples: input values 
	  | number1 | value | 
	  
Scenario Outline: find the floor of a number
    Given marker and math class  
	When enter a "<number1>"  to find floor
	Then result should be <value>	
	Examples: input values 
	  | number1 | value | 
	  
Scenario Outline: round of a number in a given scale
    Given marker and math class  
	When enter a "<number1>"  and the <scale>
	Then result should be <value>	
	Examples: input values 
	  | number1 | scale | value | 
	  
	  
Scenario Outline: round of a number in a given scale
    Given marker and math class  
	When enter a "<number1>"  and the <scale>
	Then result should be <value>	
	Examples: input values 
	  | number1 | scale | value | 
	  
Scenario Outline: check two number arrays are equal 
    Given marker and math class  
	When enter two equal "<number1>" and "<number2>"  
	Then result should be <value>	
	Examples: input values 
	  | number1 | number2 | value | 
	  
Scenario Outline: extract decimal part of a number  
    Given marker and math class  
	When enter a decimal "<number1>" to extract decimal part of the number
	Then result should be <value>	
	Examples: input values 
	  | number1 | value | 
	  
Scenario Outline: extract integer part of a number  
    Given marker and math class  
	When enter a decimal "<number1>" to extract integer part of the number
	Then result should be <value>	
	Examples: input values 
	  | number1 | value | 
	  
	  
Scenario Outline: check given string is a number  
    Given marker and math class  
	When enter a string  "<number1>" 
	Then result should be "<status>"	
	Examples: input values 
	  | number1 | status | 