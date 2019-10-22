package theoremProver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import conf.EnvironmentalSetter;

public class Simplify {
	private String temporaryDir;
	private String temporaryFileName;
	private String executableName;
	
	public Simplify() {
		temporaryDir = EnvironmentalSetter.getSimplifyTheoremProverSettings().getTemporaryDir();
		temporaryFileName = "simplifyInputFormula.txt";
		executableName = EnvironmentalSetter.getSimplifyTheoremProverSettings().getExecutableFile();
		
		System.out.println("Simplify Theorem Prover Settings:");
		System.out.println("temporaryDir: " + temporaryDir);
		System.out.println("temporaryFileName: " + temporaryFileName);
		System.out.println("executableName: " + executableName);
	}
	
	/**
	 * Evaluate the given formula formatted suitable by the Simplify Theorem Prover
	 * @param formula
	 * @return
	 * @throws IOException
	 */
	public ArrayList<SimplifyResult> doFormula(String formula) throws IOException {
		ArrayList<SimplifyResult> results = new ArrayList<SimplifyResult>();
		if (formula != null) {
			results = executeSimplify(formula); 
		}
		return results;
	}
	
	/**
	 * Concatenates with IMPLIES two predicates. For instance:
	 * p1: (EQ (age 23) (city Milan))
	 * p2: (AND (zip 20100) (contry Italy))
	 * returns the evaluation of the formula:
	 * (IMPLIES (EQ (age 23) (city Milan)) (AND (zip 20100) (contry Italy)))
	 * 
	 * @param predicate1
	 * @param predicate2
	 * @return
	 * @throws IOException
	 */
	public ArrayList<SimplifyResult> doIMPLIES(String predicate1, String predicate2) throws IOException {
		String formula = new String();
		
		formula += "(IMPLIES " + predicate1 + " " + predicate2 + " )";
				
		return doFormula(formula);		
	}
	
	/**
	 * Concatenates in AND the array of predicates. For instance:
	 * p1: (EQ (age 23) (city Milan))
	 * p2: (AND (zip 20100) (contry Italy))
	 * returns the evaluation of the formula:
	 * (AND (EQ (age 23) (city Milan)) (AND (zip 20100) (contry Italy)))
	 *  
	 * @param predicates the predicates to be concatenated
	 * @return the concatented predicates
	 * @throws IOException
	 */	
	public ArrayList<SimplifyResult> doAND(String predicates[]) throws IOException {
		String formula = new String();
		
		formula += "(AND ";
		for (String p : predicates) {
			formula += p + " ";
		}
		formula += ")";		
		
		return doFormula(formula);
	} 
	
	/**
	 * Parses the execution result looking for valid and invalid resposes.
	 * The prover result is a sequence of the following statement:
	 * 
	 * 	Counterexample:
	 * 		context:
	 * 			(AND
	 * 				(EQ (select value this) -9)
	 * 			)
	 * 
	 * 	1: Invalid.
	 * 
	 * the 'value' variable will contain the value 'Invalid' or 'Valid'
	 * the counterExample variable will contain the rest of the result
	 * 
	 * @param executionResult
	 * @return
	 * @throws IOException
	 */
	private ArrayList<SimplifyResult> parseResult(String executionResult) throws IOException {
		// create buffer readers
		StringReader stringReader = new StringReader(executionResult);
		BufferedReader bufferedReader = new BufferedReader(stringReader);				
		
		String line;
		ArrayList<SimplifyResult> results = new ArrayList<SimplifyResult>();
		
		while ((line = bufferedReader.readLine()) != null) {
			if (line.indexOf("Invalid") != -1) {
				results.add(new SimplifyResult(line, SimplifyResult.INVALID));
			}
			else if (line.indexOf("Valid") != -1) {
				results.add(new SimplifyResult(line, SimplifyResult.VALID));
			}
			else if (line.indexOf("Unable to open file") != -1) {
				results.add(new SimplifyResult(line, SimplifyResult.UNABLE_TO_OPEN_FILE));
			}
			else if (line.indexOf("Bad input") != -1) {
				results.add(new SimplifyResult(line, SimplifyResult.BAD_INPUT));
			}
			else if (line.indexOf("Sx.ReadError in file") != -1) {
				results.add(new SimplifyResult(line, SimplifyResult.SYNTAX_ERROR));
			}
			line = bufferedReader.readLine();
		}		
		
		return results;
	}
	
	/**
	 * Execute the program Simplify. The program MUST be reachable through the
	 * system PATH. The executable name MUST be "Simplify". 
	 * @param formula
	 * @return
	 * @throws IOException
	 */
	private ArrayList<SimplifyResult> executeSimplify(String formula) throws IOException {
		// check if the temporary directory exists
		File dir = new File(temporaryDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// create the input file
		File formulaInputFile = new File(temporaryDir + "/" + temporaryFileName);
		FileOutputStream fOut = new FileOutputStream(formulaInputFile);
		fOut.write(formula.getBytes());
		fOut.close();
		
		// command line
		String commandLine = executableName + " -nosc " + formulaInputFile.getAbsolutePath();		
		// execute symplify theorem prover
		Process process = Runtime.getRuntime().exec(commandLine);
		
		// create input/output streams
		final BufferedInputStream err = new BufferedInputStream(process.getErrorStream());
		final BufferedInputStream in = new BufferedInputStream(process.getInputStream());
		final StringBuffer executionResult = new StringBuffer();
		
		// Thread handling the process error stream
		Thread errThread = new Thread("errorStreamThread") {
			@Override
			public void run() {				
				super.run();
				int i;
				try {
					// print any error message on the standard error output
					while ((i = err.read()) != -1) { System.err.write(i); }
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}			
		};
		// Thread handling the process input stream
		Thread inThread = new Thread("inputStreamThread") {
			@Override
			public void run() {				
				super.run();
				int i;
				String executionResultTmp = new String();
				try {
					// get the result of the execution
					while ((i = in.read()) != -1) {
						executionResultTmp += Character.toString((char)i);
					}					
					executionResult.append(executionResultTmp);					
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}			
		};
		// Start threads
		errThread.start();
		inThread.start();		
		// wait until all the thread are die
		try {
			errThread.join();
			inThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// close the streams
		err.close();
		in.close();
		
		// remove formula input file
		formulaInputFile.delete();
		
		// parse and return the results
		return parseResult(executionResult.toString());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		Simplify simplify = new Simplify();
		
		String predicates[] = new String[] {
			"(IMPLIES (EQ (select |value| |this|) -2) (>= (select |value| |this|) 0))",
			"(IMPLIES (EQ (select |value| |this|) 13) (>= (select |value| |this|) 0))"
		};
		
		try {						
			ArrayList<SimplifyResult> results;
			
			String formula = "(NEQ (select |intValue()| (select (select elems |parameter|) 0)) (select |intValue()| (select (select elems |__orig__parameter|) 0))) (EQ (select |intValue()| (select (select elems |parameter|) 0)) 0)";
			//String formula = "";
			
			//results = simplify.doIMPLIES(predicates[1], predicates[0]);
			results = simplify.doFormula(formula);			
			
			for (SimplifyResult result : results) {
				System.out.println("Simplify output: " + result.getProgramOutput());
				System.out.println("Result: " + result.getResult());
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
