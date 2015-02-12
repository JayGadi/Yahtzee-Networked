package a1.model;

public class Die {
	private int value;
	private final int MAXVAL = 6;
	
	public Die() {
		value = 1;
	}

	public void setValue(int val){
		value = val;
	}
	public int getValue(){
		return value;
	}
	public int roll(){
		value = (int)(Math.random() * MAXVAL) + 1;
		return value;
	}


}
