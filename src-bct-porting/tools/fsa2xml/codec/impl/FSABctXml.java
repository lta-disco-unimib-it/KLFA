package tools.fsa2xml.codec.impl;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//import actionsMonitors.ActionData;
import automata.LambdaTransitionChecker;
import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tools.fsa2xml.codec.api.FSACodec;

public class FSABctXml implements FSACodec{
	//Gli atrtributi rappresentano lo stato di un oggetto.
	//Il codec non ha stato, se è invocato due volte di fila non deve ricordarsi cosa ha fatto la volta prima quindi non ha senso che abbia var di stato.
	
	
	
	
	
	public final static FSABctXml INSTANCE = new FSABctXml();


	public static class AutomataLoader{
		private LabelFactory labelFactory = new LabelFactory();
		//private HashMap<Integer,State> keyStatesMapLoad = new HashMap<Integer,State>();
		private ArrayList<State> keyStatesList = new ArrayList<State>();
		private String filename;
		private FiniteStateAutomaton fsa;
		private Integer initialStatePos;
		//This list contains arrays of three elements that represent the transition:
		//1st from, 2nd to, 3 description
		private ArrayList transitionsToSave = new ArrayList();
		
		AutomataLoader( String filename ){
			this.filename = filename;
		}
		
		/**
		 * Load the fsa from the file this loader is associated to
		 * 
		 * @return
		 * @throws IOException 
		 */
		public void loadFSA() throws IOException{
			BufferedReader r = null;
			try{
			fsa  = new FiniteStateAutomaton();
			r = new BufferedReader( new FileReader(filename) );
			
			
			String line;
			while ( ( line = r.readLine() ) != null ){
				line = line.trim();
				
				if ( isState( line ) ){
					loadAndSetState( line );
				} else if ( isTransition( line ) ) {
					loadTransition( line );
				} else if ( isFSA(line) ){
					loadFSAElement(line);
				}
			}
			
			setTransitions();
			
			setInitialState();
			
			} finally {
				if ( r != null )
					r.close();
			}
		}
		
		/**
		 * Set the initial state in the automaton
		 * 
		 */
		private void setInitialState() {
			fsa.setInitialState(keyStatesList.get(initialStatePos));
		}

		/**
		 * Add the transitions to the automaton
		 * 
		 */
		private void setTransitions() {
			for ( Object o : transitionsToSave ){
				Object transitionElements[] = (Object[]) o;
				
				String desc;
				if ( isLambda((String) transitionElements[2] ) ){
					desc = "";
				} else {
					desc = (String) transitionElements[2];
				}
				
				Transition t = new FSATransition((State)keyStatesList.get((Integer) transitionElements[0]),(State)keyStatesList.get((Integer)transitionElements[1]),desc);
				//Transition t = new FSATransition((State)keyStatesMapLoad.get(transitionElements[0]),(State)keyStatesMapLoad.get(transitionElements[1]),desc);
				
				fsa.addTransition(t);
			}
		}

		private boolean isLambda(String object) {
			return object.equals("\u03BB");
		}

		/**
		 * Load an element of type FSA
		 * 
		 * @param line
		 */
		private void loadFSAElement(String line) {
			String[] elements = line.split("\"");
			for ( int i = 0; i < elements.length; ++i ){
				if ( elements[i].endsWith("initialState=") ){
					initialStatePos = Integer.valueOf(elements[i+1].substring(10));
					break;
				}
			}
				
		}

		private boolean isFSA(String line) {
			return line.startsWith("<fsa:FSA");
		}

		/**
		 * parse the given line and load the transition it represents
		 * 
		 * @param line
		 */
		private void loadTransition(String line) {
			String[] s2 = line.split("\"");		
			String from = null;
			String to = null;
			String descr = null;
			
			for ( int i = 0; i < s2.length; ++i ){
				
				if ( s2[i].endsWith("to=") ){
					to = s2[++i];
				} else if ( s2[i].endsWith("from=") ){
					from = s2[++i];
				} else if ( s2[i].endsWith("description=") ){
					descr = getUnFormattedDescription(s2[++i], labelFactory);
				}
			}
			
			int idFrom = Integer.parseInt(from.substring(10, from.length()));	
			int idTo = Integer.parseInt(to.substring(10, to.length()));
			//System.out.println(idFrom+" "+idTo+" "+descr);
			transitionsToSave.add(new Object[]{idFrom,idTo,descr} );
			
			
		}

		/**
		 * Read the given line and create in the automaton th estate it represent
		 * 
		 * @param line
		 */
		private void loadAndSetState(String line) {
			line=getUnFormattedDescription(line, labelFactory);
			String[] s = line.split("\"");
			State state = fsa.createState(new Point(0,0));
			state.setName(s[1]);
			
			if ( s[2].trim().equals("final=") ){
				if ( s[3].equals("true") ){
					fsa.addFinalState(state);
				}
			}
			
			keyStatesList.add(state);
			//keyStatesMapLoad.put(keyStatesMapLoad.size(),state);
		}
		
