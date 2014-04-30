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
		runBenchmark(new DatastoreFetcher(), resp);
		runBenchmark(new ObjectifyFetcher(), resp);
		runBenchmark(new MemcacheFetcher(), resp);
	}
	
	private void runBenchmark(EntityBenchmark benchmark, HttpServletResponse resp) throws IOException {
		List<Entity> testData = generateTestData(benchmark);
		List<Key> keys = getKeysFromEntities(testData);
		
		benchmark.storeTestData(testData);
		benchmark.warmup();
		
		long runTime = System.nanoTime();
		for (int i = 0; i < 20; i++) {
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

	private List<Entity> generateTestData(EntityBenchmark benchmark) {
		List<Entity> entities = new ArrayList<>();
		for (int i = 1; i <= 1000; i++) {
			Key k = KeyFactory.createKey(benchmark.getBenchmarkName(), i);
			Entity e = new Entity(k);
			for (int j = 0; j < 20; j++) {
				e.setProperty("prop" + j, generateRandomString(40));
			}
			entities.add(e);
		}
		return entities;
	}

	private Object generateRandomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(AB.charAt(random.nextInt(AB.length())));
		}
		return sb.toString();
	}
}
