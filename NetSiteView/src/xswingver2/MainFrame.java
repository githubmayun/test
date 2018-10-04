package xswingver2;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width, height, screenWidth, screenHeight;
	JPanel contentPanel;
	private Map<String, LabelModel> sitesM = new HashMap<>();
	private Map<String, LineModel> linesM = new HashMap<>();
	// GraphPanel gpanel=null;
	GraphPanelver2 gpanel = null;
	ReceiveDatasByMulticast dbe = null;

	public MainFrame(int width, int height, ReceiveDatasByMulticast dbe) {
		this.width = width;
		this.height = height;
		this.dbe = dbe;
		setSize(width, height);
		this.setBounds(getScreenWidth() / 2 - this.getWidth() / 2, getScreenHeight() / 2 - this.getHeight() / 2,
				this.width, this.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		init();
	}

	public boolean init() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		JMenuItem newItem = new JMenuItem("New");
		menu.add(newItem);
		newItem.addActionListener(EventHandler.create(ActionListener.class, this, "newPanel"));
		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);
		exitItem.addActionListener(EventHandler.create(ActionListener.class, this, "exitProgram"));
		return true;
	}

	public void newPanel() {
		if (gpanel == null)
			new ParseSites().execute();
	}

	public void exitProgram() {
		System.exit(0);
	}

	public int getScreenWidth() {
		screenWidth = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		return screenWidth;
	}

	public int getScreenHeight() {
		screenHeight = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		return screenHeight;
	}

	//
	class WindowBehaviour extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	//
	public class ParseSites extends SwingWorker<Boolean, String> {

		@Override
		protected Boolean doInBackground() throws Exception {
			// TODO Auto-generated method stub
			new ModelsByXML().parseSitesByStreamReader(sitesM, linesM);
			return true;
		}

		protected void done() {
			try {
				if (get()) {
					// gpanel = new GraphPanel("background.jpg",1100,700,sitesM,linesM);
					new ArcLoction(sitesM).loc(1750,800);
					//new CircleLoction(sitesM).loc(1750, 800);
					gpanel = new GraphPanelver2("background.jpg", 1750, 800, sitesM, linesM);
					dbe.addRefreshing(gpanel);
					MainFrame.this.add(gpanel);
					validate();
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(MainFrame.this, e);
			}
		}

	}

}
