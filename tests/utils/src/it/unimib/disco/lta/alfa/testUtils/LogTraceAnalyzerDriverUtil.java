/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
