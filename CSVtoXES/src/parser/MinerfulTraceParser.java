package parser;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.MinerfulTrace;

import com.googlecode.jcsv.reader.CSVEntryParser;

public class MinerfulTraceParser implements CSVEntryParser<MinerfulTrace> {

	private int traceColumn;
	private int conceptNameColumn;
	private int lifecycleColumn;
	private int timestampColumn;

	public MinerfulTraceParser(int tColumn, int cColumn, int lColumn,
			int tsColumn) {
		traceColumn = tColumn;
		conceptNameColumn = cColumn;
		lifecycleColumn = lColumn;
		timestampColumn = tsColumn;
	}

	@Override
	public MinerfulTrace parseEntry(String... data) {

		String t = data[traceColumn];
		String c = data[conceptNameColumn];
		String l = data[lifecycleColumn];
		String ts = data[timestampColumn];
		Timestamp tStamp;

		if(ts.matches("\\d+")) // timestamp is a long integer
			tStamp = new Timestamp(Long.parseLong(ts));
		else{ //timestamp is in a date format

			String delims = "[ \\+]"; // so the delimiters are:  + - * / ^ space
			String[] tokens = ts.split(delims);
			String theDate = tokens[0];
			
			if(tokens.length > 1)
				theDate+="T"+tokens[1]+"";
			
			//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
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
		return new MinerfulTrace(t,c,l,tStamp);
	}

}
