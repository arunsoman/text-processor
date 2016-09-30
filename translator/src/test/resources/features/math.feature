Feature: Math operation in bytes 

Scenario Outline: Check number is less than another number 
     Given marker and math class 
     When enter number "<number1>" less than another number "<number2>" 
     Then for less than result should be "<status>" 
     Examples: input values 
          | number1 | number2 | status |
          |  78.9  |  96    | Y |
          | -99.78 | -98.78 | Y |
          |  12    |  12    | N | 
          | -16    |  18    | Y |
          | 9.123456 |  9.1 | N |
          | 9.123456 |  9.2 | Y |
          | 9.123456 |  9.1234567 | Y |
          | 78.9  |  -96    | N |
          | 0 | 0 | N |
          
Scenario Outline: Check number is less than or equal to another number 
     Given marker and math class 
     When enter number "<number1>" less than equal to another number "<number2>" 
     Then for less than eq result should be "<status>" 
     Examples: input values 
          | number1 | number2 | status |
          |  78.9  |  96    | Y |
          | -99.78 | -98.78 | Y |
          |  12    |  12    | Y | 
          | -16    |  18    | Y |
          | 9.123456 |  9.1 | N |
          | 9.123456 |  9.2 | Y |
          | 9.123456 |  9.1234567 | Y |
          | 78.9  |  -96    | N |
          | 0 | 0 | Y | 
          
Scenario Outline: Absolute value of a number 
     Given marker and math class 
     When enter number to apply abs "<number1>" 
     Then after applying abs result should be "<status>" 
     Examples: input values 
          | number1 | status  |
          | -99.78| 99.78 |
          | -1    | 1     |
          | 0     | 0     |
          | 9.78  | 9.78  |
          
          
          
Scenario Outline: Check number is greater than or equal to another number 
     Given marker and math class 
     When enter number "<number1>" greater than equal to another number "<number2>" 
     Then for greater than equal to result should be "<status>" 
     Examples: input values 
          | number1 | number2 | status  |
          | 1     | 0     |    Y    |
          | -1    | -2    |    Y    |
          | 1.98  | 1.96  |    Y    |
          | 13.7  | 13.7  |    Y    |
          | 0     | 0     |    Y    |
          | 0     | 1     |    N    |
          | -2    | -1    |    N    |
          | 1.96  | 1.98  |    N    |
          
Scenario Outline: Check number is greater than another number 
     Given marker and math class 
     When enter number "<number1>" greater than  another number "<number2>" 
     Then for greater than result should be "<status>" 
     Examples: input values 
          | number1 | number2 | status  |
          | 1     | 0     |    Y    |
          | -1    | -2    |    Y    |
          | 1.98  | 1.96  |    Y    |
          | 13.7  | 13.7  |    N    |
          | 0     | 0     |    N    |
          | 0     | 1     |    N    |
          | -2    | -1    |    N    |
          | 1.96  | 1.98  |    N    |
          
Scenario Outline: Substract long numbers 
     Given marker and math class 
     When enter two long numbers "<number1>" "<number2>" for substraction 
     Then after sub long result should be "<subs>" 
     Examples: input values 
          | number1 | number2 |  subs  |
          |  4    | 2     |   2  |
          |  4    | -2    |   6  |
          |  1    | 2     |   -1 |
          |  0    | 0   |   0 |
          |  -4   | -2    |   -2 |
          |2147483647| 1  |2147483646  |
          |  -2147483000  | 648  |-2147483648 |
          
Scenario Outline: Substract float numbers 
     Given marker and math class 
     When enter two float number "<number1>" "<number2>" for substraction 
     Then after sub float result should be "<subs>" 
     Examples: input values 
          | number1 | number2 | subs |
          |  3.14 | 1.96  |1.18  |
          |  1.7  | 1.96  |-0.26 |
          |  0.000000006  | 0.000000006  |0 |
          |  64568.0004181616  | 684641.3546464  |-620073.354228 |
          |  0 | 0  |0  |
          
