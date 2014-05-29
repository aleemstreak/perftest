package com.rewardly.perftest.memcache;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.rewardly.perftest.Utils;
import com.rewardly.perftest.memcache.benchmarks.EntityBenchmark;
import com.rewardly.perftest.memcache.benchmarks.EntityPreSerializedBenchmark;
import com.rewardly.perftest.memcache.benchmarks.GeoPtListBenchmark;
import com.rewardly.perftest.memcache.benchmarks.MapBenchmark;
import com.rewardly.perftest.memcache.benchmarks.StringBenchmark;

@SuppressWarnings("serial")
public class PerftestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		resp.setContentType("text/plain");

		runBenchmark(new StringBenchmark("Some random string here."), resp);
		runBenchmark(new StringBenchmark("Some really long piece of text here. This sentence will go on and on forever.... there is still more text to type. Maybe I should have just looked up some lorem ipsum text ro something."), resp);
		runBenchmark(new MapBenchmark(), resp);
		runBenchmark(new GeoPtListBenchmark(), resp);
		runBenchmark(new EntityBenchmark(), resp);
		runBenchmark(new EntityPreSerializedBenchmark(), resp);
		
	}
	
	private void runBenchmark(MemcacheBenchmark benchmark, HttpServletResponse resp) throws IOException {
		Map<String, Object> testData = benchmark.generateTestData();
		double sizeKb = calculateSizeOfTestData(testData);
		
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		ms.putAll(testData);
		
		long cumulativeRunTimeNs = 0;
		for (int i = 0; i < 20; i++) {
			long start = System.nanoTime();
			Map<String, Object> results = ms.getAll(testData.keySet());
			results = benchmark.parseResults(results);
			cumulativeRunTimeNs += (System.nanoTime() - start);
		}
		double averageRunTimeMs = cumulativeRunTimeNs/(1e6 * 20);
		
		resp.getWriter().println(benchmark.getBenchmarkName());
		resp.getWriter().println("----------------");
		resp.getWriter().println("Average Fetch Time: " + String.format("%.2fms", averageRunTimeMs));
		resp.getWriter().println("Fetch Size: " + String.format("%.2fKB", sizeKb));
		resp.getWriter().println();
		resp.getWriter().println();
		resp.getWriter().println();
	}

	private double calculateSizeOfTestData(Map<String, Object> testData) {
		double retVal = 0;
		for (Object val : testData.values()) {
			retVal += Utils.toBytes(val).length;
		}
		retVal = retVal / 1024;
		return retVal;
	}	
}
