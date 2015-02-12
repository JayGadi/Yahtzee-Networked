package a1.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WaitScreen extends JPanel implements ActionListener {

	private GameGUI view;
	private JCheckBox ready;
	private JLabel title;
	private Runner refresh;
	public WaitScreen(GameGUI view) {
		super(new BorderLayout());
		this.view = view;
		
		setSize(300,300);
		title = new JLabel("Waiting For Player To Take Turn");
		
		refresh = new Runner(this);
		Thread t = new Thread(refresh);
		t.start();
		
		add(title, BorderLayout.NORTH);
		
		setVisible(true);

	}
	
	private class Runner implements Runnable{
		private Thread update;
		private WaitScreen ws;
		private boolean go;
		
		public Runner(WaitScreen ws){
			this.ws=ws;
			go = true;
		}
		
		public void run(){
			while(go){
				try{
					Thread.sleep(1000);
										
					if(ws.getRefreshStatus()){
						go = false;
						ws.view.canIGo();
					}
				}
				catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}
				
			}
		}
		public void stopUpdate(){
			go = false;
			if(update != null)
				update.interrupt();
		}
	}
	
	 public void startUpdater()
	  {
	    refresh = new Runner(this);
	    Thread t = new Thread(refresh);
	    t.start();
	  }
	 
	 
	  public void stopUpdater()
	  {
	    refresh.stopUpdate();
	    try{
	      Thread.sleep(1050);   
	    }
	    catch(InterruptedException e){
	      e.printStackTrace();
	    }
	  }
	  
	  
	  public boolean getRefreshStatus()
	  {
	    return view.client.isNextTurn();
	  }
	
	     
	
	  public void actionPerformed(ActionEvent e)
	  {
	    String command = new String(e.getActionCommand());
	    
	    if(command.equals("Refresh"))
	    {
	      if(view.client.isNextTurn())
	      {
	        view.canIGo();
	      }
	    }
	  }
	 

}
