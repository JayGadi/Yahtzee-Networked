package a1.model;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -681622011582727807L;
	
	
	private String name;
	private ScoreCard sc;
	private Dice dice;
	private boolean ready;
	int turnNum;
	
	public Person(String name) {
		ready = false;
		sc = new ScoreCard();
		dice = new Dice();
		this.name = name;
		turnNum = 0;
		
	}
	
	public void setReady(boolean ready){
		this.ready = ready;
	}
	public boolean getReady(){
		return ready;
	}
	
	public String getName(){
		return name;
	}
	
	public ScoreCard getScoreCard(){
		return sc;
	}
	public Dice getDice(){
		return dice;
	}
	public int turnsLeft(){
		int temp = 13;
		return (temp - turnNum);
	}
	public void incTurn(){
		turnNum++;
	}
	public boolean maxTurns(){
		if(turnNum == 12)
			return false;
		return true;
		
	}
	
	
	

}
