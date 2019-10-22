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
 
package gui.action;

import grammar.Grammar;
import grammar.UnrestrictedGrammar;
import gui.environment.EnvironmentFrame;
import gui.environment.GrammarEnvironment;
import gui.environment.Universe;
import gui.environment.tag.CriticalTag;
import gui.grammar.parse.BruteParsePane;

import java.awt.event.ActionEvent;

/**
 * This action creates a new brute force parser for the grammar.
 * 
 * @author Thomas Finley
 */

public class BruteParseAction extends GrammarAction {
    /**
     * Instantiates a new <CODE>BruteParseAction</CODE>.
     * @param environment the grammar environment
     */
    public BruteParseAction(GrammarEnvironment environment) {
	super("Brute Force Parse", null);
	this.environment = environment;
	this.frame = Universe.frameForEnvironment(environment);
    }

    /**
     * Performs the action.
     */
    public void actionPerformed(ActionEvent e) {
	Grammar g = environment.getGrammar(UnrestrictedGrammar.class);
	if (g == null) return;
	BruteParsePane bpp = 
	    new BruteParsePane(environment, g);
	environment.add(bpp, "Brute Parser", new CriticalTag() {});
	environment.setActive(bpp);
    }

    /** The grammar environment. */
    private GrammarEnvironment environment;
    /** The frame for the grammar environment. */
    private EnvironmentFrame frame;
}
