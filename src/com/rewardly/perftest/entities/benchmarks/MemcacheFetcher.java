package com.rewardly.perftest.entities.benchmarks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.rewardly.perftest.entities.EntityBenchmark;

public class MemcacheFetcher extends EntityBenchmark {

	@Override
	public void storeTestData(List<Entity> entities) {
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		Map<String, Entity> vals = new HashMap<>();
		for (Entity e : entities) {
			vals.put(KeyFactory.keyToString(e.getKey()), e);
		}
		ms.putAll(vals);
	}

	@Override
	public void warmup() {
		// do nothing
	}

	@Override
	public void runOnce(List<Key> keys) {
		List<String> memcacheKeys = new ArrayList<>();
		for (Key k : keys) {
			memcacheKeys.add(KeyFactory.keyToString(k));
		}
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		ms.getAll(memcacheKeys);
	}

}
