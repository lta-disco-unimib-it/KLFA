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
package it.unimib.disco.lta.alfa.eventsDetection.slct;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;
import it.unimib.disco.lta.alfa.utils.FileUtils;

import org.junit.Ignore;
import org.junit.Test;

public class Bug163 {
	@Test
	public void testRunner() throws SlctRunnerException{
		File fileN = ArtifactsProvider.getBugFile("163-native");
		fileN.mkdir();
		File fileJ = ArtifactsProvider.getBugFile("163-java");
		fileJ.mkdir();
		SlctRunner rj = new SlctRunner(fileJ);
		SlctRunner rn = new SlctRunner(fileN);
		rn.setSlctExecutablePath("/opt/slct/slct");
		
		File file = ArtifactsProvider.getBugFile("163.log");
		List<Pattern> rulesJ = rj.getRules(file);
		
		List<Pattern> rulesN = rn.getRules(file);
		
		
	}
	
	@Ignore
	@Test
	public void testLauncher() throws SlctRunnerException{
		SlctJavaLauncher launcher = new SlctJavaLauncher();
		SlctLauncher slauncher = new SlctNativeLauncher("/opt/slct/slct", true);
		File file = ArtifactsProvider.getBugFile("163.log");
		File outFile = ArtifactsProvider.getBugFile("163.outliers.log");
		File soutFile = ArtifactsProvider.getBugFile("163.soutliers.log");
		int outliersNumber = 0;
		try {
			outliersNumber = FileUtils.getNumberOfLines(file);
		} catch (IOException e1) {
		//	throw new SlctRunnerException(e1);
		}
		int support = (int) (0.05*outliersNumber);
		if ( support == 0 ){
			support = 2;
		}
		
		
		List<Pattern> res = launcher.run(support, file, outFile);
		
		List<Pattern> sres = slauncher.run(support, file, soutFile);
		
		System.out.println(res);
	}
}
