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


import it.unimib.disco.lta.alfa.inferenceEngines.Bug131;
import it.unimib.disco.lta.alfa.inferenceEngines.Bug132;
import it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis.Bug104;
import it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis.Bug105;
import it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis.Bug106;
import it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis.Bug107;
import it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis.Bug108;
import it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis.Bug109;
import it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis.Bug110;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for bugs");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(Bug131.class);
		suite.addTestSuite(Bug132.class);
		
		suite.addTestSuite(Bug109.class);
		suite.addTestSuite(Bug107.class);
		suite.addTestSuite(Bug104.class);
		suite.addTestSuite(Bug110.class);
		suite.addTestSuite(Bug105.class);
		suite.addTestSuite(Bug108.class);
		suite.addTestSuite(Bug106.class);
		//$JUnit-END$
		return suite;
	}

}
