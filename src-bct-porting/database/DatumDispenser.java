package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import java.sql.PreparedStatement;
import java.sql.Connection;

public class DatumDispenser {
	private static DatumDispenser datumDispenser = null;
	private int MAX_PREPARED_STATEMENTS = 2000; 
	private ArrayList<PreparedStatement> preparedStatements = null;
	private int counter;
	private TimeOutThread timeOutThread = null;
	
	/*
	 * ----------------- Start Inner Class -----------------
	 */
	
	public class TimeOutThread extends java.lang.Thread {
		// time in milliseconds
		private final int DELAY = 1000;
		// time in seconds
		private final int MAX_ELAPSED_TIME = 15;
		private int timer = 0;
		
		public void run() {
			timer=0;
			while (!java.lang.Thread.interrupted()) {
				try {
					java.lang.Thread.sleep(DELAY);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}								
				System.out.println("Time: " + timer);
				if (timer == MAX_ELAPSED_TIME) {
					System.out.println("Forcing flushing...");
					datumDispenser.flushPreparedStatements();
					timer = 0;					
				}
				timer++;
			}
		}
		
		public void resert() {
			timer = 0;
		}
	}
	
	/*
	 * ----------------- End Inner Class -----------------
	 */
	
	/**
	 * @return a singleton instance of {@link DatumDispenser}
	 */
	public static synchronized DatumDispenser getInstance() {
		if (datumDispenser == null) {
			datumDispenser = new DatumDispenser();
		}
		return datumDispenser;
	}

	/**
	 * Constructor
	 */
	private DatumDispenser() {
		if (preparedStatements == null) {
			preparedStatements = new ArrayList<PreparedStatement>();
		}
		if (timeOutThread == null) {
			timeOutThread = new TimeOutThread();
			timeOutThread.start();
		}
	}
	
	/**
	 * Initialize the {@link DatumDispenser} state
	 */
	private void reset() {
		preparedStatements.clear();
		counter = 0;
		timeOutThread.resert();
	}
	
	/**
	 * Add a {@link PreparedStatement} object to a buffer. The statement are automatically
	 * executed when the buffer reach its MAX_PREPARED_STATEMENTS value or after a certain
	 * amount of time is elapsed
	 * 
	 * @param preparedStatement
	 */
	public synchronized void addPreparedStatement(PreparedStatement preparedStatement) {
		// add the prepared statement
		preparedStatements.add(preparedStatement);
		counter++;
		// check if it is time to flush
		if (counter == MAX_PREPARED_STATEMENTS) {
			// flush data
			flushPreparedStatements();
		}
	}
	
	/**
	 * Write data in the database
	 */	
	private synchronized void flushPreparedStatements() {
		int i = 0;
		Iterator<PreparedStatement> iterator = preparedStatements.iterator(); 
		while (iterator.hasNext()) {
			PreparedStatement preparedStatement = iterator.next();
			// Execute the statement
			try {
				preparedStatement.execute();
				preparedStatement.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			i++;
		}
		
		System.out.println("Done flushing " + i + "/" + preparedStatements.size() + " prepared statements");
		
		// reset state			
		reset();
	}


	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		System.out.println("Finalize");
		Connection connection = ConnectionDispenser.getConnection();
		flushPreparedStatements();
		timeOutThread.interrupt();
		super.finalize();
	}
	
	
}