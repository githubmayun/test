package xswingver2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GraphPanelver2 extends JPanel implements Refreshing {
	private static final long serialVersionUID = 1L;
	private String background = null;
	private int width, height;
	private Map<String, JLabel> allSites = new HashMap<String, JLabel>();
	private Map<String, LineComponent> allLines = new HashMap<String, LineComponent>();
	private ImageIcon icon = null;
	private Map<String, LabelModel> sitesM = null;
	private Map<String, LineModel> linesM = null;
	private BitSet bitcur, bitpre;
	private boolean isFirst;

	public GraphPanelver2() {
		this.init();
	}

	public GraphPanelver2(String background) {
		this.background = background;
		icon = new ImageIcon(this.background);
		this.init();
	}

	public GraphPanelver2(String background, int x, int y) {
		this.background = background;
		icon = new ImageIcon(this.background);
		this.width = x;
		this.height = y;
		this.init();
	}

	public GraphPanelver2(String background, int x, int y, Map<String, LabelModel> sites,
			Map<String, LineModel> lines) {
		this.background = background;
		icon = new ImageIcon(this.background);
		this.width = x;
		this.height = y;
		this.sitesM = sites;
		this.linesM = lines;
		this.init();
	}

	public boolean init() {
		setSize(width, height);
		setBorder(BorderFactory.createLineBorder(Color.CYAN));
		setLayout(null);
		bitcur = new BitSet(linesM.size());
		bitpre = new BitSet(linesM.size());
		isFirst = true;
		buildSites();
		buildLines();
		return true;
	}

	public boolean buildSites() {
		if (sitesM.isEmpty())
			return false;
		JLabel label;
		MyMouseHandler mhand = new MyMouseHandler();
		for (LabelModel lm : sitesM.values()) {
			label = new LabelComponent(lm);
			label.setVerticalAlignment(SwingConstants.BOTTOM);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalTextPosition(SwingConstants.BOTTOM);
			label.setHorizontalTextPosition(SwingConstants.CENTER);
			label.addMouseListener(mhand);
			label.addMouseMotionListener(mhand);
			label.setBounds(lm.getXpos() - lm.getWidth() / 2, lm.getYpos() - lm.getHeight() / 2, lm.getWidth(),
					lm.getHeight());
			label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			allSites.put(lm.getId(), label);
			this.add(label);
		}
		return true;
	}

	public boolean buildLines() {
		// if (linesM.isEmpty())
		buildLineModels();
		LineComponent linecomponent;
		for (LineModel lm : linesM.values()) {
			linecomponent = new LineComponent(lm);
			linecomponent.setName(lm.getId());
			allLines.put(lm.getId(), linecomponent);
			this.add(linecomponent);
			linecomponent.setBounds(lm.getCornerX(), lm.getCornerY(), lm.getWidth(), lm.getHeight());
		}
		return true;
	}

	private boolean buildLineModels() {
		if (sitesM.isEmpty())
			return false;
		LabelModel cenLabel = sitesM.get("center");
		LabelModel theLabel = null;
		LineModel theLine = null;
		for (Map.Entry<String, LabelModel> entry : sitesM.entrySet()) {
			if (!entry.getKey().equals("center")) {
				theLabel = entry.getValue();
				theLine = linesM.get(entry.getKey());
				theLine.setX1pos(cenLabel.getXpos());
				theLine.setY1pos(cenLabel.getYpos());
				theLine.setX2pos(theLabel.getXpos());
				theLine.setY2pos(theLabel.getYpos());
				// linesM.put(theLabel.getId(), lm);
			}
		}
		return true;
	}

	//
	private boolean updateGraphStatus(List<NetSiteModel> results, BitSet bschanged) {
		LabelComponent theSite;
		LineComponent theLine;
		for (int i = 0; i < bschanged.length(); i++) {
			if (bschanged.get(i)) {
				theSite = (LabelComponent) allSites.get(results.get(i).getId());
				theLine = allLines.get(results.get(i).getId());
				theSite.reverseStatus();
				theLine.reverseStatus();
			}
		}
		return true;
	}

	//
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//
		g.drawImage(icon.getImage(), 0, 0, this.getSize().width, this.getSize().height, null);
	}

	private class SizeBehaviour extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
		}
	}

	//
	private class MyMouseHandler extends MouseAdapter {
		int newX, newY, oldX, oldY;
		//
		int startX, startY, startX_L, startY_L;
		LabelModel theSite;
		LineModel theLine;
		boolean isCenter = false;

		public MyMouseHandler() {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			Component cp = (Component) e.getSource();
			//
			newX = e.getXOnScreen();
			newY = e.getYOnScreen();
			// update site model
			theSite.setXpos(Math.max(Math.min((startX + newX - oldX), width), theSite.getWidth() / 2));
			theSite.setYpos(Math.max(Math.min((startY + newY - oldY), height), theSite.getWidth() / 2));
			//
			// cp.setBounds(startX + (newX - oldX), startY + (newY - oldY), cp.getWidth(),
			// cp.getHeight());
			cp.setBounds(theSite.getXpos() - theSite.getWidth() / 2, theSite.getYpos() - theSite.getHeight() / 2,
					theSite.getWidth(), theSite.getHeight());
			// update line model this.comp.invalidate();//update all?
			if (isCenter) {
				for (LineModel entry : linesM.values()) {
					entry.setX1pos(startX_L + newX - oldX);
					entry.setY1pos(startY_L + newY - oldY);
				}
				for (LineComponent comp : allLines.values()) {
					comp.setBounds(comp.getLineModel().getCornerX(), comp.getLineModel().getCornerY(),
							comp.getLineModel().getWidth(), comp.getLineModel().getHeight());

				}
				// this.comp.repaint();
			} else {
				theLine.setX2pos(startX_L + newX - oldX);
				theLine.setY2pos(startY_L + newY - oldY);
				allLines.get(theLine.getId()).setBounds(theLine.getCornerX(), theLine.getCornerY(), theLine.getWidth(),
						theLine.getHeight());
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Component cp = (JLabel) e.getSource();
			theSite = sitesM.get(cp.getName());
			if (cp.getName() != null && cp.getName().equals("center")) {
				isCenter = true;
				startX_L = theSite.getXpos();
				startY_L = theSite.getYpos();
			} else {
				isCenter = false;
				theLine = linesM.get(theSite.getId());
				startX_L = theLine.getX2pos();
				startY_L = theLine.getY2pos();
			}
			//
			startX = theSite.getXpos();
			startY = theSite.getYpos();
			oldX = e.getXOnScreen();
			oldY = e.getYOnScreen();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Component cp = (JLabel) e.getSource();
			// System.out.println("the cp name int mousePressed: " + cp.getName());
			theSite = sitesM.get(cp.getName());
			if (cp.getName() != null && cp.getName().equals("center")) {
				isCenter = true;
				startX_L = theSite.getXpos();
				startY_L = theSite.getYpos();
			} else {
				isCenter = false;
				theLine = linesM.get(theSite.getId());
				startX_L = theLine.getX2pos();
				startY_L = theLine.getY2pos();
			}
			//
			startX = theSite.getXpos();
			startY = theSite.getYpos();
			oldX = e.getXOnScreen();
			oldY = e.getYOnScreen();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

	}

	@Override
	public void refresh(List<NetSiteModel> results, BitSet bss) {
		// TODO Auto-generated method stub
		if (isFirst) {
			bitpre.clear();
			bitcur.clear();
			bitpre.or(bss);
			isFirst = false;
		} else {
			bitcur.clear();
			bitcur.or(bss);
		}
		bitcur.xor(bitpre);
		// bitpre始终保存上一次状态
		bitpre.clear();
		bitpre.or(bss);
		// BitSet不是线程安全的；bitpre bitcur必须在下一次接收refresh前更新完毕！
		//因为是迭代更新的，所以bitpre和bitcur的更新必须是同步的！
		if (!bitcur.isEmpty())
			updateGraphStatus(results, bitcur);

	}

}
