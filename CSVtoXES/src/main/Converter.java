
package main; 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.List;

import model.GenericTrace;
import model.MinerfulTrace;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.deckfour.xes.classification.XEventNameClassifier;
import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XOrganizationalExtension;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.factory.XFactory;
import org.deckfour.xes.factory.XFactoryBufferedImpl;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.deckfour.xes.out.XesXmlSerializer;

import parser.GenericParser;
import parser.MinerfulTraceParser;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import config.ConfigManager;

public class Converter {
	 
	final static int TRACE_COLUMN = Integer.parseInt(ConfigManager.getProp("trace"));
	final static int LIFECYCLE_COLUMN = Integer.parseInt(ConfigManager.getProp("lifecycle"));
	final static int CONCEPT_COLUMN = Integer.parseInt(ConfigManager.getProp("conceptName"));
	final static int TIMESTAMP_COLUMN = Integer.parseInt(ConfigManager.getProp("timestamp"));

	//TODO: remove this
	@SuppressWarnings("static-access")
   public static void main(String[] args) throws IOException {

		CommandLineParser parser = new GnuParser();
		Options options = new Options();
		Option inFile = OptionBuilder.withArgName("input").isRequired() // make it required
									.hasArg()
									.withDescription("use given file as input" )
									.create( "i" );
		
		Option outFile = OptionBuilder.withArgName("output")
				.isRequired() // make it required
				.hasArg()
				.withDescription("use given file as output" )
				.create( "o" );
				
		options.addOption(inFile);
		options.addOption(outFile);
		HelpFormatter help = new HelpFormatter();
		String inputFile = "", outputFile = "";
		
		try {
		    // parse the command line arguments
		    CommandLine line = parser.parse( options, args );
		    //if( line.hasOption( "i" ) ) {
		    inputFile = line.getOptionValue("i");
		    //}
		    outputFile = line.getOptionValue("o");
		    
		    if(inputFile == null || outputFile == null){
		   	 help.printHelp("Converter", options);
		   	 return;
		    }
		}
		catch( ParseException exp ) {
			help.printHelp("Converter", options, true);
		}
		
		//List<MinerfulTrace> traces = readGenericLog(inputFile);
		List<GenericTrace> gtraces = readGenericLog2(inputFile);
		//XLog log = toXlog(traces);
		XLog log = toXlog2(gtraces);
		
//		List<EclipseDataEntry> dataEntries = readEclipseLog(inputFile);
//		XLog log = convertEclispeDataEntries(dataEntries);

		toXESfile(new File(outputFile), log);
	}
	
//	private static List<EclipseDataEntry> readEclipseLog(String filename) throws IOException{
//		Reader reader = new FileReader(filename);
//
//		ValueProcessorProvider provider = new ValueProcessorProvider();
//		CSVEntryParser<EclipseDataEntry> entryParser = 
//				new AnnotationEntryParser<EclipseDataEntry>(EclipseDataEntry.class, provider);
//		CSVReader<EclipseDataEntry> csvPersonReader = 
//				new CSVReaderBuilder<EclipseDataEntry>(reader).entryParser(entryParser).build();
//
//		List<EclipseDataEntry> entries = csvPersonReader.readAll();
//		
//		return entries;
//	}
	
	private static List<MinerfulTrace> readGenericLog(String filename) throws IOException{
		Reader reader = new FileReader(filename); 
		MinerfulTraceParser minerfulTraceParser = 
				new MinerfulTraceParser(TRACE_COLUMN, CONCEPT_COLUMN, LIFECYCLE_COLUMN, TIMESTAMP_COLUMN);//The numbers must be passed as parameters
		CSVReader<MinerfulTrace> csvTraceReader = new CSVReaderBuilder<MinerfulTrace>(reader).entryParser(minerfulTraceParser).build();
		List<MinerfulTrace> traces = csvTraceReader.readAll();
		
		return traces;
		
	}
	
	private static List<GenericTrace> readGenericLog2(String filename) throws IOException{
		Reader reader = new FileReader(filename); 
		GenericParser genericTraceParser = new GenericParser();
		CSVReader<GenericTrace> csvTraceReader = new CSVReaderBuilder<GenericTrace>(reader).entryParser(genericTraceParser).build();
		List<GenericTrace> traces = csvTraceReader.readAll();
		
		return traces;
		
	}
	
	//private static List<MineableDataEntry> readGenericLog()
	
//	private static XLog convertEclispeDataEntries(List<EclipseDataEntry> entriesList){
//		
//		if(entriesList == null)
//			return null;
//		
//		XFactory xFactory = new XFactoryBufferedImpl();
//		XLog xLog = xFactory.createLog();
//		
//		XTrace xTrace = null;
//		XEvent xEvent = null;
//		XConceptExtension concExt = XConceptExtension.instance();
//		XLifecycleExtension lifeExtension = XLifecycleExtension.instance();
//		XTimeExtension timeExtension = XTimeExtension.instance();
//		xLog.getExtensions().add(concExt);
//		xLog.getExtensions().add(lifeExtension);
//		xLog.getExtensions().add(timeExtension);
//		xLog.getClassifiers().add(new XEventNameClassifier());
//		
//		concExt.assignName(xLog, "Synthetic log");
//		//lifeExtension.assignModel(xLog, XLifecycleExtension.VALUE_MODEL_STANDARD);
//		//int traceCounter = 0;
//		//Remember the last case Id seen 		
//		int lastCase = -1;//supposed not to have negative caseIds
//		
//		for (EclipseDataEntry eclipseDataEntry : entriesList) {
//			
//			// When I find a new new UserId (representing the Case Id) I create a new trace 
//			if(lastCase != eclipseDataEntry.getUserID()){
//				xTrace = xFactory.createTrace();
//				//concExt.assignName(xTrace, "Trace no. "+(++traceCounter));
//				concExt.assignName(xTrace, eclipseDataEntry.getUserID()+"");
//				lastCase = eclipseDataEntry.getUserID();
//				xLog.add(xTrace);
//			}
//			
//			//Create the event within the current trace
//			xEvent = xFactory.createEvent();
//		   concExt.assignName(xEvent, eclipseDataEntry.getComponent()+"");
//		   lifeExtension.assignTransition(xEvent, eclipseDataEntry.getActivity());
//		   timeExtension.assignTimestamp(xEvent, new Timestamp(eclipseDataEntry.getTimestamp()));
//			xTrace.add(xEvent);
//		}//end loop
//		
//		return xLog;		
//	}
	
