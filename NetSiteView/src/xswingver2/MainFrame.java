package xswingver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clientend.ReceiveDatasByMulticast;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width, height, screenWidth, screenHeight;
	JPanel topInfoPanel, bottomInfoPanel, buttonPanel, contentPanel;
	private Color backgroundColor = ColorsUI.backgroundColor;

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
		bottomInfoPanel.setBorder(BorderFactory.createLineBorder(ColorsUI.panelBorderColor));
		bottomInfoPanel.setBackground(this.backgroundColor);

		// left side buttonpanel
		List<String> names = new ArrayList<>();
		names.add("netgraph");
		names.add("nettable");
		names.add("sniffers");
		List<String> xnames = new ArrayList<>();
		xnames.add("网络态势图");
		xnames.add("网络表格图");
		xnames.add("网络曲线图");
		buttonPanel = new MyButtonPanel(width / 8, height * 5 / 6, 11, this.backgroundColor, ColorsUI.btnOnActionColor,
				ColorsUI.btnOnDaemonColor, ColorsUI.btnOnRolloverColor, MainFrame.this);
		((MyButtonPanel) buttonPanel).addButtons(names, xnames);
		//

		// center contentpanel
		contentPanel = new MyContentPanel(width * 7 / 8, height * 5 / 6, dbe);

		this.add(topInfoPanel, BorderLayout.NORTH);
		this.add(bottomInfoPanel, BorderLayout.SOUTH);
		this.add(buttonPanel, BorderLayout.WEST);
		this.add(contentPanel, BorderLayout.CENTER);
		
		execButtonCommand(names.get(0));
		return true;
	}

	public void execButtonCommand(String command) {
		if (command == null)
			return;
		((MyContentPanel) contentPanel).execCommand(command);
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
	//

}
