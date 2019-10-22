package it.unimib.disco.lta.alfa.testUtils;

import java.io.File;

public class LogTraceAnalyzerDriverUtil {

	public static String [] createTraceAnalyzerArgsTrainingApplicationLevel(File modelsDir, File transformersFile, 
			File preprocessingRulesFile, File logToProcess) {
		return createTraceAnalyzerArgs(modelsDir, modelsDir, "training", "applicationLevel", transformersFile, preprocessingRulesFile, logToProcess);
	}
	
	public static String [] createTraceAnalyzerArgsCheckingApplicationLevel(File modelsDir,File outputDir, File transformersFile, 
			File preprocessingRulesFile, File logToProcess) {
		return createTraceAnalyzerArgs(outputDir, modelsDir, "checking", "applicationLevel", transformersFile, preprocessingRulesFile, logToProcess);
	}
	
	public static String [] createTraceAnalyzerArgsTrainingComponentLevel(File modelsDir, File transformersFile, 
			File preprocessingRulesFile, File logToProcess) {
		return createTraceAnalyzerArgs(modelsDir, modelsDir, "training", "componentLevel", transformersFile, preprocessingRulesFile, logToProcess);
	}
	
	public static String [] createTraceAnalyzerArgsCheckingComponentLevel(File modelsDir,File outputDir, File transformersFile, 
			File preprocessingRulesFile, File logToProcess) {
		return createTraceAnalyzerArgs(outputDir, modelsDir, "checking", "componentLevel", transformersFile, preprocessingRulesFile, logToProcess);
	}
	
	public static String [] createTraceAnalyzerArgs(File outputDir,File inputDir, String mode, String granularity, File transformersFile, 
			File preprocessingRulesFile, File logToProcess) {
		String args[] = new String[]{ 
				"-separator",
				",",
		"-componentColumn",
		"0",
		"-eventColumn",
		"1",
		"-outputDir",
		outputDir.getAbsolutePath(),
		"-inputDir",
		inputDir.getAbsolutePath(),
		granularity,
		mode,
		transformersFile.getAbsolutePath(),
		preprocessingRulesFile.getAbsolutePath(),
		logToProcess.getAbsolutePath()
		};
		return args;
	}
	
}