Scenario Outline: Add long numbers 
     Given marker and math class 
     When enter two long numbers "<number1>" "<number2>" for addition 
     Then after adding long result should be "<total>" 
     Examples: input values 
          | number1 | number2 | total |
          |  4    | -2     |   2  |
          |  4    | 2    |   6  |
          |  1    | -2     |   -1 |
          |  -18    | 22   |   4 |
          |  -4   | -2    |   -6 |
          |2147483646| 1  |2147483647  |
          |  -2147483000  | -648  |-2147483648 |
          |  0 | 0  | 0  |
          
Scenario Outline: Add float numbers 
     Given marker and math class 
     When enter two float numbers "<number1>" "<number2>" for addition 
     Then after adding float result should be "<total>" 
     Examples: input values 
          | number1 | number2 | total |
          |  3.14 | -1.96  |1.18  |
          |  1.7  | -1.96  |-0.26 |
          |  -0.0006  | 0.0006  |0.0 |
          |  64568.004  | 684641.354  |749209.358 |
          |  0 | 0  |0.0  |
          
          
Scenario Outline: find the ceil of a number 
     Given marker and math class 
     When enter a number "<number1>" to find ceil 
     Then after ceil result should be "<value>" 
     Examples: input values 
          | number1 | value | 
          | 0 | 0.0 |
          | 0.003 | 1 |
          | 2.9987 | 3 |
          | -1.5 | -1 |
          | -0.997 | 0 |
          
Scenario Outline: find the floor of a number 
     Given marker and math class 
     When enter a number "<number1>" to find floor 
     Then after floor result should be "<value>" 
     Examples: input values 
          | number1 | value | 
          | 0 | 0 |
          | 0.003 | 0 |
          | 2.9987 | 2 |
          | -1.5 | -2 |
          | -0.997 | -1 |
          
Scenario Outline: round of a number in a given scale 
     Given marker and math class 
     When enter a number "<number1>"  and the scale "<scale>" 
     Then after round result should be "<value>" 
     Examples: input values 
          | number1 | scale | value | 
          | 0.456 | 2 | 0.46 |
          | 4.456 | 2 | 4.46 |
          | 9.8009 | 3 | 9.801 |
          | 94.05 | 1 | 94.1 |
          | 0     | 1  | 0   |
          
          
          
Scenario Outline: check two number array are equal 
     Given marker and math class 
     When enter two equal numbers "<number1>"  "<number2>" 
     Then after checking two number to be equal result should be "<value>" 
     Examples: input values 
          | number1 | number2 | value | 
          | 3.14 | 3.14 | Y |
          | -3.14 | 3.14 | N |
          |  969.36659 | 963.3666  | N |
          |  0 | 0  | Y |
          
Scenario Outline: extract decimal part of a number 
     Given marker and math class 
     When enter a decimal number "<number1>" to extract decimal part of the number 
     Then after extracting decimal result should be "<value>" 
     Examples: input values 
          | number1 | value | 
          | 9.3146 | 3146 |
          | -9.3146 | 3146 |
          | 0.003 | 003 |
          | 9 | 0 |
          
Scenario Outline: extract integer part of a number 
     Given marker and math class 
     When enter a decimal number "<number1>" to extract integer part of the number 
     Then after extracting integer result should be "<value>" 
     Examples: input values 
          | number1 | value | 
          | 9.3146 | 9 |
          | -9.3146 | -9 |
          | 0.003 | 0 |
          | 7 | 7 |
          
Scenario Outline: check given string is a number 
     Given marker and math class 
     When enter a number string "<number1>" 
     Then after checking a number to be strings result should be "<status>" 
     Examples: input values 
          | number1 | status | 
          | 0.0541 | Y |
          | 0 | Y |
          | hi | N |
          | -960.0541 | Y |
          | $%^& | N |
          
	  