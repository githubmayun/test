package xswingver2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetSiteModel {
	private String name;
	private String ipaddr;
	private boolean status;
	private boolean changed;

	public NetSiteModel() {

	}

	@SuppressWarnings("finally")
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
	}

	public String getName() {
		return this.name;
	}

	public String getIpaddr() {
		return this.ipaddr;
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

	public boolean getChanged() {
		return this.changed;
	}

	public void setChanged(boolean b) {
		this.changed = b;
	}

}
