package xswingver2.model;

//LabelModel
public class LabelModel {
	//
	private String id;
	private String name;
	private String icon;
	private String icon_f;
	private int xpos;
	private int ypos;
	private int width;
	private int height;
	private boolean status;
	private boolean line1Status = false;
	private boolean line2Status = false;

	public LabelModel() {
	}

	public LabelModel(String id, String name, String ic, String ic_f, int width, int heigth, boolean status,
			boolean l1Status, boolean l2Status) {
		this.id = id;
		this.name = name;
		this.icon = ic;
		this.icon_f = ic_f;
		this.xpos = 0;
		this.ypos = 0;
		this.width = width;
		this.height = heigth;
		this.status = status;
		this.line1Status = l1Status;
		this.line2Status = l2Status;
		validateStatus();
	}

	public LabelModel(String id, String name, String ic, String ic_f, int xpos, int ypos, int width, int heigth,
			boolean status, boolean l1Status, boolean l2Status) {
		this.id = id;
		this.name = name;
		this.icon = ic;
		this.icon_f = ic_f;
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = heigth;
		this.status = status;
		this.line1Status = l1Status;
		this.line2Status = l2Status;
		validateStatus();
	}

	public void validateStatus() {
		if (!this.line1Status && !this.line2Status)
			this.status = false;
		else
			this.status = true;
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

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String ic) {
		this.icon = ic;
	}

	public String getIcon_f() {
		return this.icon_f;
	}

	public void setIcon_f(String ic) {
		this.icon_f = ic;
	}

	public int getXpos() {
		return this.xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYpos() {
		return this.ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean b) {
		this.status = b;
	}

	public boolean getLine1Status() {
		return this.line1Status;
	}

	public void setLine1Status(boolean b) {
		this.line1Status = b;
	}

	public boolean getLine2Status() {
		return this.line2Status;
	}

	public void setLine2Status(boolean b) {
		this.line2Status = b;
	}
}