package dfmaker.utilities;

import java.util.Collection;
import java.util.LinkedList;

import traceReaders.raw.IoTrace;
import traceReaders.raw.TraceException;
import traceReaders.raw.IoTrace.LineIterator;

import dfmaker.core.DaikonReader;
import dfmaker.core.ProgramPointHash;
import dfmaker.core.ProgramPointHash.Type;

public class ProgramPointHashExtractor {

	public static Collection<ProgramPointHash> getProgramPointsHashes(IoTrace trace) throws TraceException {
		LinkedList<ProgramPointHash> result = new LinkedList<ProgramPointHash>();
		
		LineIterator lineIt = trace.getLineIterator();
		long startLine = 0;
		boolean insidePp = false;
		Type type = null;
		StringBuffer buffer = new StringBuffer();
		
		while ( lineIt.hasNext() ){
			String line = lineIt.next();
			
			if ( insidePp ){
				if ( line.length() == 0 ){
					insidePp = false;
					addProgramPoint(false,lineIt, startLine, buffer, type, result);
//					long len = lineIt.getCurrentLineNumber() - startLine;
//					int hash = buffer.toString().hashCode();
//					ProgramPointHash pp = new ProgramPointHash(type,hash,startLine,len);
//					result.add(pp);
					buffer = new StringBuffer();
				} else {
					buffer.append(line);
					buffer.append("\n");
				}
			} else {
				if ( line.length() > 0 && !insidePp){
					
					if ( DaikonReader.isEntryPoint(line) ){
						type = ProgramPointHash.Type.ENTRY;
					} else {
						type = ProgramPointHash.Type.EXIT;
					}
					startLine = lineIt.getCurrentLineNumber();
					insidePp = true;
					
					buffer.append(line);
					buffer.append("\n");
				}
			}
			
			
		}
		if ( insidePp )
			addProgramPoint(true,lineIt, startLine, buffer, type, result);
		
		return result;
		
		
	}

	private static void addProgramPoint(boolean isLast, LineIterator lineIt, long startLine, Object buffer, Type type, Collection<ProgramPointHash> result) {
		
		long len = lineIt.getCurrentLineNumber() - startLine;
		if ( isLast )
			++len;
		int hash = buffer.toString().hashCode();
		ProgramPointHash pp = new ProgramPointHash(type,hash,startLine,len);
		result.add(pp);
		
	}

}

