package it.unimib.disco.lta.fsa.distinguishingSequences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import tools.fsa2xml.LazyFSALoader;
import tools.fsa2xml.LazyFSALoader.LazyFSALoaderException;

import automata.fsa.FiniteStateAutomaton;

public class DistinguishingSequencesGenerator {

	public static class DistinguishingSequencesGeneratorResult {
		private ExtendedDiagnosingTree tree;
		private List<DistinguishingSequence<String>> sequences;
		
		public ExtendedDiagnosingTree getTree() {
			return tree;
		}

		public void setTree(ExtendedDiagnosingTree tree) {
			this.tree = tree;
		}

		public List<DistinguishingSequence<String>> getSequences() {
			return sequences;
		}

		public void setSequences(List<DistinguishingSequence<String>> sequences) {
			this.sequences = sequences;
		}

		public DistinguishingSequencesGeneratorResult(
				ExtendedDiagnosingTree tree,
				List<DistinguishingSequence<String>> sequences) {
			super();
			this.tree = tree;
			this.sequences = sequences;
		}
		
		
	}
	
	public DistinguishingSequencesGeneratorResult identifyDistinguishingSequences( FiniteStateAutomaton fsa ){
		ExtendedDiagnosingTree tree = new ExtendedDiagnosingTree(fsa);
		List<DistinguishingSequence<String>> sequences = tree.getDistinguishingSequences();
		return new DistinguishingSequencesGeneratorResult(tree,sequences);
	}
	
	public static void main ( String[] args ){
		File automaton = new File(args[0]);
		
		try {
			FiniteStateAutomaton fsa = LazyFSALoader.loadFSA(automaton.getAbsolutePath());
			DistinguishingSequencesGenerator g = new DistinguishingSequencesGenerator();
			DistinguishingSequencesGeneratorResult res = g.identifyDistinguishingSequences(fsa);
			
			res.tree.store(new File(automaton.getParent(),automaton.getName()+".dtree"));
			
			System.out.println("Sequences : ");
			for ( DistinguishingSequence<String> sequence : res.sequences ){
				System.out.println(sequence);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LazyFSALoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
