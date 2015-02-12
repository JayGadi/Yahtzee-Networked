package a1.model;

import java.io.Serializable;
import java.util.*;


public class Dice implements Serializable{

	private static final long serialVersionUID = 8142357607379959214L;
	
	private boolean [] reserved = {false, false, false, false, false};
	private Die [] dice;
	
	
	public Dice() {
		dice = new Die[5];
		for(int i = 0; i < 5; i++){
			dice[i] = new Die();
		}
		
	}
	
	public void roll(){
		
		for(int i = 0; i < 5; i++){
			if(reserved[i] == false){
				dice[i].roll();
			}
		}
		
	}
	
	public int [] getDiceVal(){
		int [] diceVal = new int[5];
		for(int i = 0;i < 5; i++){
			diceVal[i] = dice[i].getValue();
		}
		return diceVal;
	}
	
	public void reserveDice(int index){
		if(index < 5){
			reserved[index] = !reserved[index];
		}
	}
	
	public boolean isReserved(int index){
		return reserved[index];
	}
	
	public void printDice(){
		for(int i = 0; i < 5; i++){
			System.out.println(getDiceVal()[i]);
		}
	}
	
	public String toString(){
		String values = "";
		for(int i = 0; i < dice.length; i++){
			values += String.valueOf(getDiceVal()[i]) + " ";
		}
		return values;
	}
	
	public static void main(String[] args) {
		Dice test = new Dice();
		test.roll();
		System.out.println(test);
		
		
				
				
	}


}