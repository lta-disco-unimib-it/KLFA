package it.unimib.disco.lta.alfa.eventsDetection.slct;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public interface SlctLauncher {

	public abstract List<Pattern> run(int support, File inputFile,
			File outliersFile) throws SlctRunnerException;

}