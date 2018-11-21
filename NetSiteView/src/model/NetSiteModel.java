package model;

import java.io.Serializable;

public class NetSiteModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String ipaddr;
	private boolean status;
	private int delay;
	private int total;
	private int current;

	public NetSiteModel() {
		id = null;
		name = null;
		ipaddr = null;
		status = false;
		delay = 0;
		total = 0;
		current = 0;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public boolean getStatus() {
		return this.status;
	}

	public int getDelay() {
		return this.delay;
	}

	public int getTotal() {
		return this.total;
	}

	public int getCurrent() {
		return this.current;
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

	public void setStatus(boolean b) {
		this.status = b;
	}

	public void setDelay(int dt) {
		this.delay = dt;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void increaseTotal() {
		this.total++;
	}

	public void setCurrent(int dt) {
		this.current = dt;
	}

	public void increaseCurrent() {
		this.current++;
	}
}
