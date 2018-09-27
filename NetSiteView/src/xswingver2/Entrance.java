package xswingver2;

import java.awt.EventQueue;

import javax.swing.JFrame;



public class Entrance {
	private static final int DEFAULT_WIDTH = 1800;
	private static final int DEFAULT_HEIGHT = 900;
	public static void main(String args[]) {
		DataBackEndver2 dbe=new DataBackEndver2();	
		new Thread(dbe).start();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new MainFrame(DEFAULT_WIDTH, DEFAULT_HEIGHT,dbe);
				frame.setTitle("网络态势图");
				frame.setVisible(true);
			}
		});
	  
	}
	
}
