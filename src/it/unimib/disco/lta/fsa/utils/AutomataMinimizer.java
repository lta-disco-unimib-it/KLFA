package it.unimib.disco.lta.fsa.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;

import tools.fsa2xml.LazyFSALoader;
import tools.fsa2xml.LazyFSALoader.LazyFSALoaderException;
import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.factory.FSA2FileFactory;
import tools.fsa2xml.codec.factory.FSA2FileFactoryException;

import automata.Automaton;
import automata.fsa.FiniteStateAutomaton;
import automata.fsa.Minimizer;
import automata.fsa.NFAToDFA;

public class AutomataMinimizer {
	
	public FiniteStateAutomaton minimizeFSA(FiniteStateAutomaton currentFSA) {
		/*
		 * The minimizer is able to minimize only DFA, therefore we need to transform the
		 * currentFSA into a DFA. 
		 */
		
		 NFAToDFA converter = new NFAToDFA();
		 currentFSA=converter.convertToDFA(currentFSA);
		
		 
		 
		 Minimizer minimizer = new Minimizer();
		 minimizer.initializeMinimizer();
		 Automaton tmpAutomaton = minimizer.getMinimizeableAutomaton(currentFSA);
	
		 DefaultTreeModel dtm = minimizer.getDistinguishableGroupsTree(tmpAutomaton);
		
/*		EngineConfig.logger.logInfo("Tree Model Built: " + dtm);
		 while (dtm != null) {
			EngineConfig.logger.logInfo("Begin Splitting States");
			ArrayList groups = minimizer.split(tmpAutomaton.getStates(),tmpAutomaton, dtm);
			EngineConfig.logger.logInfo("States Splitted");
			
			dtm = minimizer.getDistinguishableGroupsTree(tmpAutomaton);
			EngineConfig.logger.logInfo("Tree Model Built: " + dtm);
		 }*/
		 
		
		 return (FiniteStateAutomaton) minimizer.getMinimumDfa(tmpAutomaton,dtm);
	}
	
	
	public static void main ( String args[] ){
		String outputFormat = null;
		ArrayList<File> files = new ArrayList<File>();
		
		for ( int i = 0 ; i < args.length; i++ ){
			String arg = args[i];
			
			if ( arg.equals("--outputFormat") ){
				outputFormat=args[++i];
			} else {
				files.add(new File(args[i]));
			}
			
		}
		
		for ( File file : files ){
			minimizeAndSave(file,outputFormat);
		}
	}
	
	public static void minimizeAndSave ( File automatonFile, String outputFormat){
		
		
		LazyFSALoader fsaLoader = new LazyFSALoader();
		try {
			FiniteStateAutomaton nfa = fsaLoader.loadFSA(automatonFile.getAbsolutePath());
			
			AutomataMinimizer minimizer = new AutomataMinimizer();
			FiniteStateAutomaton dfa = minimizer.minimizeFSA(nfa);
			
			int dotPosition = automatonFile.getName().lastIndexOf('.');
			
			if ( outputFormat == null ){
				outputFormat = automatonFile.getName().substring(dotPosition+1);
			}
			
			FSACodec codec = FSA2FileFactory.getCodecForFileExtension(outputFormat);
			
			String finalName = automatonFile.getName().substring(0,dotPosition)+".minimized."+outputFormat;
			codec.saveFSA( dfa, new File ( automatonFile.getParentFile(), finalName ) );
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LazyFSALoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FSA2FileFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
