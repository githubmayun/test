package xswingver2;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LabelComponent extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LabelModel lm;
	private Icon tIcon, fIcon;	
	public LabelComponent(LabelModel lm) {
		this.lm=lm;
		super.setName(lm.getId());
		super.setText(lm.getName());
		this.tIcon = new ImageIcon(lm.getIcon());
		this.fIcon = new ImageIcon(lm.getIcon_f());
		setTFIcon();
	}
	public void setTrueIcon(String ti) {
		this.tIcon = new ImageIcon(ti);
		setTFIcon();
	}
	public void setFalseIcon(String ti) {
		this.fIcon = new ImageIcon(ti);
		setTFIcon();
	}
	private void setTFIcon() {
		if (lm.getStatus())
			super.setIcon(tIcon);
		else
			super.setIcon(fIcon);
	}
	public void updateStatus(boolean b) {
       if(lm.getStatus() != b) {
    	   lm.setStatus(b);
     	   setTFIcon();
       }
	}

}
