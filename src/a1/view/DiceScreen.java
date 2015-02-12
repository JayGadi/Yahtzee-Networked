package a1.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


import a1.model.Dice;

public class DiceScreen extends JPanel {

	GameGUI view;
	int counter;

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
	
	JLabel diceOne = new JLabel();
	JLabel diceTwo = new JLabel();
	JLabel diceThree = new JLabel();
	JLabel diceFour = new JLabel();
	JLabel diceFive = new JLabel();
	
	JButton roll = new JButton("Roll Dice");
	
	private JPanel dicePanel;
	private JPanel buttonPanel;
	Dice dice = new Dice();
	
	public DiceScreen(final GameGUI view) {
		super(new BorderLayout());
		this.view = view;
		
		counter = 0;
		
		dicePanel = new JPanel(new GridLayout(5, 1));
		buttonPanel = new JPanel(new GridLayout(1,2));
		
			
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
					JOptionPane.showMessageDialog(dicePanel, "No More Rolls Remaining");
					view.client.doneTurn();

				}



			}
		});


		dicePanel.add(diceOne);
		dicePanel.add(diceTwo);
		dicePanel.add(diceThree);
		dicePanel.add(diceFour);
		dicePanel.add(diceFive);

		buttonPanel.add(roll);
		
		add(dicePanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);

		
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
	
	public int getIconIndex(JLabel die){
		int index = 0;
		for(int i = 0; i < whiteDice.length; i++){
			if(die.getIcon() == whiteDice[i]){
				index = i;
			}
		}
		return index;
	}

}
