package xswingver2;

import java.util.Map;

public class ArcLoction implements AutoLocation {


	private Map<String, LabelModel> lm = null;

	public ArcLoction(Map<String, LabelModel> lm) {
		this.lm = lm;
	}
	@Override
	public void loc(int w, int h) {
		// TODO Auto-generated method stub
		if (lm.size() == 0 || lm.get("center") == null)
			return;

		//
		int cenLaX = 0, cenLaY = 0;
		int nlabel = lm.size();
		cenLaX = w / 2;
		cenLaY = h - lm.get("center").getHeight();
		// 
		if (nlabel <= 2) {
			for (LabelModel lm : lm.values()) {
				if (lm.getId().trim().equals("center")) {
					// 
					lm.setXpos(cenLaX);
					lm.setYpos(h - lm.getHeight());
				} else {
					lm.setXpos(cenLaX);
					lm.setYpos(lm.getHeight());
				}
			}
		} else {
			double delta = Math.PI / (nlabel - 2);
			double angle = 0;
			int x, y, xpos, ypos;
			for (LabelModel lm : lm.values()) {
				if (lm.getId().equals("center")) {
					lm.setXpos(cenLaX);
					lm.setYpos(h - lm.getHeight());
				} else {
					int radis = Math.min(w / 2 - lm.getWidth(), h - lm.getHeight() * 2);
					x = (int) (Math.cos(angle) * radis);
					y = (int) (Math.sin(angle) * radis);
					// System.out.println("x,y relativa:" + x + "--" + y);
					// rectangle center position
					xpos = cenLaX + x;
					ypos = cenLaY - y;
					lm.setXpos(xpos);
					lm.setYpos(ypos);
					angle += delta;
				}
			}
		}
		// test
		/*for (LabelModel lm : lm.values()) {
			System.out.println(lm.getId() + "--location---" + lm.getXpos() + "-" + lm.getYpos());
		}*/

	}

}
