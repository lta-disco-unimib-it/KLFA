/* -- JFLAP 4.0 --
 *
 * Copyright information:
 *
 * Susan H. Rodger, Thomas Finley
 * Computer Science Department
 * Duke University
 * April 24, 2003
 * Supported by National Science Foundation DUE-9752583.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
 
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
