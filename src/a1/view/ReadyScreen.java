package a1.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class ReadyScreen extends JPanel implements ActionListener {

	private GameGUI view;
	private JPanel playerPanel, buttonPanel;
	private JTextArea allPlayers;
	private JLabel title;
	private JButton ready;


	public ReadyScreen(GameGUI view, ArrayList<String> players) {
		super(new BorderLayout(5,5));
		this.view = view;
		setSize(300,300);
		title = new JLabel("Waiting Room");

		playerPanel = new JPanel(new BorderLayout());
		allPlayers = new JTextArea();
		playerPanel.setSize(300,300);


		allPlayers.setEditable(false);
		allPlayers.append("------Current Players------\n");
		allPlayers.append(players.get(0) + "\n");

		playerPanel.add(allPlayers);

		buttonPanel = new JPanel(new BorderLayout());

		ready = new JButton("I am Ready!");
		ready.addActionListener(this);
		ready.setActionCommand("ready");

		buttonPanel.add(ready);

		AllReady refresh = new AllReady(this);
		refresh.start();

		add(title, BorderLayout.NORTH);
		add(playerPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);



	}

	private class AllReady extends Thread{
		private ReadyScreen rs;
		private boolean allReady = true;

		public AllReady(ReadyScreen rs){
			this.rs = rs;
		}
		public void run(){
			while(allReady){
				try{
					sleep(1000);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				rs.refreshAll();

				if(rs.view.client.readyCheck()){
					allReady = false;
					rs.view.startGame();
				}
			}
		}
	}

	public boolean readyCheack(){
		return true;
	}

	public void updatePlayerNames(ArrayList<String> players)
	{
		int count = 1;

		allPlayers.setText(null);

		allPlayers.append("---Current Players---\n");

		for(String name : players)
		{
			allPlayers.append(count + ". " + name + "\n");  
			count++;  
		} 


	}
	public void refreshAll()
	{
		view.client.getPlayers();
		Thread.yield();
		updatePlayerNames(view.getPlayerNames());
		Thread.yield();
		view.client.readyCheck();
		Thread.yield();
	}

	public void actionPerformed(ActionEvent e)
	{
		String command = new String(e.getActionCommand());

		if(command.equals("ready"))
			view.client.isReady();
	}




}
