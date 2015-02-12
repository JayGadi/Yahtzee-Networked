package a1.model;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class ScoreCard implements Serializable {

	private static final long serialVersionUID = -3360968343824410021L;

	private Map<String, Integer> lowerSection = new HashMap<String, Integer>();
	private Map<String, Integer> upperSection = new HashMap<String, Integer>();

	private int [] dieValue;

	public ScoreCard() {

		dieValue = new int[5];

		upperSection.put("ACES", 0);
		upperSection.put("TWOS", 0);
		upperSection.put("THREES", 0);
		upperSection.put("FOURS", 0);
		upperSection.put("FIVES", 0);
		upperSection.put("SIXES", 0);

		lowerSection.put("3_OF_A_KIND", 0);
		lowerSection.put("4_OF_A_KIND", 0);
		lowerSection.put("FULL_HOUSE", 0);
		lowerSection.put("SMALL_STRAIGHT", 0);
		lowerSection.put("LARGE STRAIGHT", 0);
		lowerSection.put("YAHTZEE", 0);
		lowerSection.put("YAHTZEE_BONUS", 0);
		lowerSection.put("CHANCE", 0);

	}

	public void setDieValue(int [] theValues){
		for(int i = 0; i < 5; i++){
			dieValue[i] = theValues[i];
		}
	}

	private void setCategory(String cat, int score){
		if(lowerSection.containsKey(cat)){
			lowerSection.put(cat, score);
		}
		else if(upperSection.containsKey(cat)){
			upperSection.put(cat, score);
		}
		else{
			System.out.println("ERROR");
		}
	}

	public void aces(){
		int temp = 0;
		for(int i = 0; i < 5; i++){
			if(dieValue[i] == 1){
				temp++;			
			}
		}
		setCategory("ACES", temp);
	}

	public void twos(){
		int temp = 0;
		for(int i = 0;i < 5; i++){
			if(dieValue[i] == 2){
				temp ++;
			}
		}
		temp = temp * 2;
		setCategory("TWOS", temp);
	}

	public void threes(){
		int temp = 0;
		for(int i = 0;i < 5; i++){
			if(dieValue[i] == 3){
				temp ++;
			}
		}
		temp = temp * 3;
		setCategory("THREES", temp);
	}
	public void fours(){
		int temp = 0;
		for(int i = 0;i < 5; i++){
			if(dieValue[i] == 4){
				temp ++;
			}
		}
		temp = temp * 4;
		setCategory("FOURS", temp);
	}

	public void fives(){
		int temp = 0;
		for(int i = 0;i < 5; i++){
			if(dieValue[i] == 5){
				temp ++;
			}
		}
		temp = temp * 5;
		setCategory("FIVES", temp);
	}

	public void sixes(){
		int temp = 0;
		for(int i = 0;i < 5; i++){
			if(dieValue[i] == 6){
				temp ++;
			}
		}
		temp = temp * 6;
		setCategory("SIXES", temp);
	}

	private boolean isThreeOfAKind(int [] temp){
		int same = 0;
		for(int i = 0; i < temp.length; i++){
			if(temp[i] == temp[i + 1]){
				same++;
			}
			else{
				same = 0;
			}
		}
		if(same == 2){
			return true;
		}
		return false;
	}

	private void threeOfAKind(){
		int [] temp = (int[])dieValue.clone();
		int sum = 0;
		Arrays.sort(temp);

		if(isThreeOfAKind(temp)){
			for(int i =0; i < temp.length; i++){
				sum+= temp[i];
			}
		}
		setCategory("3_OF_A_KIND", sum);
	}

	private boolean isFourOfAKind(int [] temp){
		int same = 0;
		for(int i = 0; i < temp.length; i++){
			if(temp[i] == temp[i + 1]){
				same++;
			}
			else{
				same = 0;
			}
		}
		if(same == 3){
			return true;
		}
		return false;
	}

	private void fourOfAKind(){
		int [] temp = (int[])dieValue.clone();
		int sum = 0;
		Arrays.sort(temp);
		if(isFourOfAKind(temp)){
			for(int i = 0; i < temp.length; i++){
				sum += temp[i];
			}
		}
		setCategory("4_OF_A_KIND", sum);
	}

	private boolean isFullHouse(int [] temp){
		int count=0, upperCount=0, lowerCount = 0;
		int upperVal = temp[0];
		int lowerVal = 0;

		while(count < 5){
			if(upperVal == temp[count]){
				count++;
				upperCount++;
			}
			else{
				lowerVal = temp[count];
				lowerCount++;
				count++;
				break;
			}
		}

		if(count == 5){
			return false;
		}
		if(upperVal == lowerVal){
			return false;
		}

		while(count < 5){
			if(lowerVal == temp[count]){
				count++;
				lowerCount++;
			}
			else{
				break;
			}
		}

		if(upperCount == 2 && lowerCount == 3){
			return true;
		}
		if(upperCount == 3 && lowerCount == 2){
			return true;
		}

		return false;
	}

	private void fullHouse(){
		int [] temp = dieValue.clone();
		int sum = 0;
		Arrays.sort(temp);

		if(isFullHouse(temp)){
			sum+= 25;
		}
		setCategory("FULL_HOUSE", sum);
	}

	private boolean isSmallStraight(int [] temp){
		int count = 0;
		for(int i = 0; i < temp.length; i++){
			if(temp[i] == (temp[i + 1] - 1)){
				count++;
			}
			else if(temp[i] == temp[i+1]){
				count = count;
			}
			else{
				count = 0;
			}
		}
		if(count == 3){
			return true;
		}
		return false;
	}

	private void smallStraight(){
		int [] temp = (int[])dieValue.clone();
		int sum = 0;
		Arrays.sort(temp);

		if(isSmallStraight(temp)){
			sum+=30;
		}
		setCategory("SMALL_STRAIGHT", sum);
	}

	private boolean isLargeStraight(int [] temp){
		int count = 0;
		for(int i = 0; i < temp.length; i++){
			if(temp[i] == (temp[i + 1] - 1)){
				count++;
			}
			else if(temp[i] == temp[i+1]){
				count = count;
			}
			else{
				count = 0;
			}
		}
		if(count == 4){
			return true;
		}
		return false;
	}

	private void largeStraight(){
		int [] temp = (int[])dieValue.clone();
		int sum = 0;
		Arrays.sort(temp);

		if(isSmallStraight(temp)){
			sum+=40;
		}
		setCategory("LARGE_STRAIGHT", sum);
	}

	private boolean isYahtzee(int[] temp){
		int same = 0;
		for(int i = 0; i < temp.length; i++){
			if(temp[i] == temp[i + 1]){
				same++;
			}
			else{
				same = 0;
			}
		}
		if(same == 4){
			return true;
		}

		return false;
	}

	private void yahtzee(){
		int sum =0;
		int [] temp = dieValue.clone();
		if(isYahtzee(temp)){
			sum = 50;
		}
		setCategory("YAHTZEE", sum);
	}

	private void yahtzeeBonus(){
		int sum = 0;
		int[] temp = dieValue.clone();
		if(isYahtzee(temp)){
			sum = 100;
		}
		setCategory("YAHTZEE_BONUS", sum);
	}

	private void chance(){
		int sum = 0;
		int [] temp = dieValue.clone();
		for(int i = 0; i < temp.length; i++ ){
			sum+= temp[i];
		}
		setCategory("CHANCE", sum);

	}

	public int getUpperValue(String cat){
		if (upperSection.get(cat) == null){
			return 0;
		}
		else{
			return (int) upperSection.get(cat);
		}
	}
	
	public int getTotalUpper(){
		int sum = 0;
		for(int val : upperSection.values()){
			sum += (int) val;
		}
		return sum;
	}
	public int getTotalUpperBonus(){
		int sum = 0;
		for(int val : upperSection.values()){
			sum += (int) val;
		}
		if(sum > 63){
			sum += 35;
		}
		return sum;
	}

	public int getLowerValue(String cat){
		return (int) lowerSection.get(cat);
	}

	public int getTotalLower(){
		int sum = 0;
		for(int val: lowerSection.values()){
			sum += (int) val;
		}
		return sum;
	}

	public static void main(String[] args) {
		ScoreCard test = new ScoreCard();
		test.setCategory("ACES", 2);
		test.setCategory("TWOS", 5);
		test.setCategory("SMALL_STRAIGHT", 25);


		System.out.println(test.getUpperValue("ACES"));
		System.out.println(test.getLowerValue("SMALL_STRAIGHT"));
		System.out.println(test.getUpperValue("TWOS"));
		System.out.println(test.getTotalUpper());
		System.out.println(test.getTotalLower());
	}

}
