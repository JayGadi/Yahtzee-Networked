package a1.control;


import java.net.*;
import java.util.ArrayList;
import java.io.*;

import a1.model.Person;
import a1.view.GameGUI;


public class GameClient {

	private Socket yahtzeeSocket;
	private PrintWriter out;
	private BufferedReader in;
	private GameGUI view;

	

	public GameClient(GameGUI view){
		this.view = view;
	}
	public void run(){

		try{ 	
			yahtzeeSocket = new Socket("", 4444);
			out = new PrintWriter(yahtzeeSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(yahtzeeSocket.getInputStream()));
		} 
		catch (UnknownHostException e) {
			System.err.println("Don't know about host ");
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to ");
			System.exit(1);
		}
	}
	public void addPlayer(){
		out.println("addPlayer");
		out.println(view.allPlayers.get(0).getName());
	}

	public void getPlayers()
	{
		out.println("getPlayers");
		System.out.println("Getting Players from Server");

		view.allPlayers = new ArrayList<Person>();

		try{
			while(true)
			{
				String name = new String(in.readLine());
				if(name.equals("stop"))
					break;

				Person newPlayer = new Person(name);
				System.out.println(newPlayer);
				view.allPlayers.add(newPlayer);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println(view.allPlayers);
	}
	
	public int getIndex(){
		out.println("getIndex");
		System.out.println("Retreive Server Index");

		try{
			String res = new String(in.readLine());
			System.out.println("Client: " + res);

			int nextIndex = Integer.parseInt(res);
			return nextIndex;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return 0;
	}

	public boolean readyCheck()
	{
		System.out.println("Checking if Players are ready");
		out.println("whosReady");
		try{
			String res = new String(in.readLine());
			System.out.println("Res: " + res);

			if(res.equals("start")){
				return true;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return false;  
	}
	public void isReady(){
		out.println("ready");
	}

	public boolean cont()
	{
		System.out.println("Continue?");
		out.println("cont");

		try{
			String res = new String(in.readLine());
			System.out.println("Server: " + res);

			if(res.equals("good"))
				return true; 
		}
		catch(IOException e){
			e.printStackTrace();
		}

		return false;
	}  
	public String getActive(){
		System.out.println("Active Player");
		out.println("activePlayer");

		try{
			String res = new String(in.readLine());
			System.out.println("Server: " + res);

			return res;
		}
		catch(IOException e){
			e.printStackTrace();
		}

		return "";
	}
	public boolean isNextTurn()
	{
		out.println("turnChange");
		System.out.println("Check for Turn Change");
		out.println(view.activePlayer);

		try{
			String res = new String(in.readLine());
			if(res.equals("good"))
				return true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public boolean thisTurn()
	{
		System.out.println(view.currPlayer + " wants to know if it's their turn.");
		out.println("myTurn");
		out.println(view.currPlayer);

		try{
			String res = new String(in.readLine());
			System.out.println("Server: " + res);
			if(res.equals("good"))
				return true;
			else
				return false;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return false;
	}
	public void doneTurn(){
		out.println("doneTurn");

	}

	public void closeSocket()
	{
		try{
			yahtzeeSocket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
