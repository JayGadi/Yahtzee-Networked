# Yahtzee-Networked
Simple Dice game with Networking

The program is incomplete, however the server runs and allows for clients to connect. The game allows users to connect to server, takes them to a screen where they select if they are ready. When all players are ready, game begins. The person who's turn it is goes first, while the others wait in a waiting lobby. Was not able to implement the full game so inorder to show some functionality, the game just lets you roll dice right now. If you click on the dice, you can reserve the dice. ScoreSheet has been created and written, but did not have enough time to implement it. Was not able to implement the tracers as I ran out of time.  


Introduction: Start server in terminal using java -jar Server.java. Start multiple clients in their own terminals using java -jar Client.java.

Classes Included:

control:
GameServer.java
-Serves as the server for the networking aspect
GameClient.java
-serves as the game client for the networking aspect

model:
Die.java
Creates a single die and rolls it.
Dice.Java
-Creates an array of five dies, checkes if die are reserved, and rolls them if not.
Person.java
-The Players of the game
ScoreCard.java
Used to calculate  score based on category.

view:
DiceScreen.java
-JPanel that allows for the dice screen to be visible
GameGUI.java
-The main control class of the Program. Takes all the UIs, Models, and Server/Client and puts it together
ReadyScreen.java
-Screen that shows all the players in the lobby, and allows you to get ready to play the game
WaitScreen.java
-Screen that appears when you are waiting for your turn.
Start.java
-Start screen. Asks for Name.

