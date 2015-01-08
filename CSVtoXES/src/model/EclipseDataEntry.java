package model;

import com.googlecode.jcsv.annotations.MapToColumn;

public class EclipseDataEntry {
	@MapToColumn(column=0)
	private int userID; //becomes the caseId
	@MapToColumn(column=1)
	private String activity;  //becomes lifecycle
	@MapToColumn(column=2)
	private String component; //becomes conceptName
	
	@MapToColumn(column=5)
	private long timestamp;
	
	@Override
	public String toString() {
		return "<"+userID+","+activity+","+component+","+timestamp+">";
	}

	public int getUserID() {
		return userID;
	}

	public String getActivity() {
		return activity;
	}

	public long getTimestamp() {
		return timestamp;
	}
	public String getComponent() {
		return component;
	}
}
