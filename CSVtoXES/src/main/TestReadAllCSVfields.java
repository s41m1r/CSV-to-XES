/**
 * 
 */
package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

/**
 * @author Saimir Bala
 *
 */
public class TestReadAllCSVfields {
	
	public static void main(String[] args) throws IOException {
		Reader csvFile = new InputStreamReader(new FileInputStream("/home/saimir/out.csv"));
		
		CSVReader<String[]> entryReader = CSVReaderBuilder.newDefaultReader(csvFile);
		
		List<String[]> entries = entryReader.readAll();
		
		System.out.println("All:");
		for (String[] strings : entries) {
			for (String string : strings) {
				System.out.print(" "+string);
			}
			System.out.println();
      }
		
		
   }
	
}
