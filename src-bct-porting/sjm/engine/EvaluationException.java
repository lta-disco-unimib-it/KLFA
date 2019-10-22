package sjm.engine;

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

/**
 * Signals that an ArithmeticOperator could not be evaluated.
 * 
 * This happens, for example, when an evaluation refers to
 * an uninstantiated variable.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class EvaluationException extends RuntimeException {
/**
 * Constructs an EvaluationException with no detail message.
 *
 */
public EvaluationException() {
	super();
}
/**
 * Constructs an EvaluationException with the specified detail 
 * message. 
 *
 * @param   detail   the detail message
 */
public EvaluationException(String detail) {
	super(detail);
}
}
