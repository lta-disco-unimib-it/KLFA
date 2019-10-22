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

package file;

/**
 * This error indicates elements in a file are arranged in such a
 * fashion as to prevent the interpreter from working correctly.  This
 * should be thrown by {@link file.Decoder} implementing objects to
 * indicate a problem processing the file.
 * 
 * @author Thomas Finley
 */

public class ParseException extends RuntimeException {
    /**
     * Creates a generic parse exception.
     */
    public ParseException() {
	super();
    }
    
    /**
     * Creates a parse exception with the given message.
     * @param message the exception message
     */
    public ParseException(String message) {
	super(message);
    }
}
