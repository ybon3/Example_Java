package com.nkg.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class TestBaseObjectPool {

	public static void main(String[] args) {
		GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
		conf.setMaxTotal(16);
		conf.setMaxIdle(8);
		GenericObjectPool<Prerender> pool = new GenericObjectPool<>(new PrerenderObjectFactory(), conf);

		//Test
		int demoThreadTotal = 20;
		ExecutorService executor = Executors.newFixedThreadPool(demoThreadTotal);

		for (int i = 0; i < demoThreadTotal; i++) {
			executor.execute(new PrerenderTask(pool, i));
		}

		executor.shutdown();
	}

}

class PrerenderObjectFactory implements PooledObjectFactory<Prerender>{

	@Override
	public void activateObject(PooledObject<Prerender> arg0) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroyObject(PooledObject<Prerender> obj) throws Exception {
		System.out.println(obj.getObject().getProcessNo() + " destroyed ===============");
	}

	@Override
	public PooledObject<Prerender> makeObject() throws Exception {
		return new DefaultPooledObject<>(new Prerender());
	}

	@Override
	public void passivateObject(PooledObject<Prerender> arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validateObject(PooledObject<Prerender> arg0) {
		System.out.println("validateObject.........");
		return false;
	}

}

class PrerenderObjectFactory2 extends BasePooledObjectFactory<Prerender>{

	@Override
	public Prerender create() throws Exception {
		return new Prerender();
	}

	@Override
	public PooledObject<Prerender> wrap(Prerender prerender) {
		return new DefaultPooledObject<>(prerender);
	}
}
