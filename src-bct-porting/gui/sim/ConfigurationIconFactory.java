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

import automata.Configuration;
import automata.fsa.FSAConfiguration;
import automata.pda.PDAConfiguration;
import automata.turing.TMConfiguration;

/**
 * This is a configuration icon factory.  Given a configuration, it
 * returns the appropriate icon.
 * 
 * @author Thomas Finley
 */

public class ConfigurationIconFactory {
    /**
     * Returns an instance of an appropriate subclass of the
     * configuration icon for this sort of configuration.
     * @param configuration the configuration to return the icon for
     * @param automaton the automaton this configuration arose from
     * @return some instance of a subclass of
     * <CODE>ConfigurationIcon</CODE>, or <CODE>null</CODE> if this
     * factory is not set up to handle this sort of configuration
     */
    public static ConfigurationIcon iconForConfiguration
	(Configuration configuration) {
	if (configuration instanceof FSAConfiguration)
	    return new FSAConfigurationIcon(configuration);
	else if (configuration instanceof PDAConfiguration)
	    return new PDAConfigurationIcon(configuration);
	else if (configuration instanceof TMConfiguration)
	    return new TMConfigurationIcon(configuration);
	return null;
    }
}
