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
 
package automata;


/**
 * The alphabet retriever object can be used to find the alphabet
 * for a given automaton.  
 *
 * @author Ryan Cavalcante
 */

public abstract class AlphabetRetriever {
    /**
     * Instantiates an <CODE>AlphabetRetriever</CODE> object.
     */
    public AlphabetRetriever() {

    }

    /**
     * Returns the alphabet for <CODE>automaton</CODE> in an
     * array of strings.
     * @param automaton the automaton.
     * @return the alphabet.
     */
    public abstract String[] getAlphabet(Automaton automaton);
}