		/**
		 * Return the automaton that corresponds to the file
		 * 
		 * @return
		 * @throws IOException
		 */
		public FiniteStateAutomaton getFSA() throws IOException{
			if ( fsa == null ){
				loadFSA();
			}
			//System.out.println(fsa.toString());
			//System.out.println("Initial "+fsa.getInitialState());
			return fsa;
		}
	}
	
	/**
	 * Load the automaton given its path
	 * 
	 */
	public FiniteStateAutomaton loadFSA(String filename) throws IOException,
	ClassNotFoundException {
		AutomataLoader loader = new AutomataLoader(filename);
		return loader.getFSA();

	}

	

	/**
	 * This method return whether or not a line represent a transition
	 * @param line
	 * @return
	 */
	private static boolean isTransition(String line) {
		return line.startsWith("<transitions ");
	}

	/**
	 * This method returns whether or not a line describe a state
	 * 
	 * @param line
	 * @return
	 */
	private static boolean isState(String line) {
		return line.startsWith("<states ");
	}





	public FiniteStateAutomaton loadFSA(File file) throws IOException,
	ClassNotFoundException {

		return loadFSA(file.getAbsolutePath());
	}
	
	
	
	/**
	 * Save the passed FSA to the given filename
	 * 
	 */
	public void saveFSA(FiniteStateAutomaton fsa, String filename)
	throws FileNotFoundException, IOException {

		File file = new File(filename);

		//Build  a map in which for every state we associate a position number
		
		HashMap<State, Integer> statesMap = new HashMap<State,Integer>();
		State states[] = fsa.getStates();
		for ( int i = 0; i< states.length; ++i ){
			statesMap.put(states[i],i);
		}

		//Write down states and transitions
		
		BufferedWriter w = null;
		try{
			w = new BufferedWriter( new FileWriter(file) );

			writeHeader(fsa,w,statesMap);

			writeStates(fsa,w,statesMap);

			writeTransition(fsa,w,statesMap);

			writeFooter(fsa,w,statesMap);

			w.close();
		} finally { 
			if ( w != null ){
				w.close();
			}	
		}
	}



	private void writeFooter(FiniteStateAutomaton fsa, BufferedWriter w, HashMap<State, Integer> statesMap) throws IOException {
		w.write("</fsa:FSA>");
		
	}



	private void writeTransition(FiniteStateAutomaton fsa, BufferedWriter w, HashMap<State, Integer> statesMap) throws IOException {

		if(fsa.getTransitions()!=null){
			for ( Transition t : fsa.getTransitions() ){
				w.write("<transitions ");
				if(t.getDescription()!=null)
					w.write("description=\""+getFormattedDescription(t.getDescription())+"\" ");	
				w.write("to=\"//@states."+statesMap.get(t.getToState())+"\"");
				w.write(" from=\"//@states."+statesMap.get(t.getFromState())+"\"");
				w.write("/>");
				w.write("\n");

			}
		}

	}




	/**
	 * This method replace special characters in description fields in order to be able to save them in xml
	 *
	 *  
	 *  Replaces < with &lt;
	 *  
	 * @param description
	 * @return
	 */
	private String getFormattedDescription(String description) {
		return description.replace("<", "&lt;").replace("\"", "&quot;");
	}


	/**
	 * This method replace get a description formatted for xml saving and return it in original format
	 *  
	 *  Replaces &lt; with <
	 *  
	 *  
	 * @param description
	 * @param labelFactory 
	 * @return
	 */
	private static String getUnFormattedDescription(String description, LabelFactory labelFactory) {
		
		description = description.replace("&lt;","<").replace("&quot;", "\"");
		return labelFactory.getLabel(description);
		
	}

	/**
	 * Write the states in the order defined by the map
	 * 
	 * @param fsa
	 * @param w
	 * @param statesMap
	 * @throws IOException
	 */
	private void writeStates(FiniteStateAutomaton fsa, BufferedWriter w, HashMap<State, Integer> statesMap) throws IOException {
		
		State states[] = new State[statesMap.size()];
		for ( State state : statesMap.keySet() ){
			Integer pos = statesMap.get(state);
			states[pos] = state;
		}

		for ( State state : states ){
			
			w.write("<states name=\""+getFormattedDescription(state.getName())+"\" ");
			if ( fsa.isFinalState(state) ){
				w.write("final=\"true\" "); 
			}
			w.write("fsa=\"/\"");
			w.write("/>");
			w.write("\n");
		}
	}







	/**
	 * Write the header
	 * 
	 * @param fsa
	 * @param w
	 * @param statesMap
	 * @throws IOException
	 */
	private void writeHeader(FiniteStateAutomaton fsa, BufferedWriter w, HashMap<State, Integer> statesMap) throws IOException {

		w.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		w.write("\n");
		w.write("<fsa:FSA xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:fsa=\"fsa\""); 
		if ( fsa.getInitialState()!=null ){
			w.write(" initialState=\"//@states."+statesMap.get(fsa.getInitialState())+"\"");
		}
		w.write(">\n");

	}





	public void saveFSA(FiniteStateAutomaton o, File file)
	throws FileNotFoundException, IOException {

		saveFSA(o, file.getAbsolutePath());
	}


}
