package xswingver2;

import java.io.Serializable;
import java.net.InetAddress;
public class NetSiteModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String ipaddr;
	private boolean status;
	private int delay;

	public NetSiteModel() {
		id=null;
		name = null;
		ipaddr = null;
		status = false;
		delay = 0;
	}
  /*  @SuppressWarnings("finally")
	public boolean ping() {
		InetAddress address;
		boolean b = false;
		try {
			address = InetAddress.getByName(ipaddr);
			Long start = System.currentTimeMillis();
			b = address.isReachable(1000);
			Long end = System.currentTimeMillis() - start;
		} catch (Exception e) {
			b = false;
		} finally {
			if (b)
				System.out.println(this.name + "--ping ----" + this.ipaddr);
			else
				System.out.println(this.name + "--ping-not-reachable--" + this.ipaddr);
			this.setStatus(b);
			return b;
		}
	}*/
	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIpaddr(String ip) {
		this.ipaddr = ip;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean b) {
		this.status = b;
	}

	public int getDelay() {
		return this.delay;
	}

	public void setDelay(int dt) {
		this.delay = dt;
	}

}
