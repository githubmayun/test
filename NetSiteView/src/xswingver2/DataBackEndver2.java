package xswingver2;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComponent;


public class DataBackEndver2 implements Runnable {
	private List<Refreshing> listeners = new ArrayList<>();
	private List<NetSiteModel> results = new ArrayList<>();
	private BitSet bscur = null;
	private int bssize;
	private List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
	private ExecutorService pool = Executors.newCachedThreadPool();// pool.shutdown?
	private ExecutorCompletionService<Boolean> service = new ExecutorCompletionService<>(pool);

	public DataBackEndver2() {
		new ModelsByXML().parseNetSitesByStreamReader(results);
		bssize = results.size();
		init();
	}

	public void init() {
		bscur = new BitSet(bssize);
		bscur.clear();
		for (NetSiteModel nsm : results) {			
			Callable<Boolean> t = new SimplePingver2(nsm);
			tasks.add(t);
		}
	}

	public void shutdown() {
		pool.shutdown();
	}

	public void runReresh() {
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
		notifyAllListeners(results, bscur);
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

	public void notifyAllListeners(List<NetSiteModel> results, BitSet bsstate) {
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
		runReresh();
	}
}
