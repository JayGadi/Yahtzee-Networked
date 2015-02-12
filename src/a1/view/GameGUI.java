package a1.view;

import java.awt.*;

import javax.swing.*;

import a1.model.Dice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import a1.model.Person;
import a1.control.GameClient;

public class GameGUI extends JFrame implements ActionListener{


	ImageIcon[] whiteDice = {
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceOneWhite.png")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceTwoWhite.png")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceThreeWhite.png")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceFourWhite.PNG")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceFiveWhite.PNG")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceSixWhite.PNG"))
	};

	ImageIcon[] blackDice = {
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceOneBlack.png")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceTwoBlack.png")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceThreeBlack.png")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceFourBlack.png")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceFiveBlack.PNG")),
			new ImageIcon(this.getClass().getResource("DiceFaces/FaceSixBlack.PNG"))
	};
	JFrame frame;
	int counter = 0;

	JLabel diceOne = new JLabel();
	JLabel diceTwo = new JLabel();
	JLabel diceThree = new JLabel();
	JLabel diceFour = new JLabel();
	JLabel diceFive = new JLabel();

	JButton [] p1UpperScore = new JButton[7];
	JButton [] p2UpperScore  = new JButton[7];
	JButton [] p3UpperScore = new JButton[7];
	JButton [] p4UpperScore = new JButton[7];

	JLabel [] p1UpperScoreLabel = new JLabel[3];
	JLabel [] p2UpperScoreLabel = new JLabel[3];
	JLabel [] p3UpperScoreLabel = new JLabel[3];
	JLabel [] p4UpperScoreLabel = new JLabel[3];

	JButton [] p1LowerScore = new JButton[9];
	JButton [] p2LowerScore  = new JButton[9];
	JButton [] p3LowerScore = new JButton[9];
	JButton [] p4LowerScore = new JButton[9];

	JLabel [] p1LowerScoreLabel = new JLabel[3];
	JLabel [] p2LowerScoreLabel = new JLabel[3];
	JLabel [] p3LowerScoreLabel = new JLabel[3];
	JLabel [] p4LowerScoreLabel = new JLabel[3];

	JLabel playerOne = new JLabel("Player One");
	JLabel playerTwo = new JLabel("Player Two");
	JLabel playerThree = new JLabel("Player Three");
	JLabel playerFour = new JLabel("Player Four");

	JButton roll = new JButton("Roll Dice");
	JButton ready = new JButton("Ready");
	public JButton play = new JButton("Click to Play");

	public String name;

	public String activePlayer, currPlayer;
	public ArrayList<Person> allPlayers;
	public GameClient client;
	private int currIndex;
	private ReadyScreen rs;
	private Start startMenu;
	private WaitScreen ws;

	private JPanel dicePanel;
	private JPanel buttonPanel;

	JPanel upperScore = new JPanel(new GridBagLayout());
	Dice dice = new Dice();
	private DiceScreen ds;

	private String currPanel;

	public GameGUI(String frameName){

		allPlayers = new ArrayList<Person>();
		startMenu = new Start(this);
		currPanel = new String("start");
		setSize(500,500);
		getContentPane().add(startMenu);
		setVisible(true);

	}

	public void launch(){
		client = new GameClient(this);
		allPlayers = new ArrayList<Person>();
		//frame = new JFrame(frameName);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





		JLabel title = new JLabel("Yahtzee");
		title.setHorizontalAlignment(SwingConstants.CENTER);

		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				name = JOptionPane.showInputDialog(frame, "Please enter a name");

				allPlayers.add(new Person(name));
				client.run();
				client.addPlayer();
				client.getPlayers();
				System.out.println(allPlayers.size());
				if(allPlayers.size() > 4){
					client.closeSocket();
					System.exit(-1);
				}
				frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
				frame.getContentPane().add(createScoreSheet(), BorderLayout.CENTER);


				upperScore.setVisible(true);
				buttonPanel.setVisible(true);

				roll.setVisible(true);
				play.setVisible(false);



			}
		});

		ready.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				client.isReady();
				if(client.readyCheck()){
					roll.setEnabled(true);
				}
				ready.setEnabled(false);

			}
		});

		buttonPanel.add(ready);

		buttonPanel.setVisible(false);
		roll.setEnabled(false);
		upperScore.setVisible(false);
		roll.setVisible(false);
		play.setVisible(true);

		frame.getContentPane().add(play, BorderLayout.CENTER);
		frame.getContentPane().add(dicePanel, BorderLayout.WEST);
		frame.getContentPane().add(title, BorderLayout.NORTH);


		frame.setSize(1000,700);
		frame.setVisible(true);


	}
	public void runGame(){
		client = new GameClient(this);
		client.run();
		client.addPlayer();

		getContentPane().removeAll();
		setVisible(false);
		
		currPanel = new String("ready");
		currPlayer = new String(allPlayers.get(0).getName());

		rs = new ReadyScreen(this, getPlayerNames());


		getContentPane().add(rs);
		setVisible(true);
	}

	public void stopUpdaters()
	{
		if(currPanel.equals("wait"))
			ws.stopUpdater();
	}


	public void startUpdaters()
	{
		if(currPanel.equals("wait"))
			ws.startUpdater();
	}

	public void showDice(){
		dicePanel = new JPanel(new GridLayout(5, 1));
		buttonPanel = new JPanel(new GridLayout(1,2));

		getContentPane().removeAll();
		getContentPane().setVisible(false);

		diceOne.setIcon(whiteDice[0]);
		diceTwo.setIcon(whiteDice[0]);
		diceThree.setIcon(whiteDice[0]);
		diceFour.setIcon(whiteDice[0]);
		diceFive.setIcon(whiteDice[0]);

		diceOne.setEnabled(false);
		diceTwo.setEnabled(false);
		diceThree.setEnabled(false);
		diceFour.setEnabled(false);
		diceFive.setEnabled(false);

		diceOne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dice.reserveDice(0);
				if(dice.isReserved(0))
					diceOne.setIcon(blackDice[getIconIndex(diceOne)]);

			}
		});

		diceTwo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dice.reserveDice(1);
				if(dice.isReserved(1))
					diceTwo.setIcon(blackDice[getIconIndex(diceTwo)]);
			}
		});
		diceThree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dice.reserveDice(2);
				if(dice.isReserved(2))
					diceThree.setIcon(blackDice[getIconIndex(diceThree)]);
			}
		});
		diceFour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dice.reserveDice(3);
				if(dice.isReserved(3))
					diceFour.setIcon(blackDice[getIconIndex(diceFour)]);
			}
		});
		diceFive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dice.reserveDice(4);
				if(dice.isReserved(4))
					diceFive.setIcon(blackDice[getIconIndex(diceFive)]);
			}
		});

		roll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(counter < 3){

					diceOne.setEnabled(true);
					diceTwo.setEnabled(true);
					diceThree.setEnabled(true);
					diceFour.setEnabled(true);
					diceFive.setEnabled(true);

					dice.roll();
					int []faces = dice.getDiceVal();
					dice.printDice();
					System.out.println(" ");
					setDiceIcon(diceOne, faces[0], dice.isReserved(0));
					setDiceIcon(diceTwo, faces[1], dice.isReserved(1));
					setDiceIcon(diceThree, faces[2], dice.isReserved(2));
					setDiceIcon(diceFour, faces[3], dice.isReserved(3));
					setDiceIcon(diceFive, faces[4], dice.isReserved(4));
					counter++;
				}
				else{
					roll.setEnabled(false);
					JOptionPane.showMessageDialog(null, "No More Rolls Remaining");

				}



			}
		});


		dicePanel.add(diceOne);
		dicePanel.add(diceTwo);
		dicePanel.add(diceThree);
		dicePanel.add(diceFour);
		dicePanel.add(diceFive);

		buttonPanel.add(roll);

		getContentPane().add(dicePanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);

	}
	public void startGame(){
		
		activePlayer = new String(client.getActive());

		getContentPane().removeAll();
		setVisible(false);

		ds = new DiceScreen(this);

		if(client.cont()){
			if(client.thisTurn()){
				getContentPane().add(ds);
				setVisible(true);
			}
			else{
				wait(allPlayers.get(client.getIndex()));
			}
		}
		else{
			System.exit(-1);
		}

	}

	public void setDiceIcon(JLabel die, int value, boolean reserve){
		if(!reserve){
			switch(value){
			case 1: die.setIcon(whiteDice[0]);
			break;
			case 2: die.setIcon(whiteDice[1]);
			break;
			case 3: die.setIcon(whiteDice[2]);
			break;
			case 4: die.setIcon(whiteDice[3]);
			break;
			case 5: die.setIcon(whiteDice[4]);
			break;
			case 6: die.setIcon(whiteDice[5]);
			break;

			}
		}

	}

	public void addPlayer(String name)
	{  
		if(currPlayer == null){
			currPlayer = new String(name);
		}
		Person temp = new Person(name);     
		allPlayers.add(temp);
	}

	public ArrayList<String> getPlayerNames()
	{
		ArrayList<String> temp = new ArrayList<String>();

		for(Person p : allPlayers)
			temp.add(p.getName());    

		return temp;
	}

	public void canIGo(){
		if(client.isNextTurn())
		{
			getContentPane().removeAll();
			setVisible(false);
			startGame();       
		}
	}

	public void wait(Person player){
		
		currPanel = new String("wait");

		ws = new WaitScreen(this);
		getContentPane().add(ws);
		setVisible(true);
	}
	public JPanel createScoreSheet(){



		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weightx=0.5;
		c.gridx=0;
		c.gridy=0;

		upperScore.add(new JLabel(""), c);

		JLabel upperSection = new JLabel("Upper Section");
		upperSection.setFont(new Font("Times New Roman", Font.BOLD, 18));
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		c.ipady=10;
		upperScore.add(upperSection, c);

		JLabel instruction = new JLabel("How to Score");
		instruction.setFont(new Font("Times New Roman", Font.BOLD, 18));
		c.weightx=0.5;
		c.gridy = 0;
		c.gridx = 2;
		upperScore.add(instruction, c);

		playerOne.setFont(new Font("Times New Roman", Font.BOLD, 18));
		c.weightx = 0.5;
		c.gridy = 0;
		c.gridx = 3;
		upperScore.add(playerOne, c);


		playerTwo.setFont(new Font("Times New Roman", Font.BOLD, 18));
		c.weightx = 0.5;
		c.gridy = 0;
		c.gridx = 4;
		upperScore.add(playerTwo, c);

		playerThree.setFont(new Font("Times New Roman", Font.BOLD, 18));
		c.weightx = 0.5;
		c.gridy = 0;
		c.gridx = 5;
		upperScore.add(playerThree, c);

		playerFour.setFont(new Font("Times New Roman", Font.BOLD, 18));
		c.weightx = 0.5;
		c.gridy = 0;
		c.gridx = 6;
		upperScore.add(playerFour, c);

		c.gridx=1;
		for(int i = 1; i < 10; i++){
			String num = null;
			c.weightx = 0.5;
			c.gridy = i;
			switch(i){
			case 1: num = "Aces"; break;
			case 2: num = "Twos"; break;
			case 3: num = "Threes"; break;
			case 4: num = "Fours"; break;
			case 5: num = "Fives"; break;
			case 6: num = "Sixes"; break;
			case 7: num = "Total Score"; break;
			case 8: num = "Bonus"; break;
			case 9: num = "Total Score"; break;
			}
			upperScore.add(new JLabel(num), c);

		}

		c.gridx=2;
		for(int i = 1; i < 10; i++){
			String num = null;
			c.weightx = 0.5;
			c.gridy = i;
			switch(i){
			case 1: num = "Aces"; break;
			case 2: num = "Twos"; break;
			case 3: num = "Threes"; break;
			case 4: num = "Fours"; break;
			case 5: num = "Fives"; break;
			case 6: num = "Sixes"; break;
			case 7: num = "-------->"; break;
			case 8: num = "Score 35"; break;
			case 9: num = "-------->"; break;
			}
			if(i < 7){
				upperScore.add(new JLabel("Count And Add Only " + num), c);
			}
			else{
				upperScore.add(new JLabel(num), c);
			}
		}

		for(int j = 1; j < 7; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 3;
			for(int i = 0; i < 7; i++){
				p1UpperScore[i] = new JButton("");
				p1UpperScore[i].addActionListener(this);
				upperScore.add(p1UpperScore[i], c);

			}
		}




		for(int j = 1; j < 7; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 4;
			for(int i = 0; i < 7; i++){
				p2UpperScore[i] = new JButton("");
				upperScore.add(p2UpperScore[i], c);
				p2UpperScore[i].addActionListener(this);
			}
		}

		for(int j = 1; j < 7; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 5;
			for(int i = 0; i < 7; i++){
				p3UpperScore[i] = new JButton("");
				upperScore.add(p3UpperScore[i], c);
				p3UpperScore[i].addActionListener(this);
			}
		}

		for(int j = 1; j < 7; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 6;
			for(int i = 0; i < 7; i++){
				p4UpperScore[i] = new JButton("");
				upperScore.add(p4UpperScore[i], c);
				p4UpperScore[i].addActionListener(this);
			}
		}

		for(int i = 7; i < 10; i++){
			c.weightx = 0.5;
			c.gridy = i;
			c.gridx = 3;
			for(int j = 0; j < 3; j++){
				p1UpperScoreLabel[j] = new JLabel("1");
				upperScore.add(p1UpperScoreLabel[j], c);
			}

		}

		for(int i = 7; i < 10; i++){
			c.weightx = 0.5;
			c.gridy = i;
			c.gridx = 4;
			for(int j = 0; j < 3; j++){
				p2UpperScoreLabel[j] = new JLabel("2");
				upperScore.add(p2UpperScoreLabel[j], c);
			}

		}

		for(int i = 7; i < 10; i++){
			c.weightx = 0.5;
			c.gridy = i;
			c.gridx = 5;
			for(int j = 0; j < 3; j++){
				p3UpperScoreLabel[j] = new JLabel("3");
				upperScore.add(p3UpperScoreLabel[j], c);
			}

		}

		for(int i = 7; i < 10; i++){
			c.weightx = 0.5;
			c.gridy = i;
			c.gridx = 6;
			for(int j = 0; j < 3; j++){
				p4UpperScoreLabel[j] = new JLabel("4");
				upperScore.add(p4UpperScoreLabel[j], c);
			}

		}

		c.weightx=0.5;
		c.gridx=0;
		c.gridy=10;
		upperScore.add(new JLabel(""), c);


		JLabel lowerSection = new JLabel("Lower Section");
		lowerSection.setFont(new Font("Times New Roman", Font.BOLD, 18));
		c.weightx=0.5;
		c.gridx = 1;
		c.gridy=11;
		c.ipady = 10;

		upperScore.add(lowerSection, c);

		for(int i = 12; i < 24; i++){
			String cat = null;
			c.weightx = 0.5;
			c.gridy = i;
			c.gridx = 1;

			switch(i){
			case 12: cat = "3 of a Kind"; break;
			case 13: cat = "4 of a Kind"; break;
			case 14: cat = "Full House"; break;
			case 15: cat = "Sm. Straight"; break;
			case 16: cat = "Lg. Straight"; break;
			case 17: cat = "YAHTZEE"; break;
			case 18: cat = "Chance"; break;
			//case 19: cat = "Yahtzee Bonus"; break;
			case 21: cat = "Total of Lower Section"; break;
			case 22: cat = "Total of Upper Section"; break;
			case 23: cat = "Grand Total"; break;
			}
			upperScore.add(new JLabel(cat), c);


		}
		c.gridy = 19;
		c.gridheight = 2;
		upperScore.add(new JLabel("Yahtzee Bonus"), c);


		for(int i = 12; i < 24; i++){
			String cat = null;
			c.weightx = 0.5;
			c.gridy = i;
			c.gridx = 2;

			switch(i){
			case 12: cat = "Add Total of All Dice"; break;
			case 13: cat = "Add Total of All Dice"; break;
			case 14: cat = "Score 25"; break;
			case 15: cat = "Score 30"; break;
			case 16: cat = "Score 40"; break;
			case 17: cat = "Score 50"; break;
			case 18: cat = "Score Total Of All 5 Dice"; break;
			//case 19: cat = "For Each Tick"; break;
			case 21: cat = "------>"; break;
			case 22: cat = "------>"; break;
			case 23: cat = "------>"; break;
			}
			upperScore.add(new JLabel(cat), c);


		}

		c.gridy = 19;
		c.gridheight = 2;
		upperScore.add(new JLabel("For Each Tick"), c);


		for(int j = 12; j < 19; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 3;
			for(int i = 0; i < 9; i++){
				p1LowerScore[i] = new JButton("");
				upperScore.add(p1LowerScore[i], c);
			}
		}

		for(int j = 12; j < 19; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 4;
			for(int i = 0; i < 9; i++){
				p2LowerScore[i] = new JButton("");
				upperScore.add(p2LowerScore[i], c);
			}
		}

		for(int j = 12; j < 19; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 5;
			for(int i = 0; i < 9; i++){
				p3LowerScore[i] = new JButton("");
				upperScore.add(p3LowerScore[i], c);
			}
		}

		for(int j = 12; j < 19; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 6;
			for(int i = 0; i < 9; i++){
				p4LowerScore[i] = new JButton("");
				upperScore.add(p4LowerScore[i], c);
			}
		}

		for(int j = 21; j < 24; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 3;
			for(int i = 0; i < 3; i++){
				p1LowerScoreLabel[i] = new JLabel("1");
				upperScore.add(p1LowerScoreLabel[i], c);
			}
		}

		for(int j = 21; j < 24; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 4;
			for(int i = 0; i < 3; i++){
				p2LowerScoreLabel[i] = new JLabel("1");
				upperScore.add(p2LowerScoreLabel[i], c);
			}
		}

		for(int j = 21; j < 24; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 5;
			for(int i = 0; i < 3; i++){
				p3LowerScoreLabel[i] = new JLabel("1");
				upperScore.add(p3LowerScoreLabel[i], c);
			}
		}

		for(int j = 21; j < 24; j++){
			c.weightx = 0.5;
			c.gridy = j;
			c.gridx = 6;
			for(int i = 0; i < 3; i++){
				p4LowerScoreLabel[i] = new JLabel("1");
				upperScore.add(p4LowerScoreLabel[i], c);
			}
		}

		//scoreSheet.add(upperScore, BorderLayout.NORTH);
		//upperScore.setVisible(false);
		return upperScore;
	}

	public JPanel determineCurrentPanel()
	{
		if(currPanel.equals("start"))
			return startMenu;
		else if(currPanel.equals("dice"))
			return ds;
		else if(currPanel.equals("ready"))
			return rs;
		else if(currPanel.equals("wait"))
			return ws;
		else
			return startMenu;
		
		
	}

	public int getIconIndex(JLabel die){
		int index = 0;
		for(int i = 0; i < whiteDice.length; i++){
			if(die.getIcon() == whiteDice[i]){
				index = i;
			}
		}
		return index;
	}
	public static void main(String[] args) {

		GameGUI frame = new GameGUI("Yahtzee");
	}

	@Override
	public void actionPerformed(ActionEvent e) {


	}

}
