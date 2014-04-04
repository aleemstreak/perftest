package com.rewardly.perftest.reflection.tests;

import java.lang.reflect.Field;

import com.rewardly.perftest.reflection.Pojo;
import com.rewardly.perftest.reflection.ReflectionTest;

public class FieldSetReflectionTest extends ReflectionTest {

	private Field f;
	
	public FieldSetReflectionTest(Field f) {
		this.f = f;
	}

	@Override
	public void doTest(Pojo pojo) {
		try {
			f.set(pojo, "abc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
