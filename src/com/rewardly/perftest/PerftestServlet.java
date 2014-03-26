package com.rewardly.perftest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class PerftestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		stringTest(resp);
		mapTest(resp);
		entityTest(resp);
	}

	private void stringTest(HttpServletResponse resp) throws IOException {
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		
		Profiler p = new Profiler("Storing String Data Test");
		p.start("generateData");
		Map<String, String> strValues = new HashMap<>();
		for (int i = 0; i < 1000; i++) {
			strValues.put(Integer.toString(i), "testproperty value");
		}
		p.end("generateData");

		p.start("storeData");
		ms.putAll(strValues);
		p.end("storeData");

		p.start("fetchData");
		Map<String, Object> returnedStringMemcacheValues = ms.getAll(strValues.keySet());
		p.end("fetchData");
		resp.getWriter().println(p.getProfileOutputPretty());
	}

	private void mapTest(HttpServletResponse resp) throws IOException {
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		
		Profiler p = new Profiler("Storing Map Data Test");
		p.start("generateData");
		List<Map<String, String>> maps = generateTestMapData();
		p.end("generateData");

		p.start("storeData");
		Map<String, Map<String, String>> memcacheMapValues = new HashMap<>();
		for (int i = 0; i < maps.size(); i++) {
			memcacheMapValues.put(Integer.toString(i), maps.get(i));
		}
		ms.putAll(memcacheMapValues);
		p.end("storeData");

		p.start("fetchData");
		Map<String, Object> returnedMapValues = ms.getAll(memcacheMapValues.keySet());
		p.end("fetchData");
		resp.getWriter().println(p.getProfileOutputPretty());

	}

	private void entityTest(HttpServletResponse resp) throws IOException {
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

		Profiler p = new Profiler("Storing Entity Data Test");
		p.start("generateData");
		List<Entity> entities = generateTestEntityData();
		p.end("generateData");

		p.start("storeData");
		Map<String, Entity> memcacheEntityValues = new HashMap<>();
		for (int i = 0; i < entities.size(); i++) {
			memcacheEntityValues.put(Integer.toString(i), entities.get(i));
		}
		ms.putAll(memcacheEntityValues);
		p.end("storeData");

		p.start("fetchData");
		Map<String, Object> returnedMemcacheValues = ms.getAll(memcacheEntityValues.keySet());
		p.end("fetchData");
		resp.getWriter().println(p.getProfileOutputPretty());

	}

	private List<Entity> generateTestEntityData() {
		List<Entity> retVal = new ArrayList<>();
		for (int i = 1; i <= 1000; i++) {
			Entity e = new Entity("Foo");
			e.setProperty("testproperty", "value");
			retVal.add(e);
		}
		return retVal;
	}

	private List<Map<String, String>> generateTestMapData() {
		List<Map<String, String>> retVal = new ArrayList<>();
		for (int i = 1; i <= 1000; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("testproperty", "value");
			retVal.add(map);
		}
		return retVal;
	}
}
