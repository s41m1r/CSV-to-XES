/**
 * Class for generic key/value entries
 */
package model;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author Saimir Bala
 *  *
 */
public class GenericTrace{
	
	private String traceColumn;
	private String conceptNameColumn;
	private String lifecycleColumn;
	private String timestampColumn;
	private String resourceColumn;
	
	
	/**
    * 
    */
   public GenericTrace(Map<String, String> props) {
   	traceColumn = props.get("trace");
   	conceptNameColumn = props.get("conceptName");
   	lifecycleColumn = props.get("lifecycle");
   	timestampColumn = props.get("timestamp");
   	resourceColumn = props.get("resource");
   }
   
   /**
	 * @param traceColumn
	 * @param conceptNameColumn
	 * @param lifecycleColumn
	 * @param tStamp
	 * @param resourceColumn
	 */
   public GenericTrace(String traceColumn, String conceptNameColumn,
         String lifecycleColumn, Timestamp tStamp, String resourceColumn) {
	   this.traceColumn = traceColumn;
	   this.conceptNameColumn = conceptNameColumn;
	   this.lifecycleColumn = lifecycleColumn;
	   this.timestampColumn = tStamp.toString();
	   this.resourceColumn = resourceColumn;
   }

	public String getTraceColumn() {
		return traceColumn;
	}

	public String getConceptNameColumn() {
		return conceptNameColumn;
	}

	public String getLifecycleColumn() {
		return lifecycleColumn;
	}

	public String getTimestampColumn() {
		return timestampColumn;
	}

	public String getResourceColumn() {
		return resourceColumn;
	}
}
