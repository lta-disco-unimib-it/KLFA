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
