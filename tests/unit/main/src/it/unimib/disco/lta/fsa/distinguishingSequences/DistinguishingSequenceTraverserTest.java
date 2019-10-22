package it.unimib.disco.lta.fsa.distinguishingSequences;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

public class DistinguishingSequenceTraverserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddSymbol() {

FiniteStateAutomaton fsa = new FiniteStateAutomaton();
		
		
		State state1 = fsa.createState(new Point(0,0));
		State state2 = fsa.createState(new Point(0,0));
		State state3 = fsa.createState(new Point(0,0));
		State state4 = fsa.createState(new Point(0,0));
		State state5 = fsa.createState(new Point(0,0));
		State state6 = fsa.createState(new Point(0,0));
		State state7 = fsa.createState(new Point(0,0));
		State state8 = fsa.createState(new Point(0,0));
		
		
		Transition t = new FSATransition(state1,state2,"A");
		fsa.addTransition(t);
		t = new FSATransition(state2,state3,"B");
		fsa.addTransition(t);
		t = new FSATransition(state3,state4,"C");
		fsa.addTransition(t);
		t = new FSATransition(state4,state5,"D");
		fsa.addTransition(t);
		t = new FSATransition(state5,state6,"A");
		fsa.addTransition(t);
		t = new FSATransition(state6,state7,"B");
		fsa.addTransition(t);
		t = new FSATransition(state1,state8,"E");
		fsa.addTransition(t);
		t = new FSATransition(state8,state3,"C");
		fsa.addTransition(t);
		
		
		
		
		fsa.setInitialState(state1);
		fsa.addFinalState(state7);
		
		
		
		ExtendedDiagnosingTree dt = new ExtendedDiagnosingTree(fsa);
		List<DistinguishingSequence<String>> sequences = dt.getDistinguishingSequences();
		
		for ( DistinguishingSequence<String> sequence : sequences ){
			System.out.println(sequence.toString());
		}

		DistinguishingSequenceTraverser tr = new DistinguishingSequenceTraverser(dt);
		assertNull(tr.addSymbol("A"));
		assertNull(tr.addSymbol("B"));
		assertEquals(state4,tr.addSymbol("C"));
		
		tr.clear();
		
		assertNull(tr.addSymbol("A"));
		assertNull(tr.addSymbol("C"));
		assertEquals(state4,tr.addSymbol("C"));
		
		assertNull(tr.addSymbol("G"));
		assertNull(tr.addSymbol("A"));
		assertEquals(state5,tr.addSymbol("D"));
		
	}

}
