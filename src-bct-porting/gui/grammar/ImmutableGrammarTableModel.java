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
 
package gui.grammar;

import grammar.Grammar;

/**
 * The <CODE>ImmutableGrammarTableModel</CODE> is a grammar table
 * model that cannot be changed.
 * 
 * @see grammar.Production
 * 
 * @author Thomas Finley
 */

public class ImmutableGrammarTableModel extends GrammarTableModel {
    /**
     * Instantiates a <CODE>GrammarTableModel</CODE>.
     */
    public ImmutableGrammarTableModel() {
	super();
    }
    
    /**
     * Instantiates a <CODE>GrammarTableModel</CODE>.
     * @param grammar the grammar to have for the grammar table model
     * initialized to
     */
    public ImmutableGrammarTableModel(Grammar grammar) {
	super(grammar);
    }

    /**
     * No cell is editable in this model.
     * @param row the index for the row
     * @param column the index for the column
     * @return <CODE>false</CODE> always
     */
    public boolean isCellEditable(int row, int column) {
	return false;
    }
}
