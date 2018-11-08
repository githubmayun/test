package xswingver2.model;

import xswingver2.GlobalConstants;

//LineModel
public class LineModel {
	// xpos,ypos,cenxpos,cenypos >(GlobalConstants.MINRECTANGLE)/2
	private String id;
	private String name;
	private String lineWidth;
	private String dash;
	private String color;
	private int x1pos;
	private int y1pos;
	private int x2pos;
	private int y2pos;
	// private int cornerX;
	// private int cornerY;
	private boolean status;
	private String type;

	public LineModel() {
	}

	public LineModel(String id, String name, String width, String dash, String color, int x1pos, int y1pos, int x2pos,
			int y2pos, boolean status) {
		this.id = id;
		this.name=name;
		this.lineWidth = width;
		this.dash = dash;
		this.color = color;
		this.x1pos = x1pos;
		this.y1pos = y1pos;
		this.x2pos = x2pos;
		this.y2pos = y2pos;
		this.status = status;
	}

	// 
	// xpos cenXpos > (GlobalConstants.MINRECTANGLE)/2
	public int getCornerX() {
		int cornerX;
		if (Math.abs(x1pos - x2pos) < GlobalConstants.MINRECTANGLE) {
			cornerX = Math.max(x1pos, x2pos) - GlobalConstants.MINRECTANGLE / 2;
		} else {
			cornerX = x1pos > x2pos ? x2pos : x1pos;
		}

		return cornerX;
	}

	//
	public int getCornerY() {
		int cornerY;
		if (Math.abs(y1pos - y2pos) < GlobalConstants.MINRECTANGLE) {
			// ypos,cenYpos > GloabalConstants.MINRECTANGLE / 2
			cornerY = Math.max(y1pos, y2pos) - GlobalConstants.MINRECTANGLE / 2;
		} else {
			cornerY = y1pos > y2pos ? y2pos : y1pos;
		}
		return cornerY;
	}

	public int getWidth() {
		return Math.max(Math.abs(x1pos - x2pos), GlobalConstants.MINRECTANGLE);
	}

	public int getHeight() {
		return Math.max(Math.abs(y1pos - y2pos), GlobalConstants.MINRECTANGLE);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLineWidth() {
		return this.lineWidth;
	}

	public void setLineWidth(String lt) {
		this.lineWidth = lt;
	}

	public String getDash() {
		return this.dash;
	}

	public void setDash(String lt) {
		this.dash = lt;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String lt) {
		this.color = lt;
	}

	public int getX1pos() {
		return this.x1pos;
	}

	public void setX1pos(int x1pos) {
		this.x1pos = x1pos;
	}

	public int getY1pos() {
		return this.y1pos;
	}

	public void setY1pos(int y1pos) {
		this.y1pos = y1pos;
	}

	public int getX2pos() {
		return this.x2pos;
	}

	public void setX2pos(int x2pos) {
		this.x2pos = x2pos;
	}

	public int getY2pos() {
		return this.y2pos;
	}

	public void setY2pos(int y2pos) {
		this.y2pos = y2pos;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean b) {
		this.status = b;
	}
	public String getType() {
		return this.type;
	}

	public void setType(String ty) {
		this.type = ty;
	}
}