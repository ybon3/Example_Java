package com.nkg.pool;

import java.util.concurrent.atomic.AtomicLong;

public class Prerender {
	static private AtomicLong processNoIndex = new AtomicLong(0);
	private long processNo;

	public Prerender() {
		super();
		this.processNo = processNoIndex.incrementAndGet();
		System.out.println("Object with process no. " + processNo + " was created");
	}

	public long getProcessNo() {
		return processNo;
	}

	public void setProcessNo(long processNo) {
		this.processNo = processNo;
	}
}