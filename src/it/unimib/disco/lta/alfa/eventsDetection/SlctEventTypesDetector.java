package it.unimib.disco.lta.alfa.eventsDetection;

import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctPattern;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunner;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunnerException;
import it.unimib.disco.lta.alfa.logging.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Pattern;


public class SlctEventTypesDetector {
	private SlctRunner  slctRunner;
	private int rulePosition;
	
	public SlctEventTypesDetector(File workingDir){
		slctRunner = new SlctRunner(workingDir);
	}
	
	/**
	 * Process a log file and idnetify event types in the form of regular expressions
	 * 
	 * @param file
	 * @return
	 * @throws SlctRunnerException
	 */
	public List<SlctPattern> getSlctPatterns(File file) throws SlctRunnerException {
		List<Pattern> rules;
		
		rules = slctRunner.getRules(file);

		 return getSlctPatterns(rules);
	}

	public List<Pattern> getRules(File fileToProcess)
			throws SlctRunnerException {
		return slctRunner.getRules(fileToProcess);
	}

	public String getSlctExecutablePath() {
		return slctRunner.getSlctExecutablePath();
	}

	public double getSupportDecreaseFactor() {
		return slctRunner.getSupportDecreaseFactor();
	}

	public double getSupportPercentage() {
		return slctRunner.getSupportPercentage();
	}

	public File getWorkingDir() {
		return slctRunner.getWorkingDir();
	}

	public boolean isIntersectionEnabled() {
		return slctRunner.isIntersectionEnabled();
	}

	public boolean isIterateEnabled() {
		return slctRunner.isIterateEnabled();
	}

	public void setIntersectionEnabled(boolean intersectionEnabled) {
		slctRunner.setIntersectionEnabled(intersectionEnabled);
	}

	public void setIterateEnabled(boolean iterateEnabled) {
		slctRunner.setIterateEnabled(iterateEnabled);
	}

	public void setSlctExecutablePath(String slctExecutablePath) {
		slctRunner.setSlctExecutablePath(slctExecutablePath);
	}

	public void setSupportDecreaseFactor(double supportDecreaseFactor) {
		slctRunner.setSupportDecreaseFactor(supportDecreaseFactor);
	}

	public void setSupportPercentage(double supportPercentage) {
		slctRunner.setSupportPercentage(supportPercentage);
	}

	public void setWorkingDir(File workingDir) {
		slctRunner.setWorkingDir(workingDir);
	}

	
	
	private List<SlctPattern> getSlctPatterns(List<Pattern> rules) {
		List<SlctPattern> patterns = new ArrayList<SlctPattern>();
		int i = 0;
		for( Pattern p : rules ){
			++i;
			patterns.add(new SlctPattern(getPatternName(),p) );
		}
		return patterns;
	}
	
	/**
	 * Return the rule name associated to the rule in the given position.
	 * The position is the position in the rule array, not in the file (it is the inverse position with respect to the file).
	 * 
	 * @param rulePosition
	 * @param rule
	 * @return
	 */
	private String getPatternName() {
		Formatter f = new Formatter();
		f.format("R%4d", rulePosition++);
		return f.toString().replace(" ", "0"); 
	}
	
}
