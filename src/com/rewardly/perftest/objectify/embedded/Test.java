package com.rewardly.perftest.objectify.embedded;

import java.util.List;

import com.googlecode.objectify.Key;

public interface Test<T>  {
	public List<T> generateEntities();
	public List<Key<T>> getKeys(List<T> entities);
}
