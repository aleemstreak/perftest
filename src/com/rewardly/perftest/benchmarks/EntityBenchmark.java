package com.rewardly.perftest.benchmarks;

import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.Entity;
import com.rewardly.perftest.MemcacheBenchmark;

public class EntityBenchmark extends MemcacheBenchmark {
	
	@Override
	public Map<String, Object> generateTestData() {
		Map<String, Object> retVal = new HashMap<>();
		for (int i = 1; i <= 1000; i++) {
			Entity e = new Entity("Foo");
			e.setProperty("testproperty", "value");
			retVal.put(Integer.toString(i), e);
		}
		return retVal;
	}

}
