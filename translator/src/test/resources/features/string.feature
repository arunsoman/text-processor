Feature: String operation in bytes 

  Scenario Outline: get the length of string
    Given marker and tpstring class 
     When "<string1>" provided
     Then return length "<result>"
    Examples: input values
      | string1 | result | 
      | hi      | 2      | 
  
  Scenario Outline: String starts with a
    Given marker and tpstring class 
     When "<string1>" starts with prefix "<string2>"
     Then return true if string starts with prefix"<result>"
    Examples: input values 
      | string1                      | string2  | result | 
      | string1                    | string | Y    | 
      | abcdefghijklmnopqrstuvwxyz | abcd   | Y    | 
      | abcdefghijklmnopqrstuvwxyz | ABCD   | N    | 
      | ab12!@cddddd               | ab12!@ | Y    | 
      | VVVVVVVVVVVVVVVVVVV        | V      | Y    | 
      | A!@#$%^&*()_+pdsol76w7q    | A!     | Y    | 
      | AAA                        | AAAA   | N    | 
      | 123fly123                  | 123    | Y    | 
      | 123fly123                  | fly    | N    | 
      | ' APPLE'                     | APPLE  | N    | 
      | aPpLE                      | APp    | N    | 
  
  Scenario Outline: Convert to upper case string
    Given marker and tpstring class 
     When convert to upperCase"<string>"
     Then returns upperCase "<result>"
    Examples: input values 
      | string        | result        | 
      | string      | STRING      | 
      | aPpLE       | APPLE       | 
      | APPLE       | APPLE       | 
      | ab12!@cddd  | AB12!@CDDD  | 
      | I am a boy. | I AM A BOY. | 
      |  a a  a a   |  A A  A A   | 
      |             |             | 
  
  Scenario Outline: Convert to lower case string
    Given marker and tpstring class 
     When convert to lowerCase "<string>"
     Then returns lowerCase "<result>"
    Examples: input values 
      | string        | result        | 
      | string      | string      | 
      | aPpLE       | apple       | 
      | APPLE       | apple       | 
      | AB12!@CDDD  | ab12!@cddd  | 
      | I am a boy. | i am a boy. | 
      |             |             | 
      |  A A  A A   |  a a  a a   | 
  
  Scenario Outline: Convert to title case string
    Given marker and tpstring class 
     When conver to titleCase"<string>"
     Then returns titleCase "<result>"
    Examples: input values 
      | string        | result        | 
      | string      | String      | 
      | aPpLE       | Apple       | 
      | APPLE       | Apple       | 
      | AB12!@CDDD  | Ab12!@cddd  | 
      | i am a boy. | I Am A Boy. | 
      |             |             | 
  
  Scenario Outline: Left trim a string
    Given marker and tpstring class 
     When "<string>" with leading whitespace is provided
     Then returns string without leading whitespaces "<result>"
    Examples: input values 
      | string                      | result                   | 
      | '   ABCd'                   | 'ABCd'                   | 
      | '   Negative    testing   ' | 'Negative    testing   ' | 
      | 'I Am A Boy.'               | 'I Am A Boy.'            | 
      | '...   !!!'                 | '...   !!!'              | 
  
  Scenario Outline: Right trim a string
    Given marker and tpstring class 
     When "<string>" with trailing whitespaces provided
     Then return string without trailing whilespaces "<result>"
    Examples: input values 
    | string                      | result                   | 
      | 'ABCd   '                   | 'ABCd'                   | 
      | '   Negative    testing   ' | '   Negative    testing' | 
      | 'I Am A Boy.'               | 'I Am A Boy.'            | 
      | '...   !!!'                 | '...   !!!'              | 
  
  Scenario Outline: Trim a string
    Given marker and tpstring class 
     When "<string>" with whitespace in beginning or end is provided
     Then return string with no whitespaces at start or end "<result>"
    Examples: input values 
     | string                      | result                | 
      | 'ABCd   '                   | 'ABCd'                | 
      | '   Negative    testing   ' | 'Negative    testing' | 
      | ' I Am A Boy. '             | 'I Am A Boy.'         | 
      | '...   !!!'                 | '...   !!!'           | 
  
  Scenario Outline: Substring search
    Given marker and tpstring class 
     When "<string>" contains "<substring>"   
     Then return true if string contais subString"<result>"
    Examples: input values 
      | string                       | substring | result | 
      | string1                    | string  | Y    | 
      | abcdefghijklmnopqrstuvwxyz | efgh    | Y    | 
      | abcdefghijklmnopqrstuvwxyz | ABCD    | N    | 
      | ab12!@cddddd               | ab12!@  | Y    | 
      | VVVVVVVVVVVVVVVVVVV        | V       | Y    | 
      | A!@#$%^&*()_+pdsol76w7q    | 7q      | Y    | 
      | AAA                        | AAAA    | N    | 
      | 123 f l y 123              | fly     | N    | 
      | aPpLE                      | APp     | N    | 
      | s7s7s7s7                   | 7s      | Y    | 
  
  Scenario Outline: Substring search ignore case
    Given marker and tpstring class 
     When "<string>" contains "<substring>" ignore case  
     Then return true if string contais subString ignoreCase "<result>"
    Examples: input values 
      | string                       | substring | result | 
      | string1                    | string  | Y    | 
      | abcdefghijklmnopqrstuvwxyz | efgh    | Y    | 
      | abcdefghijklmnopqrstuvwxyz | ABCD    | Y    | 
      | ab12!@cddddd               | ab12!@  | Y    | 
      | VVVVVVVVVVVVVVVVVVV        | V       | Y    | 
      | A!@#$%^&*()_+pdsol76w7Q    | 7q      | Y    | 
      | AAA                        | AAAA    | N    | 
      | 123 f l y 123              | fly     | N    | 
      | aPpLE                      | APp     | Y    | 
      | s7s7s7s7                   | 7s      | Y    | 
      | I Am A Boy.                | m a     | Y    | 

  Scenario Outline: check if string ends with prefix
    Given marker and tpstring class 
     When "<string>" endsWith "<substring>"  
     Then return true if string ends with subString <result>
    Examples: input values 
      | string                       | substring | result | 
      
  Scenario Outline: check if string ends with prefix
    Given marker and tpstring class 
     When "<string>" endsWith "<substring>" ignore case  
     Then return true if string ends with subString ignoreCase "<result>"
    Examples: input values 
      | string                       | substring | result | 
 
 
 Scenario Outline: extract n chars from leading
    Given marker and tpstring class
     When "<string>" is given and "<n>" chars to be extracted from head
     Then return a marker with n chars from leading "<result>"
    Examples: input values
      | string                       | n | result |

Scenario Outline: extract n chars from trailing
    Given marker and tpstring class
     When "<string>" is given and "<n>" chars to be extracted from trail
     Then return a marker with n chars from trailing "<result>"
    Examples: input values
      | string                       | m | result |
 
 
Scenario Outline: merge content of two markers
    Given marker and tpstring class
     When two markers has to be merged "<string1>" and "<string1>"
     Then return a marker which contains chars from both markers "<result>"
    Examples: input values
      | string1                       | string2 | result |