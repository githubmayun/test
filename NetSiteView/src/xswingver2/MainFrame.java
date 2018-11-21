package xswingver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import model.LabelModel;
import model.LineModel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width, height, screenWidth, screenHeight;
	JPanel topInfoPanel, bottomInfoPanel, buttonPanel, contentPanel;

	private Color backgroundColor=new Color(102,148,52);

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
		this.setLayout(new BorderLayout());
		
		topInfoPanel = new MyTopInfoPanel(width, height / 8, this.backgroundColor);

		// bottom infomation panel
		bottomInfoPanel = new JPanel();
		bottomInfoPanel.setPreferredSize(new Dimension(width, height / 24));
		bottomInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		bottomInfoPanel.setBackground(this.backgroundColor);

		// left side buttonpanel
		List<String> names = new ArrayList<>();
		names.add("netgrahp");
		names.add("nettable");
		names.add("sniffers");
		List<String> xnames = new ArrayList<>();
		xnames.add("网络态势图");
		xnames.add("网络表格图");
		xnames.add("网络曲线图");
		buttonPanel = new MyButtonPanel(width / 8, height * 5 / 6, 12, this.backgroundColor, new Color(135, 191, 72),
				new Color(108, 157, 55), new Color(164, 207, 116));
		((MyButtonPanel) buttonPanel).addButtons(MainFrame.this, names, xnames);

		// center contentpanel
		contentPanel = new JPanel();
		// contentPanel.setPreferredSize(new Dimension(width*5/6,height*2/3));
		contentPanel.setLayout(null);
		//
		this.add(topInfoPanel, BorderLayout.NORTH);
		this.add(bottomInfoPanel, BorderLayout.SOUTH);
		this.add(buttonPanel, BorderLayout.WEST);
		this.add(contentPanel, BorderLayout.CENTER);

		return true;
	}

	public void execButtonCommand(String command) {
		if (command.equals("netgrahp"))
			newNetGraphPanel(width * 7 / 8, height * 5 / 6);
		else if (command.equals("nettable")) {
			System.out.println("table..");
		}
	}

	public void newNetGraphPanel(int w, int h) {
		

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



}
