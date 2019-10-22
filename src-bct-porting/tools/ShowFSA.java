/*
 * This class implements functionalities for visualizing a serialized FSA.
 * 
 */
package tools;

import gui.environment.EnvironmentFrame;
import gui.environment.FrameFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;

import tools.fsa2xml.LazyFSALoader;
import tools.fsa2xml.LazyFSALoader.LazyFSALoaderException;

import automata.fsa.FiniteStateAutomaton;

public class ShowFSA {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java tools.ShowFSA <fileName>");
			System.out.println("Example: java tools.ShowFSA method().ser");
			System.exit(0);
		}
		
/*		File toBeOpened = new File(args[0]);
		if (!toBeOpened.exists()) {
			System.out.println("Error: File " + toBeOpened.getAbsolutePath() + " does not exist!");
			System.exit(1);
		}
		
	  	ObjectInputStream in;
	  	FiniteStateAutomaton fsa=null;
		try {
			in = new ObjectInputStream(new FileInputStream(toBeOpened.getAbsolutePath()));
			try {
				fsa = (FiniteStateAutomaton)in.readObject();
			} catch (ClassNotFoundException e1) {
				System.out.println("The class corresponding to the deserialized object has not been found");
				e1.printStackTrace();
				System.exit(1);
			}
		} catch (IOException e) {
			System.out.println("Input/Output error while opening " + toBeOpened.getAbsolutePath() + " resource");
			e.printStackTrace();
			System.exit(1);
		}*/

		
	    try {
	    	FiniteStateAutomaton fsa = LazyFSALoader.loadFSA(args[0]);
	    	
			EnvironmentFrame f = FrameFactory.createFrame(fsa);
		} catch (FileNotFoundException e) {
			System.out.println("Error: File " + new File(args[0]).getAbsolutePath() + " does not exist!");
			//e.printStackTrace();
			System.exit(1);
		} catch (LazyFSALoaderException e) {
			System.out.println("Problem loading the FSA "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
	}
}
