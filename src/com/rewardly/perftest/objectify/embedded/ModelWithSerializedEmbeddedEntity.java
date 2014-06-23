package com.rewardly.perftest.objectify.embedded;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
public class ModelWithSerializedEmbeddedEntity {
	@Id
	public Long id;
	@Serialize
	public List<EmbeddedEntity> embeddedEntities = new ArrayList<>();
	public String fieldA;
	public String fieldB;
	public String fieldC;
	public String fieldD;
	public Date fieldE;
	
	public void fillWithRandomData() {
		fieldA = "A";
		fieldB = "B";
		fieldC = "C";
		fieldD = "D";
		fieldE = new Date();
		
		for (int i = 0; i < EmbeddedEntityTestServlet.NUM_EMBEDDED_ENTITIES; i++) {
			EmbeddedEntity embeddedEntity = new EmbeddedEntity();
			embeddedEntity.fillWithRandomData();
			this.embeddedEntities.add(embeddedEntity);
		}
	}
	public Key<ModelWithSerializedEmbeddedEntity> getKey() {
		return Key.create(ModelWithSerializedEmbeddedEntity.class, id);
	}
}
