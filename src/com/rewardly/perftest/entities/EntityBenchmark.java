package com.rewardly.perftest.entities;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public abstract class EntityBenchmark {
	public abstract void storeTestData(List<Entity> entities);
	public abstract void warmup();
	public abstract void runOnce(List<Key> keys);
	
	public String getBenchmarkName() {
		return this.getClass().getSimpleName();
	}
	public void setup(List<Key> keys) {
		// do nothing by default
	}
}
