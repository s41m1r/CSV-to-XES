package model;

import java.sql.Timestamp;

public class MinerfulTrace {
	
	private String trace;
	private String conceptName;
	private String lifecycle;
	private Timestamp timestamp;
	
	public MinerfulTrace(String trace, String conceptName, String lifecycle, Timestamp timestamp) {
		this.trace = trace;
		this.lifecycle = lifecycle;
		this.conceptName = conceptName;
		this.timestamp = timestamp;
   }
	
	public String getTrace() {
		return trace;
	}
	public String getConceptName() {
		return conceptName;
	}
	public String getLifecycle() {
		return lifecycle;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
	   return "<"+trace+","+conceptName+","+lifecycle+","+timestamp+">";
	}
	
}
