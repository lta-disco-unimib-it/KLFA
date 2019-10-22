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
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Maps either a DOM or a object to the appropriate transducer.
 * 
 * @author Thomas Finley
 */

public class TransducerFactory {
    /**
     * Given a DOM document, this will return an appropriate instance
     * of a transducer for the type of document.  Note that the type
     * of the structure should be specified with in the "type" tags.
     * @param document the document to get the transducer for
     * @return the correct transducer for this document
     * @throws IllegalArgumentException if the document does not map
     * to a transducer, or if it does not contain a "type" tag at all
     */
    public static Transducer getTransducer(Document document) {
	Element elem = document.getDocumentElement();
	// Check for the type tag.
	NodeList typeNodes = elem.getElementsByTagName("type");
	if (typeNodes.getLength() == 0)
	    throw new IllegalArgumentException
		("No <type> tag appears to exist!");
	// Find the type node.  Assume the first is valid.
	Node typeElement = typeNodes.item(0);
	NodeList subtypeNodes = typeElement.getChildNodes();
	String type = "";
	// Check for the text nodes in <type> tag.
	for (int i=0; i<subtypeNodes.getLength(); i++) {
	    Node node = subtypeNodes.item(i);
	    if (node.getNodeType() == Node.TEXT_NODE) {
		type = ((Text) node).getData();
		break;
	    }
	}
	// Check for the type.
	Object o = typeToTransducer.get(type);
	if (o == null) throw new IllegalArgumentException
			   ("The type \""+type+"\" is not recognized.");
	return instantiate(o);
    }

    /**
     * Given a JFLAP structure, this will return an appropriate
     * instance of a transducer for the class of structure passed in.
     * Note, if there are multiple transducer types that could be
     * applied to this structure, the transducer that corresponds to
     * the closest superclass type is returned.  For example, if class
     * A is a superclass of B is a superclass of C is a superclass of
     * D, and this factory has transducers for A and C, instances of A
     * and B will get A transducers, and instances of C and D will get
     * C transducers.
     * @param structure the structure to get the transducer for
     * @return the correct transducer for this structure
     * @throws IllegalArgumentException if the structure does not map
     * to a transducer
     */
    public static Transducer getTransducer(Serializable structure) {
	Class c = structure.getClass();
	// Cycle through the superclasses.
	while (c != null) {
	    Object o = classToTransducer.get(c);
	    if (o != null) return instantiate(o);
	    c = c.getSuperclass();
	}
	// Apparently no transducer could be found.
	throw new IllegalArgumentException
	    ("Cannot get transducer for object of "+structure.getClass()+"!");
    }

    /**
     * Turns an object into a transducer.  If the passed in object is
     * of type <TT>java.lang.Class</TT>, a new instance is returned.
     * If the passed in is a {@link file.Transducer}, the argument is
     * cast and returned.
     * @param object should be either a class or a transducer
     * @return an instance of a transducer appropriate for the passed
     * in argument
     * @throws IllegalArgumentException if the passed argument is not
     * class, nor a transducer, or is a class that cannot be
     * instantiated
     */
    private static Transducer instantiate(Object object) {
	if (object instanceof Class) {
	    try {
		return (Transducer) ((Class) object).newInstance();
	    } catch (Throwable e) {
		throw new IllegalArgumentException
		    ("Could not instantiate "+object+"!");
	    }
	} else if (object instanceof Transducer) {
	    return (Transducer) object;
	} else {
	    throw new IllegalArgumentException
		("Object "+object+" does not correspond to a transducer!");
	}
    }

    /**
     * Creates a correspondence between type tags, structure classes,
     * and a transducer class.
     * @param type the type tag, or <CODE>null</CODE> if
     * <CODE>transducer</CODE> is in fact an instance and the
     * transducer type should be retrieved from there
     * @param structureClass the class of the structure
     * @param transducer either a transducer instance, or a transducer
     * class
     */
    private static void add(String type, Class structureClass,
			    Object transducer) {
	if (type == null)
	    type = ((Transducer) transducer).getType();
	typeToTransducer.put(type, transducer);
	classToTransducer.put(structureClass, transducer);
    }

    /**
     * Initializes the maps.
     */
    static {
	typeToTransducer = new HashMap();
	classToTransducer = new HashMap();
	add(null,automata.fsa.FiniteStateAutomaton.class,new FSATransducer());
	add(null,automata.pda.PushdownAutomaton.class,new PDATransducer());
	add(null,automata.turing.TuringMachine.class,new TMTransducer());
	add(null,grammar.Grammar.class,new GrammarTransducer());
	add(null,regular.RegularExpression.class,new RETransducer());
	add(null,grammar.lsystem.LSystem.class,new LSystemTransducer());
    }

    /** Mapping of DOM "type" tags to a corresponding transducer class. */
    private static Map typeToTransducer;
    /** Mapping of structure classes to a corresponding transducer class. */
    private static Map classToTransducer;
}
