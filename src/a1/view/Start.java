package a1.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Start extends JPanel implements ActionListener {


	private GameGUI view;
	private JTextField entryName;
	private JPanel titlePanel, buttonPanel, entryPanel; 
	private JButton next;

	public Start(GameGUI view) {
		super(new BorderLayout (5, 5));
		setSize(300,300);
		this.view = view;

		titlePanel = new JPanel(new GridLayout());
		JLabel title = new JLabel("Yahtzee!");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		titlePanel.add(title);

		buttonPanel = new JPanel();
		next = new JButton("Continue");
		
		next.addActionListener(this);
		
		buttonPanel.add(next);
		
		entryPanel = new JPanel();
		entryName = new JTextField("Enter your name!");
		
		entryName.setActionCommand("name");
		entryPanel.add(entryName);
		
		add(buttonPanel, BorderLayout.SOUTH);
		add(titlePanel, BorderLayout.NORTH);
		add(entryPanel, BorderLayout.CENTER);
		
		setVisible(true);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String game = new String(e.getActionCommand());
		if(game.equals("Continue")){
			view.addPlayer(entryName.getText());
			view.runGame();
		}
		
	}
}
