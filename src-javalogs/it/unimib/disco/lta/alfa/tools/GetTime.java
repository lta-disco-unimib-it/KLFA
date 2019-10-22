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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GetTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar c =Calendar.getInstance(); 
			//new GregorianCalendar();
		c.set(2008, 2, 27, 14, 05, 19);
		System.out.println("Action time"+c.getTimeInMillis());
		
		
		
		long time = 1206012465;
		
		Date d = new Date(time);
		System.out.println(d.getYear());
		c.setTimeInMillis(time*1000);
		
		System.out.println(System.nanoTime());
		System.out.println(c.get(Calendar.YEAR));
		
		long millis = (time%1000);
		time = (time/1000);
		long secs = (time%60);
		time = (time/60);
		long mins = (time%60);
		time = (time/60);
		long hours = (time%60);
		time = (time/60);
	}

}
