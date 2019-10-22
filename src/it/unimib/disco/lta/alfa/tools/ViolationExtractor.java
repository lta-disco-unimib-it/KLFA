package it.unimib.disco.lta.alfa.tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import automata.State;
import automata.Transition;
import automata.fsa.FiniteStateAutomaton;

/**
 * This program parse a KBehavior log file and detects all the additions.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ViolationExtractor {

	public static  class Violation{
		private String state;
		private int lineN;
		private String event;
		private String comment = "";

		public Violation( String state, int lineN, String event ){
			this.state = state;
			this.lineN = lineN;
			this.event = event;
		}

		public void addComment(String comment) {
			this.comment  = comment;
		}
		
		public void setState( String value ){
			state = value;
		}
		
		public void setLine( int value ){
			lineN = value;
		}
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( args.length < 2 ){
			printUsage();
			System.exit(-1);
		}
		boolean tail = false;
		StringBuffer tailAdded = new StringBuffer();
		File log = new File(args[0]);
		File trace = new File(args[1]);
		FiniteStateAutomaton fsa = null;
		String traceFile = null;
		if ( args.length > 2 ){
			try {
				fsa = FiniteStateAutomaton.readSerializedFSA(args[2]);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if ( args.length > 3 ){
			traceFile = args[3];
		}
		ArrayList<Violation> viols = new ArrayList<Violation>();
		int k = 5;
		try {
			BufferedReader logReader = new BufferedReader( new FileReader(log));
			
			BufferedReader traceReader = new BufferedReader( new FileReader(trace));
			String line;
			ArrayList<String> lines = new ArrayList(); 
			while ( ( line = traceReader.readLine() ) != null )
				lines.add(line);
			traceReader.close();
			Violation lastViol = null ;
			Violation incomplViol = null ;
			while ( ( line = logReader.readLine() ) != null ){
				
				if ( line.startsWith("3) Adding Branch")){
					 Integer lineN = Integer.valueOf( line.substring( Integer.valueOf( line.indexOf("actualPos") ) + 9 ) );
					 String event = lines.get(lineN);
					 int sindex = line.indexOf("actualState =") + 14;
					 String state = line.substring( sindex , line.indexOf(" at (")  );
					 lastViol = new Violation(state, lineN, event );
					 viols.add( lastViol );
				} else if ( line.startsWith("3) Recursion on")){
					if ( lastViol != null ){
						String comment = line.substring(21);
						lastViol.addComment( comment );
					} else {
						incomplViol = new Violation( "", 0, "");
						String comment = line.substring(21);
						incomplViol.addComment( comment );
						System.out.println(line);
					}
				} else if ( line.startsWith("3) cursors updates;" ) ) {
					if ( incomplViol!=null){
						String[] els = line.split(" ");
						
						incomplViol.setState(els[5].split("=")[1]);
						incomplViol.setLine(Integer.valueOf(els[10].split("=")[1]));
						viols.add(incomplViol);
						incomplViol = null;
					}
				} else if ( line.startsWith("3) No Suitable Behavioral") ){
					tail = true;
				} else if ( tail ){
					if ( line.startsWith("3) Transition") ){
						String[] els = line.split("\"");
						tailAdded.append(els[1]);
					}
				}
			}
			if (tail ){
				Violation v = new Violation( "", -1,"TAIL" );
				v.addComment(tailAdded.toString());
				viols.add(v);
			}
			System.out.println( "Violations occurred" );
			System.out.println( "State\tLine\tEvent" );
			HashSet<String> nextE = new HashSet<String>();
			
			for ( Violation viol : viols ){
				System.out.println( viol.state +"\t"+viol.lineN+"\t"+viol.event+"\t\t\t"+viol.comment );
				if ( fsa != null ){
					System.out.println("Expecting FSA:");
					String sname = viol.state.replace("_", "");
					for ( State state : fsa.getStates() ){
						
						if( state.getName().equals(sname) ){
							List<String> paths = getPath(fsa,state,3);
							for ( String path : paths ){
								System.out.println(path);
								nextE.add(path.split(",")[0].trim());
							}
							
						}
					}
				}
				
				System.out.println("Expecting TRACE:");
				HashMap<String,String> results = new HashMap<String,String>();
				if ( traceFile != null && viol.lineN > 0 ){
					String pattern = getPattern(trace,k,viol.lineN);
					
					FileMatcher fm = new FileMatcher();
					ArrayList<FileMatcherResult> matches = fm.find(pattern, new File(traceFile));
					
					for ( FileMatcherResult result : matches ){
						String ts = result.getContent().replace("\n", " , ");
						
						String tslines = results.get(ts);
						if ( tslines == null ){
							tslines = "";
						}
						tslines += ","+result.getStartLine();
						results.put(ts,tslines );
					}
					
					for ( String result : results.keySet() ){
						System.out.println(result);
					}
				}
				System.out.println("Missing:");
				for ( String result : results.keySet() ){
					if ( result.contains(viol.event))
						System.out.println(result);
				}
				
				System.out.println("Best:");
				HashMap<String,Integer> best = new HashMap<String,Integer>();
				
				for ( String result : results.keySet() ){
					String res = result.split(",")[k+1].trim();
					if ( nextE.contains(res)){
						Integer n = best.get(res);
						if ( n == null ){
							n = 0;
						}
						n++;
						best.put(res, n);
					}
				}
				
				for ( String r : best.keySet() ){
					System.out.println(r+" : "+best.get(r));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getPattern(File trace, int k, int lineN) {
		String regexp="";
		try {
			BufferedReader reader = new BufferedReader( new FileReader(trace));
			String line;
			int count=0;
			int start = lineN-k+1;
			
			while ( ( line = reader.readLine() ) != null ){
				++count;
				String pattern;
				if ( count >= start && count <= lineN ){
					pattern = line.split(":")[0].replace(")", "\\)");
					regexp+="\n"+pattern;
				}
			}
			regexp+="\n.*\n.*\n.*\n.*\n.*\n.*\n";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return regexp;
	}

	private static List<String> getPath(FiniteStateAutomaton fsa, State state, int i) {
		return getPath(fsa,state,i,new HashSet<Transition>());
	}
	
	private static List<String> getPath(FiniteStateAutomaton fsa, State state, int i, HashSet<Transition> visitedTransitions) {
		Transition[] transitions = fsa.getTransitionsFromState(state);
		Stack<State> level1= new Stack<State>();
		level1.push(state);
		
		ArrayList<Transition> levelTransitions = new ArrayList<Transition>();
		while(level1.size()>0){
			State curState = level1.pop();
			transitions = fsa.getTransitionsFromState(curState);
			for ( Transition transition : transitions ){
				if ( visitedTransitions.contains(transition) )
					continue;
				visitedTransitions.add(transition);
				if ( isEpsilon( transition ) ){
					level1.push(transition.getToState());
				} else {
					levelTransitions.add(transition);
				}
			}
		}

		ArrayList<String> result = new ArrayList<String>();
		if ( i > 0 ){
			for ( Transition transition : levelTransitions ){
				for ( String child : getPath(fsa,transition.getToState(),i-1,visitedTransitions)){
					result.add(transition.getDescription()+" , "+child);
				}
			}
		} else {
			for ( Transition transition : levelTransitions ){
				result.add(transition.getDescription());
			}
		}
		return result;
	}

	private static boolean isEpsilon(Transition transition) {
		return transition.getDescription().length() < 3;
	}

	static void printUsage(){
		System.err.println("This program printout correlations between branch additions in KBehavior and" +
				"information on trace file.");
		System.err.println("Usage : "+ViolationExtractor.class.getSimpleName()+" <extensionTraceLog> <normalizedTrace>");
		System.err.println("<extensionTraceLog> \tLog file with branch additions");
		System.err.println("<normalizedLog> \tFile with the trace (must be in column)");
	}
}
