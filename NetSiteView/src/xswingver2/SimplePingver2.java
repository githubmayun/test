package xswingver2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;

public class SimplePingver2 implements Callable<Boolean> {
	private NetSiteModel nsm = null;
	private static final String OSNAME = System.getProperty("os.name");

	public SimplePingver2(NetSiteModel nsm) {
		this.nsm = nsm;
	}

	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		boolean b = commandExecPing(nsm.getIpaddr(), "1000");
		nsm.setStatus(b);
		return b;
	}

	public boolean commandExecPing(String ipaddr, String timeout) {
		String cmdstr;
		Runtime r = Runtime.getRuntime();
		BufferedReader in = null;
		String linestr = null;
		if (OSNAME.contains("Windows"))
			cmdstr = "ping " + ipaddr + " -w " + timeout + " -n 1";
		else
			cmdstr = "ping " + " -w " + timeout + "-c 1" + " " + ipaddr;// linux
		try {
			Process p = r.exec(cmdstr);
			if (p == null) {
				System.out.println("null process");
				return false;
			}
			in = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));
			while ((linestr = in.readLine()) != null) {
				if (linestr.contains("TTL"))
					System.out.println(nsm.getIpaddr() + " true");
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace(); // 出现异常则返回假
			return false;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("finally")
	public boolean isReachablePing(String ipaddr, String timeout) {
		InetAddress address;
		boolean b = false;
		try {
			address = InetAddress.getByName(ipaddr);
			// Long start = System.currentTimeMillis();
			b = address.isReachable(Integer.parseInt(timeout));
			// Long end = System.currentTimeMillis() - start;
		} catch (Exception e) {
			b = false;
		} finally {
			if (b)
				System.out.println("--ping ----" + ipaddr);
			else
				System.out.println("--ping-not-reachable--" + ipaddr);
			return b;
		}
	}

	public static void main(String[] args) {
		if (new SimplePingver2(null).commandExecPing("127.0.0.1", "1000"))
			System.out.println("ok");
	}

	public void test() throws InterruptedException, IOException {
		String addr = "111.13.100.92";
		InetAddress address = InetAddress.getByName(addr);
		System.out.println("正在Ping " + addr + " [" + address.getHostAddress() + "] 具有32字节的数据");
		int flag = 0;
		for (int i = 0; i < 4; i++) {
			Long start = System.currentTimeMillis();
			boolean b = address.isReachable(1000);
			Long end = System.currentTimeMillis() - start;
			System.out.println("来自  " + address.getHostAddress() + " 的回复:  " + (b ? "成功" : "失败") + end.toString());
			if (b)
				flag++;
			Thread.sleep(1000);
		}
		System.out.println();
		System.out.println(address.getHostAddress() + " 的  Ping 统计信息：");
		System.out.println(
				"    数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) / 4 * 100 + "% 丢失)");
	}

}