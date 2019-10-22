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
