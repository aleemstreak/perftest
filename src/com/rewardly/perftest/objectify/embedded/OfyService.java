package com.rewardly.perftest.objectify.embedded;

import java.util.logging.Level;

import com.google.appengine.api.memcache.ConsistentLogAndContinueErrorHandler;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
    static {
		factory().register(ModelWithSerializedEmbeddedEntity.class);
		factory().register(ModelWithEmbeddedEntity.class);
		factory().setMemcacheErrorHandler(new ConsistentLogAndContinueErrorHandler(Level.INFO));
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}