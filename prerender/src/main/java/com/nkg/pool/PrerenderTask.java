package com.nkg.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;

public class PrerenderTask implements Runnable {

	private GenericObjectPool<Prerender> pool;
	private int threadNo;

	public PrerenderTask(GenericObjectPool<Prerender> pool, int threadNo) {
		super();
		this.pool = pool;
		this.threadNo = threadNo;
	}

	@Override
	public void run() {
		Prerender prerender = null;

		try {
			prerender = pool.borrowObject();

			int workTime = (int)(Math.random()*30)+1+5; // 5 ~ 35
			System.out.println("[" + threadNo + "] - " + prerender.getProcessNo() + " was borrowed: " + workTime);

			//TODO
			Thread.sleep(workTime * 1000);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// return ExportingProcess instance back to the pool
			if (prerender != null) {
				pool.returnObject(prerender);
				System.out.println("Thread " + threadNo +": Object with process no. " + prerender.getProcessNo() + " was returned");
			}
		}
	}

}
