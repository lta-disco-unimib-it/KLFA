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
 
package gui.sim;

import java.awt.Component;
import java.awt.Graphics2D;

import automata.Configuration;
import automata.fsa.FSAConfiguration;

/**
 * This is a configuration icon for configurations related to finite
 * state automata.  These sorts of configurations are defined only by
 * the state that the automata is current in, plus the input left.
 * 
 * @author Thomas Finley
 */

public class FSAConfigurationIcon extends ConfigurationIcon {
    /**
     * Instantiates a new <CODE>ConfigurationIcon</CODE>.
     * @param configuration the FSA configuration that is represented
     * @param automaton the FS-automaton that this configuration
     * "arose" from
     */
    public FSAConfigurationIcon(Configuration configuration) {
	super(configuration);
    }

    /**
     * This will paint a sort of "torn tape" object that shows the
     * rest of the input.
     * @param c the component this icon is drawn on
     * @param g the <CODE>Graphics2D</CODE> object to draw on
     * @param width the width the configuration is painted in
     * @param height the height that the configuration is painted in
     */
    public void paintConfiguration(Component c, Graphics2D g,
				   int width, int height) {
	super.paintConfiguration(c, g, width, height);
	FSAConfiguration config = (FSAConfiguration) getConfiguration();
	// Draw the torn tape with the rest of the input.
	Torn.paintString((Graphics2D)g, config.getInput(),
			 RIGHT_STATE.x+5.0f,((float)height)*0.5f, 
			 Torn.MIDDLE,width-RIGHT_STATE.x-5.0f,
			 false, true,
			 config.getInput().length()-
			 config.getUnprocessedInput().length());
    }
}
