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
