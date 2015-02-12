package a1.control;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import a1.model.Person;
import a1.view.GameGUI;


public class GameServer{

	private Thread server;
	String activePlayer;
	boolean canConnect;
	ArrayList<Person> allPlayers;
	ArrayList<ServerThread> connectedPlayers;
	int turnIndex;
	ServerSocket ss = null;
	private static int playerID = 0;


	public GameServer(){
		server = new Thread(new Server());
		server.start();
	}
	public class Server implements Runnable{


		public Server(){
			canConnect = true;
			allPlayers = new ArrayList<Person>();
			connectedPlayers = new ArrayList<ServerThread>();
			activePlayer = new String();
			turnIndex = 0;

			try{
				ss = new ServerSocket(4444);
			}
			catch(IOException e){
				e.printStackTrace();
			}

		}

		public void run(){

			while(server != null)
			{
				ServerThread st;
				try{
					Thread.sleep(100);
					st = new ServerThread(ss.accept(), this);
					if(!canConnect)
						server.interrupt();
					connectedPlayers.add(st);
					Thread t = new Thread(st);
					t.start();
				}   
				catch (IOException e) {
					System.out.println("Client accept connection failed!");
				} 
				catch (InterruptedException e){
					server.interrupt();
				}
			}
		}


		public boolean gameRunning(){
			if (true)
				return true;
			else
				return false;
		}

		public void interruptServer(){
			canConnect = false;
			server.interrupt();
		}

		public boolean checkReady(){
			for(ServerThread p: connectedPlayers){
				if(!(p.getReady())){
					return false;
				}
			}
			activePlayer = allPlayers.get(0).getName();
			return true;
		}
		public void setTurn(){
			if(turnIndex == (allPlayers.size() - 1)){
				turnIndex = 0;
			}
			else{
				turnIndex++;
			}
		}

	}


	public class ServerThread implements Runnable{

		private Socket socket = null;
		private Server server;
		private Thread client;
		private PrintWriter out = null;
		private BufferedReader in = null;
		private boolean continueServer;
		private boolean readyPlayer;
		private String name;


		public ServerThread(Socket socket,Server server) {
			this.server = server;
			this.socket = socket;
			readyPlayer = false;
			continueServer = true;
		}

		public void run() {

			try {
				try{
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out = new PrintWriter(socket.getOutputStream(), true);
				}
				catch(IOException e){
					System.out.println("I/O Failed");
					e.printStackTrace();
					System.exit(-1);
				}

				while (continueServer) {
					try{
						processInput(in.readLine());

					}
					catch(IOException e){
						System.out.println("Could Not Read From Server");
						e.printStackTrace();
						System.exit(-1);
					}
					Thread.sleep(100);
					Thread.yield();
				}

			} 
			catch (InterruptedException e) {
				continueServer = false;
				if(client != null){
					client.interrupt();
				}
			}
		}

		public void processInput(String theInput){

			if(theInput.equals("addPlayer"))
				addPlayer();
			else if(theInput.equals("startGame"))
				startGame();
			else if(theInput.equals("ready"))
				playerReady();
			else if(theInput.equals("activePlayer"))
				getActivePlayer();
			else if(theInput.equals("getPlayers"))
				getAllPlayers();
			else if((theInput.equals("getIndex")))
				getIndex();
			else if((theInput.equals("whosReady")))
				ready();
			else if((theInput.equals("myTurn")))
				myTurn();
			else if((theInput.equals("doneTurn")))
				nextTurn();
			else if((theInput.equals("turnChange")))
				turnChange();
			else if((theInput	.equals("cont")))
				cont();


		}

		public void startGame(){
			activePlayer = allPlayers.get(turnIndex).getName();
		}

		public void getActivePlayer(){
			out.println(activePlayer);
		}
		public void cont(){
			for(int i = 0; i < allPlayers.size(); i++){
				if(allPlayers.get(i).maxTurns()){
					out.println("good");
					return;
				}
			}
			out.println("bad");
		}
		public void addPlayer(){
			try{

				Person newPlayer = new Person(in.readLine());
				System.out.println("Adding "+ newPlayer.getName());
				allPlayers.add(newPlayer);
				name = new String(newPlayer.getName());
			}
			catch(IOException e){
				e.printStackTrace();
			}

		}

		public void getAllPlayers(){
			for(Person p: allPlayers){
				out.println(p.getName());
			}
			out.println("stop");
		}
		public void getIndex(){
			out.println(Integer.toString(turnIndex));
		}
		public void playerReady(){
			readyPlayer = !readyPlayer;
		}
		public boolean getReady(){
			return readyPlayer;
		}
		public void ready(){
			if(server.checkReady()){
				out.println("start");
				server.interruptServer();
			}
			else{
				out.println("no");
			}

		}
		public void nextTurn(){
			allPlayers.get(turnIndex).incTurn();
			server.setTurn();
			activePlayer = new String(allPlayers.get(turnIndex).getName());
		}
		public void myTurn(){
			try{
				String name = new String(in.readLine());
				boolean isActive = activePlayer.equals(name);
				
				if(isActive){
					out.println("good");
				}
				else{
					out.println("bad");
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		public void turnChange(){
			try{
				String name = new String(in.readLine());
				if(name.equals(activePlayer)){
					out.println("bad");
				}
				else{
					out.println("good");
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		GameServer gs = new GameServer();
	}
}