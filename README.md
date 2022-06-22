Description:
Netframe-0.0.1-SNAPSHOT.jar 						- Interview Task
SimlpeTest-1.0-SNAPSHOT-jar-with-dependencies.jar	- Test for the task


To run Interview Task 
	java -jar Netframe-0.0.1-SNAPSHOT.jar   	

Compiled with Java 11, Initialized port: 8080, database: H2

http://localhost:8080  - will show recieved and stored results of the events (score)
http://localhost:8080/clear - to clear database
localhost:8080/actuator/shutdown (post) - for shutdown


To run integration test 
	java -jar SimlpeTest-1.0-SNAPSHOT-jar-with-dependencies.jar threadsCount teamsCount eventsCount maxTestRunTime

where: 
	- threadsCount - count of threads which send the requests
	- teamsCount - count of different teams that take part int all events
	- eventsCount - count of events
	- maxTestRunTime - max time for test in seconds

Example: java -jar SimlpeTest-1.0-SNAPSHOT-jar-with-dependencies.jar 5 10 5 60 
	- will create test that lasts 60 second and sends 5 events in 5 different threads. Will created 10 teams.

To send new score you can use POST method to endpoint

http://localhost:8080/score 
with body : 
{
    "team1": "team1»,
    "team2": "team1»,
    "score": {
        "team1Score": 0,
        "team2Score": 0
    }
}
To identify created event I used unique combination of team labels.

	
