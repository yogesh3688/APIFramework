Feature: Validating Place API 

@AddPlace 
Scenario Outline: Verify if Place is being sucesssfully added using AddPlaceAPI 
	Given Add Place PayLoad "<Name>" "<Language>" "<Address>" 
	When User calls "AddPlaceAPI" with "POST" http request 
	Then The API calls got success with status code "200" 
	And "status" in response body is "OK" 
	And "scope" in response body is "APP" 
	And Verify "place_id" created maps to "<Name>" using "GetPlaceAPI" 
	
	Examples: 
		| Name  	| Language  | Address		|
		| Priyansha | English   | Parel		  	|
		| Yogesh	| Marathi	| Majiwada		|
		
#		@DeletePlace 
#		Scenario: Verify Delete Place functioanlity is working or not 
#			Given DeletePlace Payload 
#			When User calls "DeletePlaceAPI" with "POST" http request 
#			And "status" in response body is "OK" 
# 