package sjm.imperative;

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
 * This abstract class represents a hierarchy of classes
 * that encapsulate commands. A command object is a request
 * that is dormant until a caller asks it to execute.
 * <p>
 * Subclasses typically encapsulate some primary function, 
 * and allow for parameters that tailor a command to a 
 * purpose. All subclasses must implement an <code>execute
 * </code> command, which is abstract here.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public abstract class Command {
/**
 * Perform the request encapsulated in this command.
 */
public abstract void execute();
}
