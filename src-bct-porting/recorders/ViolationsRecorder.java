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
package recorders;

import java.util.HashMap;

import automata.State;
import conf.ConfigurationSettings;



/**
 * This class handles violations 
 * 
 * @author Fabrizio Pastore fabrizio.pastore[AT]gmail.com
 *
 */
public interface ViolationsRecorder {

	public class InteractionViolationType {
		public static final InteractionViolationType illegalTransition = new IllegalTransition();
		public static final InteractionViolationType nonFinalState = new NonFinalState();
		
		private static class IllegalTransition extends InteractionViolationType{
			
		}
		
		private static class NonFinalState extends InteractionViolationType{
			
		}
	}

	public static class IoViolationType {
		public static final IoViolationType enter = new Enter();
		public static final IoViolationType exit = new Exit();
		
		private static class Enter extends IoViolationType{
			
		}
		
		private static class Exit extends IoViolationType{
			
		}
	}
	
	
	
	
	public void init( ConfigurationSettings opts);

	public void recordIoViolationEnter(String signature, String expression, boolean result, Object[] argumentValues, Object returnValue, StackTraceElement[] stElements) throws RecorderException;
	
	public void recordIoViolationExit(String signature, String expression, boolean result, Object[] argumentValues, Object returnValue, StackTraceElement[] stElements, HashMap origValues ) throws RecorderException;
	
	public void recordInteractionViolation(String invokingMethod, String invokedMethod, State[] state, InteractionViolationType type, StackTraceElement[] stElements) throws RecorderException;
	
	
}
