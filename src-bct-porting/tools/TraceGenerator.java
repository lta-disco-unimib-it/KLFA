package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import automata.State;
import automata.Transition;
import automata.fsa.FiniteStateAutomaton;

public class TraceGenerator {

	public static void main(String[] args) {
		if (args.length < 6) {
			System.out
					.println("Usage: java tools.TraceGenerator -fsa <file> -num_traces <number of traces> -outputFile <file>");
			System.out.println();
			System.out
					.println("-fsa: must specify a file containing a serialized FSA");
			System.out
					.println("-num_traces: number of traces that must be randomly generated from the given FSA");
			System.out
					.println("-outputFile: output file where the generated traces will be stored");
			System.out.println();
			System.out
					.println("Example: java tools.TraceGenerator -fsa myFsa.ser -num_traces 100 -outputFile myFsa.traces");
			return;
		}
		if (!args[0].equals("-fsa"))
			throw new IllegalArgumentException("First argument must be -fsa");
		else if (!args[2].equals("-num_traces"))
			throw new IllegalArgumentException(
					"Second argument must be -num_traces");
		else if (!args[4].equals("-outputFile"))
			throw new IllegalArgumentException(
					"Third argument must be -outputFile");
		
		int numTraces=0;
		try {
			numTraces = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			System.out
					.println("Error: number of traces must be an integer number");
			//e.printStackTrace();
			System.exit(1);
		}

		// reading the FiniteStateAutomaton
		FiniteStateAutomaton fsa = null;
		try {
			fsa = FiniteStateAutomaton.readSerializedFSA(args[1]);
		} catch (FileNotFoundException e) {
			System.out.println("Error: File "
					+ new File(args[1]).getAbsolutePath() + " does not exist!");
			//e.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.out
					.println("The class corresponding to the deserialized object has not been found");
			//e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Input/Output error while opening "
					+ new File(args[1]).getAbsolutePath() + " resource");
			//e.printStackTrace();
			System.exit(1);
		}

		File outputFile = new File(args[5]);
		outputFile.delete();

		try {
			if (!outputFile.createNewFile()) {
				System.out.println("Error: impossible to create output file");
				System.exit(1);
			}
		} catch (IOException e1) {
			System.out.println("OUTPUT FOLDER...");
			System.exit(1);
		}

		generateTraces(fsa, outputFile, numTraces);
	}

	private static final boolean APPEND = true;
	
	private static void generateTraces(FiniteStateAutomaton fsa,
			File outputFile, int numTraces) {
		
		// initialize output file
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(outputFile, APPEND);
		} catch (FileNotFoundException e) {
			System.out.println("Error: impossible to open output file " + outputFile);
			//e.printStackTrace();
			System.exit(1);
		}
		
		for(int i=0; i<numTraces;i++) {
			System.out.println("Generation of trace " + (i+1) + "/" +numTraces );
			generateTrace(fsa, outStream);
		}

		// close output file
		try {
			outStream.flush();
			outStream.close();
		} catch (IOException e1) {
			System.out.println("Error while closing the output stream.");
		}
		
	}
	
	private static final String interactionSeparator = "#";
	private static final String traceSeparator = "|";
	
	private static State selectInteraction(State state, FiniteStateAutomaton fsa, Interaction interaction) {

		Transition[] outgoingTransitions = fsa.getTransitionsFromState(state);

		if (fsa.isFinalState(state)){
			if (genRandomValue() < ( 1 / ( (double)outgoingTransitions.length + 1) ) ) {
				return null;
			}
		}
		
		//random selection of the transition
		double val = genRandomValue();
		
		int scelta = -1;
		int i =1;
		
		while (scelta==-1) {
			if (scelta < ((double)i/outgoingTransitions.length)) {
				scelta = i-1;
			}
		}
		
		//returning the label and the next state
		interaction.setInteraction(((Transition)outgoingTransitions[scelta]).getDescription());
		return ((Transition)outgoingTransitions[scelta]).getToState();
	}

	
	private static State generateInteractionWithSeparator(State state, FiniteStateAutomaton fsa, FileOutputStream outStream) {
		Interaction interaction = new TraceGenerator().new Interaction();
		State newState = null;
		
		if ((newState=selectInteraction(state, fsa, interaction))==null) return null;
		
		try {
			outStream.write(interactionSeparator.getBytes());
			outStream.write(interaction.getInteraction().getBytes());
		} catch (IOException e) {
			System.out.println("Error: impossible to write the output file");
			//e.printStackTrace();
			System.exit(1);
		}
		return newState;		
	}
	
	private static State generateInteractionWithoutSeparator(State state, FiniteStateAutomaton fsa, FileOutputStream outStream) {
		Interaction interaction = new TraceGenerator().new Interaction();
		State newState = null;
		
		if ((newState=selectInteraction(state, fsa, interaction))==null) return null;
		
		try {
			outStream.write(interaction.getInteraction().getBytes());
		} catch (IOException e) {
			System.out.println("Error: impossible to write the output file");
			//e.printStackTrace();
			System.exit(1);
		}
		return newState;		
	}

	class Interaction {
		private String interaction = null;
		
		public String getInteraction() {
			return interaction;
		}
		public void setInteraction(String interaction) {
			this.interaction = interaction;
		}
	}
	

	private static void closeTrace(FileOutputStream outStream) {
		try {
			outStream.write(traceSeparator.getBytes());
		} catch (IOException e) {
			System.out.println("Error: impossible to write the output file");
			//e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	private static void generateTrace(FiniteStateAutomaton fsa, FileOutputStream outStream) {
		// current state = initial
		State currentState = fsa.getInitialState();
		
		currentState = generateInteractionWithoutSeparator(currentState, fsa, outStream);
		if (currentState == null) {
			closeTrace(outStream);
			return;
		}

		while (currentState != null) {
			currentState = generateInteractionWithSeparator(currentState, fsa, outStream);
		}
		closeTrace(outStream);
		
	}

	
	private static double genRandomValue() {
		return Math.random();
	}

	private static double genRandomValue(int maxval) {
		return Math.random() * maxval;
	}

}