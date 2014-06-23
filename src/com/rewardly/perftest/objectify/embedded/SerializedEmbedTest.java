package com.rewardly.perftest.objectify.embedded;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;

public class SerializedEmbedTest implements Test<ModelWithSerializedEmbeddedEntity> {

	@Override
	public List<ModelWithSerializedEmbeddedEntity> generateEntities() {
		List<ModelWithSerializedEmbeddedEntity> retVal = new ArrayList<>();
		for (int i = 1; i < EmbeddedEntityTestServlet.NUM_ENTITES  + 1; i++) {
			ModelWithSerializedEmbeddedEntity e = new ModelWithSerializedEmbeddedEntity();
			e.id = (long) i;
			e.fillWithRandomData();
			retVal.add(e);
		}
		return retVal;
	}

	@Override
	public List<Key<ModelWithSerializedEmbeddedEntity>> getKeys(List<ModelWithSerializedEmbeddedEntity> entities) {
		List<Key<ModelWithSerializedEmbeddedEntity>> retVal = new ArrayList<>();
		for (ModelWithSerializedEmbeddedEntity e : entities) {
			retVal.add(e.getKey());
		}
		return retVal;
	}

}
