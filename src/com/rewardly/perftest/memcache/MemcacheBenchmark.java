package com.rewardly.perftest.memcache;

import java.util.Map;

public abstract class MemcacheBenchmark {
	public abstract Map<String, Object> generateTestData();
	
	public String getBenchmarkName() {
		return this.getClass().getSimpleName();
	}
	
	public Map<String, Object> parseResults(Map<String, Object> raw) {
		return raw;
	}
}
