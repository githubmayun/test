package serverend;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComponent;

import model.NetSiteModel;
import model.TopInfoModel;
import model.WarnningMessageModel;
import xswingver2.ModelsByXML;
import xswingver2.Refreshing;

public class DataBackEndver2 implements Runnable {
	private List<Refreshing> listeners = new ArrayList<>();
	//
	private List<NetSiteModel> results = new ArrayList<>();
	private TopInfoModel infoModel = new TopInfoModel();
	private WarnningMessageModel messageModel=new WarnningMessageModel();
	private BitSet bscur = null;
	//
	private List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
	private ExecutorService pool = Executors.newCachedThreadPool();// pool.shutdown?
	private ExecutorCompletionService<Boolean> service = new ExecutorCompletionService<>(pool);

	public DataBackEndver2() {
		new ModelsByXML().parseNetSitesByStreamReader(results);
		bscur = new BitSet(results.size());
		bscur.clear();
		init();
	}

	public void init() {
		for (NetSiteModel nsm : results) {
			Callable<Boolean> t = new SimplePingver2(nsm);
			tasks.add(t);
		}
	}

	public void shutdown() {
		pool.shutdown();
	}

	public void cancleJob() {
		// ????????????????????
	}

	public void addRefreshing(Refreshing rf) {
		listeners.add(rf);
	}

	public void delRefreshing(Refreshing rf) {
		listeners.remove(rf);
	}

	public void notifyAllListeners(List<NetSiteModel> results, TopInfoModel infoModel, BitSet bsstate) {
		for (Refreshing rf : listeners) {
			if (rf instanceof JComponent) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						rf.refresh(results, bsstate);
					}
				});
			} else
				rf.refresh(results, bsstate);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		runRefreshAll();
	}

	public void runRefreshTopInfo() {
		infoModel.setMissionInfo("xx-xxx全系统调试");
		infoModel.setStartingTime("16:59:123");
		infoModel.setCurrentTime(new SimpleDateFormat("HH:MM:SS").format(new Date()));
		infoModel.setLastingTime("");
		infoModel.setProcessInfo("第一次全系统测试");
	}

	public void runRefreshMessage() {
		messageModel.setCurrentTime(new SimpleDateFormat("HH:MM:SS").format(new Date()));
		messageModel.setMissionInfo("hahaha");
		messageModel.setProcessInfo("111--111");
	}

	public void runRereshPing() {
		for (Callable<Boolean> task : tasks) {
			service.submit(task);
		}
		for (int i = 0; i < tasks.size(); i++) {
			try {
				service.take().get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		} //
		int index = 0;
		bscur.clear();
		for (NetSiteModel nsm : results) {
			if (nsm.getStatus()) {
				bscur.set(index);
			}
			index++;
		}
	}

	public void runRefreshAll() {
		runRefreshTopInfo();
		runRereshPing();
		runRefreshMessage();
		notifyAllListeners(results, infoModel, bscur);
	}
}
