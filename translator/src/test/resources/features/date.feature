Feature: Date (ddmmyyyy HH:mm:ss ) operation in bytes


Scenario Outline: After a given date
    Given marker and tpdate class
    When <String1> after <String2>
    Then date1 after date2 returns <status>
    Examples: input values
          | date1 | date2 | status |
          | 09091991 16:00:00 | 09091991 15:59:59 | Y |
          | 09091991 16:00:00 | 09091992 15:59:59 | N |
          | 28092016 16:07:39 | 28102016 16:07:39 | N |
          | 28022016 16:07:39 | 28022012 16:07:39 | Y |
          | 12122012 12:12:12 | 28103013 16:07:39 | N |
          | 31122012 23:59:59 | 01012013 00:00:00 | Y |

Scenario Outline: Before a given date
    Given marker and tpdate class
    When <date1> before <date2>
    Then date1 before date2 returns <status>
    Examples: input values
          | date1 | date2 | status |
          | 09091991 16:00:00 | 09091991 15:59:59 | Y |
          | 09091991 16:00:00 | 09091992 15:59:59 | Y |
          | 28092016 16:07:39 | 28102016 16:07:39 | N |
          | 28022016 16:07:39 | 28022012 16:07:39 | Y |
          | 12122012 12:12:12 | 28103013 16:07:39 | N |
          | 31122012 23:59:59 | 01012013 00:00:00 | Y |

Scenario Outline: Date difference in milliseconds
    Given marker and tpdate class
    When <date1> minus <date2>
    Then  diff is <value>
    Examples: input values
          | date1 | date2 | value |
          | 09091991 16:00:00 | 09091991 15:59:59 | 1000 |
          | 09091991 16:00:00 | 09091992 15:59:59 | 31622401000 |
          | 28092016 16:17:00 | 28102016 16:07:00 | -2591400000 |
          | 28022016 16:07:00 | 28022012 16:07:00 | 105235200000 |
          | 31122012 23:59:59 | 01012013 00:00:00 | -1000 |
          | 01012013 00:00:00 | 31122012 23:59:59 | 1000 |