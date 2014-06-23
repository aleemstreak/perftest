package com.rewardly.perftest.objectify.embedded;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;

public class RegularEmbedTest implements Test<ModelWithEmbeddedEntity> {

	@Override
	public List<ModelWithEmbeddedEntity> generateEntities() {
		List<ModelWithEmbeddedEntity> retVal = new ArrayList<>();
		for (int i = 1; i < EmbeddedEntityTestServlet.NUM_ENTITES + 1; i++) {
			ModelWithEmbeddedEntity e = new ModelWithEmbeddedEntity();
			e.id = (long) i;
			e.fillWithRandomData();
			retVal.add(e);
		}
		return retVal;
	}

	@Override
	public List<Key<ModelWithEmbeddedEntity>> getKeys(List<ModelWithEmbeddedEntity> entities) {
		List<Key<ModelWithEmbeddedEntity>> retVal = new ArrayList<>();
		for (ModelWithEmbeddedEntity e : entities) {
			retVal.add(e.getKey());
		}
		return retVal;
	}

}
