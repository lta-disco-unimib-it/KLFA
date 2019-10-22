/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.alfa.tools;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;



public class ActionsInserter {
	public static void main(String args[]){
		File actions = new File(args[0]);
		
//		Properties p = new Properties();
//		
//		try {
//			FileReader r = new FileReader(actions);
//			p.load(r);
//			
//			List<Calendar> calendars = getCalendars(p);
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private static List<Calendar> getCalendars(Properties p) {
		for ( Object key : p.keySet() ){
			String k = (String) key;
			
			Calendar c = getCalendar(k);
			
		}
		  
		throw new NotImplementedException();
			
		
	}

	private static Calendar getCalendar(String k) {
		throw new NotImplementedException();
//		GregorianCalendar c = new GregorianCalendar();
//		k.split(" ");
//		c.set(year, month, date, hourOfDay, minute)
//		c.set(year, month, date, hourOfDay, minute);
	}
}
