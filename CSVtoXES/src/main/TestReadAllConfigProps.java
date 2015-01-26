/**
 * 
 */
package main;

import java.io.IOException;
import java.util.Map;

import config.ConfigManager;

/**
 * @author Saimir Bala
 *
 */
public class TestReadAllConfigProps {
	public static void main(String[] args) {
		Map<String, String> props = null;
	   try {
	      props = ConfigManager.getAllProperties();
      } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
	   System.out.println(props);
   }
}
