package com.rewardly.perftest.entities.benchmarks;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.cache.CachingDatastoreServiceFactory;
import com.rewardly.perftest.entities.EntityBenchmark;

public class ObjectifyFetcher extends EntityBenchmark {

	@Override
	public void storeTestData(List<Entity> entities) {
		CachingDatastoreServiceFactory.getDatastoreService().put(entities);
	}

	@Override
	public void warmup() {
		//do nothing
	}

	@Override
	public void runOnce(List<Key> keys) {
		CachingDatastoreServiceFactory.getDatastoreService().get(keys);		
	}

}
