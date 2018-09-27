package xswingver2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class LineComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LineModel lm;
	private float[] dash;

	public LineComponent(LineModel lm) {
		this.lm = lm;
	}

	private float getLineWidth() {
		return Float.parseFloat(lm.getLineWidth().trim());
	}

	private Color getColor() {
		String[] strs = lm.getColor().trim().split("_");
		// for(String str:strs) System.out.println(str);

		if (lm.getStatus())
			return new Color(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(strs[2]));
		else
			return new Color(255 - Integer.parseInt(strs[0]), Integer.parseInt(strs[1]),
					255 - Integer.parseInt(strs[2]));
	}

	private float[] getDash() {
		String[] strs = lm.getDash().trim().split("_");
		dash = new float[strs.length];
		for (int i = 0; i < strs.length; i++)
			dash[i] = Float.parseFloat(strs[i]);
		return dash;
	}

	public LineModel getLineModel() {
		return this.lm;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		// public BasicStroke(float width, int cap, int join, float miterlimit, float[]
		// dash, float dash_phase)
		if (lm.getStatus())
			g2.setStroke(new BasicStroke(getLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f));
		else
			g2.setStroke(
					new BasicStroke(getLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, getDash(), 0f));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawLine(lm.getX1pos() - lm.getCornerX(), lm.getY1pos() - lm.getCornerY(), lm.getX2pos() - lm.getCornerX(),
				lm.getY2pos() - lm.getCornerY());
	}

	public void updateStatus(boolean bb) {
		if (this.lm.getStatus() != bb) {
			this.lm.setStatus(bb);
			repaint();
		}
	}

	public Dimension getPreferredSize() {
		//
		return new Dimension(lm.getWidth(), lm.getHeight());

	}
}
