/**
 * 
 */
package parser;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import model.GenericTrace;

import com.googlecode.jcsv.reader.CSVEntryParser;

import config.ConfigManager;

/**
 * @author Saimir Bala
 *
 */
public class GenericParser implements CSVEntryParser<GenericTrace> {
	
	Map<String, String> props;
	
	/**
	 * @throws IOException 
    * 
    */
   public GenericParser() throws IOException {
   	props = ConfigManager.getAllProperties();
	   
   }
	
	
	/* (non-Javadoc)
	 * @see com.googlecode.jcsv.reader.CSVEntryParser#parseEntry(java.lang.String[])
	 */
   @Override
   public GenericTrace parseEntry(String... data) {
   	int traceColumn = Integer.parseInt(props.get("trace"));
   	int conceptNameColumn = Integer.parseInt(props.get("conceptName"));
   	int lifecycleColumn = Integer.parseInt(props.get("lifecycle"));
   	int timestampColumn = Integer.parseInt(props.get("timestamp"));
   	int resourceColumn = Integer.parseInt(props.get("resource"));
   	
   	String t = data[traceColumn];
		String c = data[conceptNameColumn];
		String l = data[lifecycleColumn];
		String ts = data[timestampColumn];
		String resource = data[resourceColumn];
		Timestamp tStamp;

		if(ts.matches("\\d+")) // timestamp is a long integer
			tStamp = new Timestamp(Long.parseLong(ts));
		else{ //timestamp is in a date format

			String delims = "[ \\+]"; // so the delimiters are:  + - * / ^ space
			String[] tokens = ts.split(delims);
			String theDate = tokens[0];
			
			if(tokens.length > 1)
				theDate+="T"+tokens[1]+"";
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			//SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	      Calendar cal = Calendar.getInstance();
         
	      try {
	         Date d = formatter.parse(theDate);
	         cal.setTime(d);
	         //cal.setTimeZone(TimeZone.getTimeZone("GMT"+tokens[2]));
         } catch (ParseException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
         }
			tStamp = new Timestamp(cal.getTimeInMillis());
		}
		return new GenericTrace(t,c,l,tStamp, resource);

   }

}
