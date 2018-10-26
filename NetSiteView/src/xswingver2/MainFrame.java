package xswingver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	JPanel topInfoPanel, bottomInfoPanel, buttonPanel, contentPanel;
	private Map<String, LabelModel> sitesM = new HashMap<>();
	private Map<String, LineModel> linesM = new HashMap<>();
	// GraphPanel gpanel=null;
	GraphPanelver2 gpanel = null;
	ReceiveDatasByMulticast dbe = null;

	public MainFrame(ReceiveDatasByMulticast dbe) {
		this.width = getScreenWidth();
		this.height = getScreenHeight();
		this.dbe = dbe;
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		init();
	}

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
		/*
		 * JMenuBar menuBar = new JMenuBar(); //setJMenuBar(menuBar); JMenu menu = new
		 * JMenu("File"); menuBar.add(menu); JMenuItem newItem = new JMenuItem("New");
		 * menu.add(newItem);
		 * newItem.addActionListener(EventHandler.create(ActionListener.class, this,
		 * "newPanel")); JMenuItem exitItem = new JMenuItem("Exit"); menu.add(exitItem);
		 * exitItem.addActionListener(EventHandler.create(ActionListener.class, this,
		 * "exitProgram"));
		 */
		this.setLayout(new BorderLayout());
		topInfoPanel = new JPanel();
		topInfoPanel.setPreferredSize(new Dimension(width / 8, height / 8));
		topInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		topInfoPanel.setBackground(new Color(146, 150, 203));
		topInfoPanel.setLayout(new GridLayout(1, 6));

		//
		bottomInfoPanel = new JPanel();
		bottomInfoPanel.setPreferredSize(new Dimension(width / 12, height / 24));
		bottomInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		bottomInfoPanel.setBackground(new Color(146, 150, 203));
		//
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(width / 8, height * 5 / 6));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		buttonPanel.setBackground(new Color(146, 150, 203));
		GridLayout gl = new GridLayout(12, 1);
		gl.setVgap(0);
		buttonPanel.setLayout(gl);
		buttonPanel.add(buildButton("网络态势1", new Color(130, 130, 255), new Color(255, 255, 255)));
		buttonPanel.add(buildButton("网络态势1", new Color(91, 85, 147), new Color(255, 255, 255)));
		buttonPanel.add(buildButton("网络态势3", new Color(91, 85, 147), new Color(255, 255, 255)));
		//
		contentPanel = new JPanel();
		// contentPanel.setPreferredSize(new Dimension(width*5/6,height*2/3));
		contentPanel.setLayout(null);
		//
		this.add(topInfoPanel, BorderLayout.NORTH);
		this.add(bottomInfoPanel, BorderLayout.SOUTH);
		this.add(buttonPanel, BorderLayout.WEST);
		this.add(contentPanel, BorderLayout.CENTER);

		contentPanel.add(newNetGraphPanel(width * 7 / 8, height * 5 / 6));
		return true;
	}

	private JButton buildButton(String name, Color bc, Color fc) {
		JButton button = new JButton(name);
		button.setBackground(bc);
		button.setForeground(fc);
		return button;
	}

	public JPanel newNetGraphPanel(int w, int h) {
		if (gpanel == null)
			new ParseSites(w, h).execute();
		return gpanel;
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
		private int width, height;

		public ParseSites(int w, int h) {
			this.width = w;
			this.height = h;
		}

		@Override
		protected Boolean doInBackground() throws Exception {
			// TODO Auto-generated method stub
			new ModelsByXML().parseSitesByStreamReader(sitesM, linesM);
			return true;
		}

		//
		protected void done() {
			try {
				if (get()) {
					// gpanel = new GraphPanel("background.jpg",1100,700,sitesM,linesM);
					new ArcLoction(sitesM).loc(1750, 800);
					// new CircleLoction(sitesM).loc(1750, 800);
					gpanel = new GraphPanelver2("background.jpg", width, height, sitesM, linesM);
					dbe.addRefreshing(gpanel);
					contentPanel.add(gpanel);
					validate();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(MainFrame.this, e);
			}
		}
	}

}
