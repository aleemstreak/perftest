package com.rewardly.perftest.objectify.embedded;

import static com.rewardly.perftest.objectify.embedded.OfyService.ofy;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;

@SuppressWarnings("serial")
public class EmbeddedEntityTestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		runTests(new RegularEmbedTest(), resp);
		runTests(new SerializedEmbedTest(), resp);
	}

	public static final int ITERATIONS = 5_000;
	public static final int WARM_UP = 1_000;
	public static final int NUM_EMBEDDED_ENTITIES = 100;
	public static final int NUM_ENTITES = 50;
	

	public <T> void runTests(Test<T> test, HttpServletResponse resp) throws IOException {

		List<T> entities = test.generateEntities();
		storeEntities(entities);

		List<Key<T>> keys = test.getKeys(entities);

		for (int i = 0; i < WARM_UP; i++) {
			ofy().load().keys(keys);
		}

		long start = System.currentTimeMillis();
		for (int i = 0; i < ITERATIONS; i++) {
			ofy().load().keys(keys);
		}
		long duration = System.currentTimeMillis() - start;
		resp.getWriter().println(test.getClass().getSimpleName());
		resp.getWriter().println("---------------------------------");
		resp.getWriter().println("Total Time: " + duration + "ms");
		resp.getWriter().println("Total Time: " + (double) duration / (double) ITERATIONS + "ms");
		resp.getWriter().println();
	}

	private void storeEntities(List<?> entities) {
		ofy().clear();
		ofy().save().entities(entities);
	}
}
