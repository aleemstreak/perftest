package com.rewardly.perftest.benchmarks;

import java.util.HashMap;
import java.util.Map;

import com.rewardly.perftest.MemcacheBenchmark;

public class MapBenchmark extends MemcacheBenchmark {

	@Override
	public Map<String, Object> generateTestData() {
		Map<String, Object> retVal = new HashMap<>();
		for (int i = 1; i <= 1000; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("testproperty", "value");
			retVal.put(Integer.toString(i), map);
		}
		return retVal;
	}

}
