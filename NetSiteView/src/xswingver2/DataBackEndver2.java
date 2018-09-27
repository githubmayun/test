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

public class DataBackEndver2 implements Runnable {
	private List<Refreshing> listeners = new ArrayList<>();
	private List<NetSiteModel> results = new ArrayList<>();
	private BitSet bscur = null, bspre = null, bstemp;
	private int bssize;
	private List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
	private static ExecutorService pool = Executors.newCachedThreadPool();
	private static ExecutorCompletionService<Boolean> service = new ExecutorCompletionService<>(pool);

	public DataBackEndver2() {
		new ModelsByXML().parseNetSitesByStreamReader(results);
		bssize = results.size();
		init();
	}

	public void init() {
		bscur = new BitSet(bssize);
		bspre = new BitSet(bssize);
		bscur.clear();
		bspre.clear();
		int i = 0;
		for (NetSiteModel nsm : results) {
			Callable<Boolean> t = new SimplePingver2(nsm);
			tasks.add(t);
			if (nsm.getStatus())
				bspre.set(i);
			i++;
		}
	}

	public void runReresh() {
		// TODO Auto-generated method stub
		bstemp = bspre;
		bspre = bscur;
		bscur = bstemp;	
		bscur.clear();
		for (Callable<Boolean> task : tasks) {
			service.submit(task);
		}
		for (int i = 0; i < tasks.size(); i++) {
			try {
				service.take().get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//
		if (hasChanged())
			notifyAllListeners(bspre);
	}

	private boolean hasChanged() {
		int i = 0;
		boolean b = false;
		for (NetSiteModel nsm : results) {
			if (nsm.getStatus())
				bscur.set(i);
			i++;
		}
		bspre.xor(bscur);
		for (int j = 0; j < bssize; j++) {
			if (bspre.get(i)) {
				b = true;
				results.get(i).setChanged(true);
				break;
			}
		}
	
		return b;
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

	public void notifyAllListeners(BitSet bschanged) {

		for (Refreshing rf : listeners) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					rf.refresh(bschanged);
				}
			});
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		runReresh();
	}

}
