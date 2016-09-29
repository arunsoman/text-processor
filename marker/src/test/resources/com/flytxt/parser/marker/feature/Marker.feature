Feature: Marker feature 

Scenario Outline: Extract nth element 
	Given markerfactory
	And tokenfactory
	When In the input "<input>" delimited "<dim>" get "<n>" element 
	Then result should be "<result>"
	Examples: input values 
		| input | dim | n | result|
        | hi-hi|-|1|hi|
        | hi.there|.|2|there|
        | hi.there|.|1|hi|
        | hi-there|-|2|there|
        | hi-there-|-|2|there|
		
Scenario Outline: Extract all element 
	Given markerfactory
	And tokenfactory
	When In the input "<input>" delimited "<dim>" 
	Then lenght should be "<n>"
	Examples: input values 
		| input | dim | n |
		| 1,2,3,4,5 | , | 5 |