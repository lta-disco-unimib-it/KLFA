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

package file.xml;

import java.io.Serializable;

import org.w3c.dom.Document;

import file.ParseException;

/**
 * This is an interface for objects that serve as a go between from
 * DOM to a JFLAP object representing a structure (such as an
 * automaton or grammar), and back again.
 * 
 * @author Thomas Finley
 */

public interface Transducer {
    /**
     * Given a document, this will return the corresponding JFLAP
     * structure encoded in the DOM document.
     * @param document the DOM document to decode
     * @return a serializable object, as all JFLAP structures are
     * encoded in serializable objects
     * @throws ParseException in the event of an error that may lead
     * to undesirable functionality
     */
    public Serializable fromDOM(Document document);

    /**
     * Given a JFLAP structure, this will return the corresponding DOM
     * encoding of the structure.
     * @param structure the JFLAP structure to encode
     * @return a DOM document instance
     */
    public Document toDOM(Serializable structure);

    /**
     * Returns the string encoding of the type this transducer decodes
     * and encodes.
     * @return the type this transducer recognizes
     */
    public String getType();

    /** The tag name for the root of a structure. */
    public static final String STRUCTURE_NAME = "structure";
    /** The tag name for the type of structure this is. */
    public static final String STRUCTURE_TYPE_NAME = "type";
}
