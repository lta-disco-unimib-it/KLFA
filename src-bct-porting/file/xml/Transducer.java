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
