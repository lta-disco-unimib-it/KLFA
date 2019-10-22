package it.unimib.disco.lta.alfa.tasks;

public interface Task {

	/**
	 * Run the task
	 * @throws TaskException
	 */
	public void run() throws TaskException;
	
	/**
	 * Return the computed result
	 * @return
	 */
	public TaskData getResult() throws TaskException;
	
	/**
	 * Set the data needed by the task to run
	 * @param input
	 * @throws TaskException 
	 */
	public void setInput( TaskData input) throws TaskException;
}
