package com.rewardly.perftest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profiler implements Serializable {
	private static final long serialVersionUID = 1L;

	public class ProfileStep implements Comparable<ProfileStep>, Serializable {
		private static final long serialVersionUID = 1L;
		public String name;
		public Long startTime;
		public Long endTime;
		
		public ProfileStep(String name) {
			this.name = name;
		}
		
		public int compareTo(ProfileStep o) {
			return this.startTime.compareTo(o.startTime);
		}
		
		public String toString() {
			if (endTime == null || startTime == null) {
				return name + ": profiler step not closed";
			}
			else {
				return name + ": " + (endTime - startTime) + "ms";
			}
		}
	}
	private String name;
	private Map<String, ProfileStep> steps = new HashMap<>();
	private List<ProfileStep> stepList = new ArrayList<>();
	
	public Profiler(String name) {
		this.name = name;
	}
	
	public void start(String stepName) {
		ProfileStep ps = this.steps.get(stepName);
		if (ps == null) {
			ps = new ProfileStep(stepName);
			this.steps.put(stepName, ps);
			this.stepList.add(ps);
		}
		ps.startTime = System.currentTimeMillis();
	}

	public void end(String stepName) {
		ProfileStep ps = this.steps.get(stepName);
		if (ps == null) {
			return;
		}
		ps.endTime = System.currentTimeMillis();
	}

	public String getProfileOutputPretty () {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append("\n");
		sb.append("-------------------------").append("\n");
		for (ProfileStep step : this.stepList) {
			sb.append(step.toString()).append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	public Map<String, ProfileStep> getSteps() {
		return steps;
	}

	public void setSteps(Map<String, ProfileStep> steps) {
		this.steps = steps;
	}
}
