package xswingver2;

import java.awt.EventQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Entrance {
	public static void main(String args[]) {
	//start server...
		DataBackEndver2 dbe = new DataBackEndver2();
		SpreadDatasByMulticast sdbm = new SpreadDatasByMulticast("238.10.10.10", 4567, true);
		dbe.addRefreshing(sdbm);
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(dbe,5,1, TimeUnit.SECONDS);
		
	//start client
		ReceiveDatasByMulticast rdbm=new ReceiveDatasByMulticast("238.10.10.10",4567);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//JFrame frame = new MainFrame(DEFAULT_WIDTH, DEFAULT_HEIGHT, dbe);
				JFrame frame = new MainFrame(rdbm);
				frame.setTitle("网络态势图");
				frame.setVisible(true);
			}
		});
		new Thread(rdbm).start();
		
	}
}
