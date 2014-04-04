package com.rewardly.perftest.reflection.tests;

import java.lang.invoke.MethodHandle;

import com.rewardly.perftest.reflection.Pojo;
import com.rewardly.perftest.reflection.ReflectionTest;

public class MethodInvokeExactReflectionTest extends ReflectionTest {

	private MethodHandle mh;
	
	public MethodInvokeExactReflectionTest(MethodHandle mh) {
		this.mh = mh;
	}

	@Override
	public void doTest(Pojo pojo) {
		try {
			mh.invoke(pojo, "def");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
