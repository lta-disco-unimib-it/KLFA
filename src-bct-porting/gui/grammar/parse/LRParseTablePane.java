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
 
package gui.grammar.parse;

import grammar.parse.LRParseTable;
import gui.LeftTable;

/**
 * This holds a LR parse table.
 * 
 * @author Thomas Finley
 */

public class LRParseTablePane extends LeftTable {
    /**
     * Instantiates a new parse table pane for a parse table.
     * @param table the table pane's parse table
     */
    public LRParseTablePane(LRParseTable table) {
	super(table);
	this.table = table;
	setCellSelectionEnabled(true);
    }

    /**
     * Retrieves the parse table in this pane.
     * @return the parse table in this pane
     */
    public LRParseTable getParseTable() {
	return table;
    }

    /** The parse table for this pane. */
    private LRParseTable table;
}
