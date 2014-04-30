package com.rewardly.perftest.entities.benchmarks;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.rewardly.perftest.entities.EntityBenchmark;

public class DatastoreFetcher extends EntityBenchmark {

	@Override
	public void storeTestData(List<Entity> entities) {
		DatastoreServiceFactory.getDatastoreService().put(entities);
	}

	@Override
	public void warmup() {
		//do nothing
	}

	@Override
	public void runOnce(List<Key> keys) {
		DatastoreServiceFactory.getDatastoreService().get(keys);		
	}

}