	private static XLog toXlog(List<MinerfulTrace> traces){
		if(traces == null)
			return null;
		
		XFactory xFactory = new XFactoryBufferedImpl();
		XLog xLog = xFactory.createLog();
		
		XTrace xTrace = null;
		XEvent xEvent = null;
		XConceptExtension concExt = XConceptExtension.instance();
		XLifecycleExtension lifeExtension = XLifecycleExtension.instance();
		XTimeExtension timeExtension = XTimeExtension.instance();
		xLog.getExtensions().add(concExt);
		xLog.getExtensions().add(lifeExtension);
		xLog.getExtensions().add(timeExtension);
		xLog.getClassifiers().add(new XEventNameClassifier());
		
		concExt.assignName(xLog, "Synthetic log");
		lifeExtension.assignModel(xLog, XLifecycleExtension.VALUE_MODEL_STANDARD);
		//int traceCounter = 0;
		//Remember the last case Id seen 		
		String lastSeenTrace = "";//no case seen
		
		for (MinerfulTrace trace : traces) {
			
			// When I find a new new UserId (representing the Case Id) I create a new trace 
			if(!lastSeenTrace.equals(trace.getTrace())){
				xTrace = xFactory.createTrace();
				//concExt.assign(xTrace, "Trace no. "+(++traceCounter));
				concExt.assignName(xTrace, trace.getTrace()+"");
				lastSeenTrace = trace.getTrace();
				xLog.add(xTrace);
			}
			
			//Create the event within the current trace
			xEvent = xFactory.createEvent();
		   concExt.assignName(xEvent, trace.getConceptName()+"");
		   //lifeExtension.assignTransition(xEvent, trace.getLifecycle());
		   lifeExtension.assignStandardTransition(xEvent, XLifecycleExtension.StandardModel.COMPLETE);
		   timeExtension.assignTimestamp(xEvent, trace.getTimestamp());
			xTrace.add(xEvent);
		}//end loop
		
		return xLog;
	}

	//TODO: new	
	private static XLog toXlog2(List<GenericTrace> traces){
		if(traces == null)
			return null;
		
		XFactory xFactory = new XFactoryBufferedImpl();
		XLog xLog = xFactory.createLog();
		
		XTrace xTrace = null;
		XEvent xEvent = null;
		XConceptExtension concExt = XConceptExtension.instance();
		XLifecycleExtension lifeExtension = XLifecycleExtension.instance();
		XTimeExtension timeExtension = XTimeExtension.instance();
		XOrganizationalExtension orgExt = XOrganizationalExtension.instance();
		xLog.getExtensions().add(concExt);
		xLog.getExtensions().add(lifeExtension);
		xLog.getExtensions().add(timeExtension);
		xLog.getExtensions().add(orgExt);
		xLog.getClassifiers().add(new XEventNameClassifier());
		
		concExt.assignName(xLog, "Synthetic log");
		lifeExtension.assignModel(xLog, XLifecycleExtension.VALUE_MODEL_STANDARD);
		//int traceCounter = 0;
		//Remember the last case Id seen 		
		String lastSeenTrace = "";//no case seen
		
		for (GenericTrace trace : traces) {
			
			// When I find a new new UserId (representing the Case Id) I create a new trace 
			if(!lastSeenTrace.equals(trace.getTraceColumn())){
				xTrace = xFactory.createTrace();
				//concExt.assign(xTrace, "Trace no. "+(++traceCounter));
				concExt.assignName(xTrace, trace.getTraceColumn()+"");
				lastSeenTrace = trace.getTraceColumn();
				xLog.add(xTrace);
			}
			
			//Create the event within the current trace
			xEvent = xFactory.createEvent();
		   concExt.assignName(xEvent, trace.getConceptNameColumn()+"");
		   //lifeExtension.assignTransition(xEvent, trace.getLifecycle());
		   lifeExtension.assignStandardTransition(xEvent, XLifecycleExtension.StandardModel.COMPLETE);
		   timeExtension.assignTimestamp(xEvent, Timestamp.valueOf(trace.getTimestampColumn()));
		   orgExt.assignResource(xEvent, trace.getResourceColumn());
			xTrace.add(xEvent);
		}//end loop
		
		return xLog;
	}
	private static void toXESfile(File file, XLog xLog) throws FileNotFoundException, IOException {
		if(xLog == null) 
			return;
		
		XesXmlSerializer serializer = new XesXmlSerializer();
		serializer.serialize(xLog, new FileOutputStream(file));
		
   }

}
