package xswingver2;


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
       public LabelModel() {
    	   
       }
		public LabelModel(String id,String name, String ic,String ic_f,int width,int heigth, boolean status) {
			this.id=id;
			this.name = name;
			this.icon=ic;
			this.icon_f=ic_f;
			this.xpos=0;
			this.ypos=0;
			this.width=width;
			this.height=heigth;
			this.status = status;
		}

		public LabelModel(String id,String name, String ic,String ic_f,int xpos, int ypos,int width,int heigth, boolean status) {
			this.id=id;
			this.name = name;
			this.icon=ic;
			this.icon_f=ic_f;
			this.xpos = xpos;
			this.ypos = ypos;
			this.width=width;
			this.height=heigth;
			this.status = status;
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
	}