package com.rewardly.perftest.reflection;

public abstract class ReflectionTest {
	public String getName() {
		return this.getClass().getSimpleName();
	}
	public abstract void doTest(Pojo pojo);
}
