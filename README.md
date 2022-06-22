{\rtf1\ansi\ansicpg1251\cocoartf2580
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 ArialMT;}
{\colortbl;\red255\green255\blue255;\red0\green0\blue0;\red255\green255\blue254;\red144\green1\blue18;
\red9\green60\blue148;\red19\green118\blue70;}
{\*\expandedcolortbl;;\cssrgb\c0\c0\c0;\cssrgb\c100000\c100000\c99608;\cssrgb\c63922\c8235\c8235;
\cssrgb\c1569\c31765\c64706;\cssrgb\c3529\c52549\c34510;}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 Description:\
Netframe-0.0.1-SNAPSHOT.jar 						- Interview Task\
SimlpeTest-1.0-SNAPSHOT-jar-with-dependencies.jar	- Test for the task\
\
\
To run Interview Task \
	java -jar Netframe-0.0.1-SNAPSHOT.jar   	\
\
Compiled with Java 11, Initialized port: 8080, database: H2\
\
http://localhost:8080  - will show recieved and stored results of the events (score)\
http://localhost:8080/clear - to clear database\
localhost:8080/actuator/shutdown (post) - for shutdown\
\
\
To run integration test \
	java -jar SimlpeTest-1.0-SNAPSHOT-jar-with-dependencies.jar threadsCount teamsCount eventsCount maxTestRunTime\
\
where: \
	- threadsCount - count of threads which send the requests\
	- teamsCount - count of different teams that take part int all events\
	- eventsCount - count of events\
	- maxTestRunTime - max time for test in seconds\
\
Example: java -jar SimlpeTest-1.0-SNAPSHOT-jar-with-dependencies.jar 5 10 5 60 \
	- will create test that lasts 60 second and sends 5 events in 5 different threads. Will created 10 teams.\
\
To send new score you can use POST method to endpoint\
\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 http://localhost:8080/score \
with body : \
\pard\pardeftab720\sl360\partightenfactor0
\cf2 \cb3 \expnd0\expndtw0\kerning0
\outl0\strokewidth0 \strokec2 \{\cb1 \
\cb3     \cf4 \strokec4 "team1"\cf2 \strokec2 : \cf5 \strokec5 "team1\'bb\cf2 \strokec2 ,\cb1 \
\cb3     \cf4 \strokec4 "team2"\cf2 \strokec2 : \cf5 \strokec5 "team1\'bb\cf2 \strokec2 ,\cb1 \
\cb3     \cf4 \strokec4 "score"\cf2 \strokec2 : \{\cb1 \
\cb3         \cf4 \strokec4 "team1Score"\cf2 \strokec2 : \cf6 \strokec6 0\cf2 \strokec2 ,\cb1 \
\cb3         \cf4 \strokec4 "team2Score"\cf2 \strokec2 : \cf6 \strokec6 0\
\cf2 \strokec2     \}\cb1 \
\cb3 \}\
To identify created event I used unique combination of team labels.\cb1 \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 \kerning1\expnd0\expndtw0 \outl0\strokewidth0 \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 	\
}