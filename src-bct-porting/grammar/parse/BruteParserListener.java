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
 
package grammar.parse;

import java.util.EventListener;

/**
 * The listener to a brute force parser accepts brute force events
 * generated by a brute force parser.
 * 
 * @author Thomas Finley
 */

public interface BruteParserListener extends EventListener {
    /**
     * A brute parser will call this method when a brute parser's
     * state changes.
     * @param event the brute parse event generated by a parser
     */
    public void bruteParserStateChange(BruteParserEvent event);
}
