package com.rewardly.perftest.reflection;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rewardly.perftest.Profiler;
import com.rewardly.perftest.reflection.tests.DirectReflectionTest;
import com.rewardly.perftest.reflection.tests.FieldSetReflectionTest;
import com.rewardly.perftest.reflection.tests.MethodInvokeExactReflectionTest;
import com.rewardly.perftest.reflection.tests.MethodInvokeReflectionTest;

@SuppressWarnings("serial")
public class ReflectionTestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		try {
			runTests(resp);
		} catch (Throwable e) {
			resp.getWriter().print("Error");
		}
	}

	private static final int ITERATIONS = 50_000;
	private static final int WARM_UP = 1_000;

	public void runTests(HttpServletResponse resp) throws Throwable {
		Pojo pojo = new Pojo();
		Field nameField = Pojo.class.getDeclaredField("name");
		nameField.setAccessible(true);

		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.unreflectSetter(nameField);

		List<ReflectionTest> tests = Arrays.asList(new DirectReflectionTest(),
				new FieldSetReflectionTest(nameField), new MethodInvokeReflectionTest(mh),
				new MethodInvokeExactReflectionTest(mh));

		
		for (int i = 0; i < WARM_UP; i++) {
			for (ReflectionTest test : tests) {
				test.doTest(pojo);
			}
		}
		
		
		Profiler p = new Profiler("Reflection Tests");
		
		for (ReflectionTest test : tests) {
			p.start(test.getName());
			for (int j = 0; j < ITERATIONS; j++) {
				test.doTest(pojo);
			}
			p.end(test.getName());
		}
		
		resp.getWriter().print(p.getProfileOutputPretty());
	}

}
