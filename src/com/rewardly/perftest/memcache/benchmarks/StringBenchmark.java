package com.rewardly.perftest.memcache.benchmarks;

import java.util.HashMap;
import java.util.Map;

import com.rewardly.perftest.memcache.MemcacheBenchmark;

public class StringBenchmark extends MemcacheBenchmark {
	private String testValue = null;
	
	public StringBenchmark(String testValue) {
		this.testValue = testValue;
	}
	
	@Override
	public Map<String, Object> generateTestData() {
		Map<String, Object> strValues = new HashMap<>();
		for (int i = 0; i < 1000; i++) {
			strValues.put(Integer.toString(i), this.testValue);
		}
		return strValues;
	}
	
	public String getBenchmarkName() {
		return this.getClass().getSimpleName() + " - " + this.testValue.length() + " character string";
	}

}
