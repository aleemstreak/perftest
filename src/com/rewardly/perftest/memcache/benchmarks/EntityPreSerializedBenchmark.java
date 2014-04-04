package com.rewardly.perftest.memcache.benchmarks;

import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.Entity;
import com.rewardly.perftest.Utils;
import com.rewardly.perftest.memcache.MemcacheBenchmark;

public class EntityPreSerializedBenchmark extends MemcacheBenchmark {

	@Override
	public Map<String, Object> generateTestData() {
		Map<String, Object> retVal = new HashMap<>();
		for (int i = 1; i <= 1000; i++) {
			Entity e = new Entity("Foo");
			e.setProperty("testproperty", "value");
			retVal.put(Integer.toString(i), Utils.toBytes(e));
		}
		return retVal;
	}

	public Map<String, Object> parseResults(Map<String, Object> raw) {
		Map<String, Object> retVal = new HashMap<>();
		for (String key : raw.keySet()) {
			retVal.put(key, Utils.fromBytes((byte[]) raw.get(key)));
		}
		
		return retVal;
	}
}
