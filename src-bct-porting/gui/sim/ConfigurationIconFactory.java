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
