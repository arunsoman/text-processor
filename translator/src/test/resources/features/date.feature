Feature: Date operation in bytes 


Scenario Outline: After a given date 
     Given marker and tpdate class 
     When  "<date1>" after "<date2>" 
     Then date one after date two returns "<status>" 
     Examples: input values 
          | date1 | date2 | status |
          | 09091991 16:00:00 | 09091991 15:59:59 | Y |
          | 09091991 16:00:00 | 09091992 15:59:59 | N |
          | 28092016 16:07:39 | 28102016 16:07:39 | N |
          | 28022016 16:07:39 | 28022012 16:07:39 | Y |
          | 12122012 12:12:12 | 28103013 16:07:39 | N |
          | 31122012 23:59:59 | 01012013 00:00:00 | N |
          
Scenario Outline: Before a given date 
     Given marker and tpdate class 
     When "<date1>" before "<date2>" 
     Then date one before date two returns "<status>" 
     Examples: input values 
          | date1 | date2 | status |
          | 09091991 16:00:00 | 09091991 15:59:59 | N |
          | 09091991 16:00:00 | 09091992 15:59:59 | Y |
          | 28092016 16:07:39 | 28102016 16:07:39 | Y |
          | 28022016 16:07:39 | 28022012 16:07:39 | N |
          | 12122012 12:12:12 | 28103013 16:07:39 | Y |
          | 31122012 23:59:59 | 01012013 00:00:00 | Y |
          
Scenario Outline: Date difference in milliseconds 
     Given marker and tpdate class 
     When "<date1>" minus "<date2>" 
     Then  diff is "<value>" 
     Examples: input values 
          | date1 | date2 | value |
          | 09091991 16:00:00.0 | 09091991 15:59:59.4 | 996 |
          | 09091991 16:00:00.0 | 09091992 15:59:59.6 | -31622399006 |
          | 28092016 16:17:00.0 | 28102016 16:07:00.0 | -2591400000 |
          | 28022016 16:07:00.2 | 28022012 16:07:00.0 | 126230400002|
          | 31122012 23:59:59.2 | 01012013 00:00:00.2 | -1000 |
          | 01012013 00:00:00.6 | 31122012 23:59:59.1 | 1005 |