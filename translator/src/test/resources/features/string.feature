Feature: String operation in bytes 

Scenario Outline: String starts with a 
	Given marker and tpstring class 
	When enter string "<string1>" and enter first few characters of first string "<string2>"
	Then returns "<result>"
	Examples: input values 
		| string1 | string2 | result |
		| string1 | string | Y |
		| abcdefghijklmnopqrstuvwxyz | abcd | Y |
		| abcdefghijklmnopqrstuvwxyz | ABCD | N |
		| ab12!@cddddd | ab12!@ | Y |
		| VVVVVVVVVVVVVVVVVVV | V | Y |
		| A!@#$%^&*()_+pdsol76w7q | A! | Y |
		| AAA | AAAA | N |
		| 123fly123 | 123 | Y |
		| 123fly123 | fly | N |
		|  APPLE | APPLE | N |
		| aPpLE | APp | N |
		
Scenario Outline: Convert to upper case string
	Given marker and tpstring class 
	When enter a lower case string "<string>"
	Then returns "<result>"
	Examples: input values 
		| string | result |
		| string | STRING |
		| aPpLE | APPLE |
		| APPLE | APPLE |
		| ab12!@cddd | AB12!@CDDD |
		| I am a boy. | I AM A BOY. |
		|  a a  a a  |  A A  A A  |
		|   |   |
		
Scenario Outline: Convert to lower case string
	Given marker and tpstring class 
	When enter a upper case string "<string>"
	Then returns "<result>"
	Examples: input values 
		| string | result |
		| string | string |
		| aPpLE | apple |
		| APPLE | apple |
		| AB12!@CDDD | ab12!@cddd |
		| I am a boy. | i am a boy. |
		|   |   |
		|  A A  A A  |  a a  a a  |
		

Scenario Outline: Convert to title case string
	Given marker and tpstring class 
	When enter a  string "<string>" with first letter in lower case  
	Then returns "<result>"
	Examples: input values 
		| string | result |
		| string | String |
		| aPpLE | Apple |
		| APPLE | Apple |
		| AB12!@CDDD | Ab12!@cddd |
		| i am a boy. | I Am A Boy. |
		|   |   |
		
Scenario Outline: Left trim a string
	Given marker and tpstring class 
	When enter a  string "<string>" with whitespace in beginning of the string   
	Then returns "<result>"
	Examples: input values 
		| string | result |
		|    ABCd | ABCd |
		|    Negative    testing    | Negative    testing    |
		| I Am A Boy. | I Am A Boy. |
		| ...   !!! | ...   !!! |
		
		
Scenario Outline: Right trim a string
	Given marker and tpstring class 
	When enter a string "<string>" with whitespace in end of the string   
	Then returns "<result>"
	Examples: input values 
		| string | result |
		| ABCd    | ABCd |
		|    Negative    testing    |    Negative    testing |
		| I Am A Boy. | I Am A Boy. |
		| ...   !!! | ...   !!! |
		
		
Scenario Outline: Trim a string
	Given marker and tpstring class 
	When enter a string "<string>" with whitespace in beginning or end of the string   
	Then returns "<result>"
	Examples: input values 
		| string | result |
		| ABCd    | ABCd |
		|    Negative    testing    | Negative    testing |
		|  I Am A Boy.  | I Am A Boy. |
		| ...   !!! | ...   !!! |
		
Scenario Outline: Substring search
	Given marker and tpstring class 
	When enter a string "<string>" and another substring "<substring>" of first string   
	Then returns "<result>"
	Examples: input values 
		| string | substring | result |
		| string1 | string | Y |
		| abcdefghijklmnopqrstuvwxyz | efgh | Y |
		| abcdefghijklmnopqrstuvwxyz | ABCD | N |
		| ab12!@cddddd | ab12!@ | Y |
		| VVVVVVVVVVVVVVVVVVV | V | Y |
		| A!@#$%^&*()_+pdsol76w7q | 7q | Y |
		| AAA | AAAA | N |
		| 123 f l y 123 | fly | N |
		| aPpLE | APp | N |
		| s7s7s7s7 | 7s | Y |
		
Scenario Outline: Substring search ignore case
	Given marker and tpstring class 
	When enter a string "<string>" and another string "<substring>" in uppercase or lowercase  
	Then returns "<result>"
	Examples: input values 
		| string | substring |result |
		| string1 | string | Y |
		| abcdefghijklmnopqrstuvwxyz | efgh | Y |
		| abcdefghijklmnopqrstuvwxyz | ABCD | Y |
		| ab12!@cddddd | ab12!@ | Y |
		| VVVVVVVVVVVVVVVVVVV | V | Y |
		| A!@#$%^&*()_+pdsol76w7Q | 7q | Y |
		| AAA | AAAA | N |
		| 123 f l y 123 | fly | N |
		| aPpLE | APp | Y |
		| s7s7s7s7 | 7s | Y |
		| I Am A Boy. | m a | Y |