package com.rewardly.perftest.entities;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.rewardly.perftest.entities.benchmarks.DatastoreFetcher;
import com.rewardly.perftest.entities.benchmarks.MemcacheFetcher;
import com.rewardly.perftest.entities.benchmarks.ObjectifyFetcher;

@SuppressWarnings("serial")
public class EntityTestServlet extends HttpServlet {
	private SecureRandom random = new SecureRandom();
	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		resp.setContentType("text/plain");
		
		List<Entity> testData = generateTestData();
		List<Key> keys = getKeysFromEntities(testData);
		
		runBenchmark(new DatastoreFetcher(), resp, testData, keys);
		runBenchmark(new ObjectifyFetcher(), resp, testData, keys);
		runBenchmark(new MemcacheFetcher(), resp, testData, keys);
	}
	
	private void runBenchmark(EntityBenchmark benchmark, HttpServletResponse resp, List<Entity> testData, List<Key> keys) throws IOException {
		benchmark.storeTestData(testData);
		benchmark.warmup();
		benchmark.setup(keys);
		
		long runTime = System.nanoTime();
		for (int i = 0; i < 10; i++) {
			benchmark.runOnce(keys);
		}
		runTime = (System.nanoTime() - runTime);
		double averageRunTimeMs = runTime/(1e6 * 20);
		
		resp.getWriter().println(benchmark.getBenchmarkName());
		resp.getWriter().println("----------------");
		resp.getWriter().println("Average Fetch Time: " + String.format("%.2fms", averageRunTimeMs));
		resp.getWriter().println();
		resp.getWriter().println();
		resp.getWriter().println();
	}

	private List<Key> getKeysFromEntities(List<Entity> entities) {
		List<Key> keys = new ArrayList<>();
		for (Entity e : entities) {
			keys.add(e.getKey());
		}
		return keys;
	}

	private List<Entity> generateTestData() {
		List<Entity> entities = new ArrayList<>();
		for (int i = 1; i <= 1000; i++) {
			Key k = KeyFactory.createKey("testentity", i);
			Entity e = new Entity(k);
			for (int j = 0; j < 100; j++) {
				e.setProperty("prop" + j, generateRandomString(40));
			}
			entities.add(e);
		}
		return entities;
	}

	private String generateRandomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(AB.charAt(random.nextInt(AB.length())));
		}
		return sb.toString();
	}
}
