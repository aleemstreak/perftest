package com.rewardly.perftest.benchmarks;

import java.util.HashMap;
import java.util.Map;

import com.rewardly.perftest.MemcacheBenchmark;

public class StringBenchmark extends MemcacheBenchmark {

	@Override
	public Map<String, Object> generateTestData() {
		Map<String, Object> strValues = new HashMap<>();
		for (int i = 0; i < 1000; i++) {
			String val = "testproperty value";
			strValues.put(Integer.toString(i), val);
		}
		return strValues;
	}

}
