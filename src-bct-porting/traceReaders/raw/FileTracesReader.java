package traceReaders.raw;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tools.TraceRepository;
import traceReaders.TraceReaderException;
import util.FileIndex;


import conf.BctSettingsException;
import conf.EnvironmentalSetter;
import conf.InvariantGeneratorSettings;
import conf.BCTFileFilter;
import conf.TraceReaderSettings;

/**
 * Trace reader that works on file
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class FileTracesReader implements TracesReader {
	private File ioTracesDir;
	private File interactionTracesDir;
	private FileIoTraceRepository ioRepository;
	
	public interface Options {
		public final String tracesPath = "traceReader.tracesPath";
		public final String ioTracesDirName = "traceReader.ioTracesDirName";
		public final String interactionTracesDirName = "traceReader.interactionTracesDirName";
	}
	
	public Iterator<IoTrace> getIoTraces() throws FileReaderException {
//		File[] srcFiles = ioTracesDir.listFiles(dtraceFilter);
//		ArrayList<IoTrace> al = new ArrayList<IoTrace>(srcFiles.length);
//		
//		for (int i = 0; i < srcFiles.length; i++) {
//			al.add(getIoTrace( srcFiles[i] ));
//		}
		List<IoTrace> al = ioRepository.getRawTraces();
		return al.iterator();
	}

	public FileIoTrace getIoTrace(String methodName, String fileName) {
		File traceFile = new File(ioTracesDir,fileName+".dtrace");
		return getIoTrace(methodName, traceFile);
	}
	/**
	 * Get the IoTrace object from the passed trace 
	 * @param file
	 * @return
	 */
	public FileIoTrace getIoTrace(String methodName, File file) {
		String fileName = file.getName();
		
		String methName = fileName.substring(0,fileName.length()-7);
		
		File metaFile = new File( ioTracesDir, methName+".meta");
		if ( ! metaFile.exists() )
			metaFile = null;
		return new FileIoTrace(methodName,file,metaFile);
	
	}

	public void init(InvariantGeneratorSettings trs) throws BctSettingsException {
		String dir = trs.getProperty(Options.tracesPath);
		if ( dir == null ){
			throw new BctSettingsException("parameter "+Options.tracesPath+" not properly set");
		}
		
		String ioDir = trs.getProperty(Options.ioTracesDirName);
		if ( dir == null ){
			throw new BctSettingsException("parameter "+Options.ioTracesDirName+" not properly set");
		}
		
		String intDir = trs.getProperty(Options.interactionTracesDirName);
		if ( dir == null ){
			throw new BctSettingsException("parameter "+Options.interactionTracesDirName+" not properly set");
		}
		
		ioTracesDir = new File(dir+File.separator+ioDir);
		if ( ! ioTracesDir.exists() || ! ioTracesDir.isDirectory() )
			throw new BctSettingsException("directory does not exists "+ioTracesDir);
		
		interactionTracesDir = new File(dir+File.separator+intDir);
		if ( ! interactionTracesDir.exists() || ! interactionTracesDir.isDirectory() )
			throw new BctSettingsException("directory does not exists "+interactionTracesDir);
		
		ioRepository = new FileIoTraceRepository(ioTracesDir);
		
	}

	public Iterator<InteractionTrace> getInteractionTraces() {
		File[] srcFiles = interactionTracesDir.listFiles(EnvironmentalSetter.getInteractionTraceFilter());
		ArrayList<InteractionTrace> al = new ArrayList<InteractionTrace>(srcFiles.length);
		for (int i = 0; i < srcFiles.length; i++) {
				
			File meta = new File ( interactionTracesDir, srcFiles[i].getName().substring(0, srcFiles[i].getName().length()-4)+".meta" );
			//System.out.println(meta.getName()+" "+meta.exists());
			if ( ! meta.exists() )
				meta = null;
			
			al.add(new FileInteractionTrace(srcFiles[i].getName().substring(16, srcFiles[i].getName().length()-4),srcFiles[i], meta));
		}
		
		
		return al.iterator();
	}

	public IoTrace getIoTrace(String methodName) throws TraceReaderException {
		return ioRepository.getRawTrace(methodName);
		
	}

}
