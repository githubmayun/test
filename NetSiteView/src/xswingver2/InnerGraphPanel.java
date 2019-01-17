package xswingver2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import clientend.ReceiveDatasByMulticast;
import model.LabelModel;
import model.LineModel;
import model.NetSiteModel;

public class InnerGraphPanel extends JPanel implements Refreshing {
	private static final long serialVersionUID = 1L;
	private int width, height;// panel width height
	private Map<String, LabelComponent> allSites = new HashMap<String, LabelComponent>();
	private Map<String, LineComponent> allLines = new HashMap<String, LineComponent>();
	private Map<String, LabelModel> sitesM;
	private Map<String, LineModel> linesM;
	//
	private BitSet bitcur, bitpre;
	private boolean isFirst;
	private ImageIcon bgImage;

	public InnerGraphPanel() {
		this.init();
	}

	public InnerGraphPanel(int x, int y, Map<String, LabelModel> sitesM, Map<String, LineModel> linesM) {
		this.width = x;
		this.height = y;
		this.sitesM = sitesM;
		this.linesM = linesM;
		bgImage=new ImageIcon("background.jpg");
		this.init();
	}

	public boolean init() {
		this.setPreferredSize(new Dimension(width, height));
		// setBorder(BorderFactory.createLineBorder(Color.CYAN));
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
		LabelComponent label;
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
			if ("three".equalsIgnoreCase(lm.getType())) {
				linecomponent.setVisible(false);
			}
		}
		return true;
	}

	private boolean buildLineModels() {
		if (sitesM.isEmpty())
			return false;
		LabelModel cenLabel = sitesM.get("center");
		LabelModel theLabel = null;
		int x1, y1, x2, y2;
		x1 = cenLabel.getXpos();
		y1 = cenLabel.getYpos();
		String tid = null;
		for (Map.Entry<String, LabelModel> entry : sitesM.entrySet()) {
			if (!entry.getKey().equals("center")) {
				theLabel = entry.getValue();
				tid = entry.getKey();
				x2 = theLabel.getXpos();
				y2 = theLabel.getYpos();
				newLineXY(tid, x1, y1, x2, y2);
				// linesM.put(theLabel.getId(), lm);
			}
		}
		return true;
	}

	private void newLineXY(String id, int x1, int y1, int x2, int y2) {
		LineModel theLine = null;
		boolean b = Math.abs(y1 - y2) > Math.abs(x1 - x2);
		//
		theLine = linesM.get(id + "_1");
		if (theLine != null) {
			theLine.setX1pos(x1);
			theLine.setY1pos(y1);
			if (b) {
				theLine.setX2pos(x2 - GlobalConstants.FSXDISTANCE);
				theLine.setY2pos(y2);
			} else {
				theLine.setX2pos(x2);
				theLine.setY2pos(y2 - GlobalConstants.FSYDISTANCE);
			}
		}
		//
		theLine = linesM.get(id + "_2");
		if (theLine != null) {
			theLine.setX1pos(x1);
			theLine.setY1pos(y1);
			if (b) {
				theLine.setX2pos(x2 + GlobalConstants.FSXDISTANCE);
				theLine.setY2pos(y2);
			} else {
				theLine.setX2pos(x2);
				theLine.setY2pos(y2 + GlobalConstants.FSYDISTANCE);
			}
		}
	}

	// 更新所有的
	private boolean updateGraphStatus(boolean status) {
		for (LabelModel lm : sitesM.values()) {
			lm.setStatus(status);
		}
		for (LineModel lm : linesM.values()) {
			lm.setStatus(status);
		}
		repaint();
		return true;
	}

	private boolean updateGraphStatusByResults(List<NetSiteModel> results) {
		LineModel theLineM = null;
		LabelModel theLabelM = null;
		String lineId = null;
		String siteId, lineNum;
		boolean b;
		for (int i = 0; i < results.size(); i++) {
			lineId = results.get(i).getId();
			theLineM = linesM.get(lineId);
			b = results.get(i).getStatus();
			theLineM.setStatus(b);
			//
			siteId = lineId.split("_")[0];
			lineNum = lineId.split("_")[1];
			theLabelM = sitesM.get(siteId);
			if (lineNum.equals("1")) {
				theLabelM.setLine1Status(b);
			} else if (lineNum.equals("2")) {
				theLabelM.setLine2Status(b);
			}
		}
		for (LabelComponent lcom : allSites.values()) {
			lcom.validateStatus();
		}
		this.repaint();
		return true;
	}

	//
	public void paintComponent(Graphics g) {
		//System.out.println("building graph....");		
		super.paintComponent(g);
		g.drawImage(bgImage.getImage(), 0, 0, this.getSize().width, this.getSize().height, null);
	}

	private class SizeBehaviour extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
		}
	}

	//
	private class MyMouseHandler extends MouseAdapter {
		int newX, newY, oldX, oldY;
		//
		int startX, startY;
		LabelModel theSite = null, theSiteCenter = null;
		LineModel theLine_1 = null, theLine_2 = null;
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
			int newSiteX = startX + newX - oldX;
			int newSiteY = startY + newY - oldY;
			int cenSiteX = theSiteCenter.getXpos();
			int cenSiteY = theSiteCenter.getYpos();
			// update site model
			theSite.setXpos(Math.max(Math.min(newSiteX, width - theSite.getWidth() / 2), theSite.getWidth() / 2));
			theSite.setYpos(Math.max(Math.min(newSiteY, height - theSite.getHeight() / 2), theSite.getHeight() / 2));
			newSiteX = theSite.getXpos();
			newSiteY = theSite.getYpos();
			boolean b = Math.abs(newSiteY - cenSiteY) > Math.abs(newSiteX - cenSiteX);
			cp.setBounds(theSite.getXpos() - theSite.getWidth() / 2, theSite.getYpos() - theSite.getHeight() / 2,
					theSite.getWidth(), theSite.getHeight());
			// update line model this.comp.invalidate();//update all?
			if (isCenter) {
				for (LineModel entry : linesM.values()) {
					entry.setX1pos(newSiteX);
					entry.setY1pos(newSiteY);
				}
				for (LineComponent comp : allLines.values()) {
					comp.setBounds(comp.getLineModel().getCornerX(), comp.getLineModel().getCornerY(),
							comp.getLineModel().getWidth(), comp.getLineModel().getHeight());
				}
				// this.comp.repaint();
			} else {
				if (theLine_1 != null) {
					if (b) {
						theLine_1.setX2pos(newSiteX - GlobalConstants.FSXDISTANCE);
						theLine_1.setY2pos(newSiteY);
					} else {
						theLine_1.setX2pos(newSiteX);
						theLine_1.setY2pos(newSiteY - GlobalConstants.FSYDISTANCE);
					}

					allLines.get(theLine_1.getId()).setBounds(theLine_1.getCornerX(), theLine_1.getCornerY(),
							theLine_1.getWidth(), theLine_1.getHeight());
				}
				if (theLine_2 != null) {
					if (b) {
						theLine_2.setX2pos(newSiteX + GlobalConstants.FSXDISTANCE);
						theLine_2.setY2pos(newSiteY);
					} else {
						theLine_2.setX2pos(newSiteX);
						theLine_2.setY2pos(newSiteY + GlobalConstants.FSYDISTANCE);
					}
					allLines.get(theLine_2.getId()).setBounds(theLine_2.getCornerX(), theLine_2.getCornerY(),
							theLine_2.getWidth(), theLine_2.getHeight());
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Component cp = (JLabel) e.getSource();
			theSite = sitesM.get(cp.getName());
			theSiteCenter = sitesM.get("center");
			if (cp.getName() != null && cp.getName().equals("center")) {
				isCenter = true;
			} else {
				isCenter = false;
				theSiteCenter = sitesM.get("center");
				theLine_1 = linesM.get(theSite.getId() + "_1");
				theLine_2 = linesM.get(theSite.getId() + "_2");
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

		if (results == null) {
			updateGraphStatus(false);
			bitpre.clear();
		} else {
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
			// BitSet不是线程安全的；bitpre bitcur必须在下一次接收 bss refresh前更新完毕！
			// 因为是迭代更新的，所以bitpre和bitcur的更新必须是同步的！
			if (!bitcur.isEmpty()&&InnerGraphPanel.this.isVisible())
				updateGraphStatusByResults(results);
		}
	}

}
