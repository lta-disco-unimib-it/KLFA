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
 
package grammar.parse;

import java.util.EventObject;


/**
 * This is an event that is thrown from a brute parser whenever it
 * starts, pauses, accepts, or rejects a string.
 * 
 * @see grammar.parse.BruteParser#addBruteParserListener
 * 
 * @author Thomas Finley
 */

public class BruteParserEvent extends EventObject {
    /**
     * Instantiates a new brute parser event.
     * @param parser the parser that generated this event
     * @param type the event type
     */
    public BruteParserEvent(BruteParser parser, int type) {
	super(parser);
	this.type = type;
    }
    
    /**
     * Returns the brute parser object that generated this event
     * @return the brute parser object 
     */
    public BruteParser getParser() {
	return (BruteParser) super.getSource();
    }

    /**
     * Returns true if this event indicates the start (or resumption)
     * of parsing.
     * @return if this event is of type start
     */
    public boolean isStart() {
	return type == START;
    }
    
    /**
     * Returns true if this event indicates the parser is paused.
     * @return if this event is of type pause
     */
    public boolean isPause() {
	return type == PAUSE;
    }

    /**
     * Returns true if this event indicates a parse was found.
     * @return if this event is of type accept
     */
    public boolean isAccept() {
	return type == ACCEPT;
    }

    /**
     * Returns true if this event indicates the string was rejected.
     * @return if this event is of type reject
     */
    public boolean isReject() {
	return type == REJECT;
    }

    /**
     * Returns the type of event this is.
     */
    public int getType() {
	return type;
    }

    /** The type of event. */
    private int type;
    /** The type of events. */
    public static final int START=0, PAUSE=1, ACCEPT=2, REJECT=3;
}
