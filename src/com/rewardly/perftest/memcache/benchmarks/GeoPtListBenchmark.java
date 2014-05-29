package com.rewardly.perftest.memcache.benchmarks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.GeoPt;
import com.rewardly.perftest.memcache.MemcacheBenchmark;

public class GeoPtListBenchmark extends MemcacheBenchmark {

	@Override
	public Map<String, Object> generateTestData() {
		List<GeoPt> points = new ArrayList<>();
		Map<String, Object> retVal = new HashMap<>();
		for (int i = 1; i <= 1000; i++) {
			points.add(new GeoPt((float)Math.random(), (float)Math.random()));
		}
		retVal.put("1", points);
		
		return retVal;
	}

}
