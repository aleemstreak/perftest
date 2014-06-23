package com.rewardly.perftest.objectify.embedded;

import java.io.Serializable;
import java.util.Date;

public class EmbeddedEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
	}
	
}