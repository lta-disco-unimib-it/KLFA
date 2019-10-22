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

package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;

import file.xml.DOMPrettier;
import file.xml.Transducer;
import file.xml.TransducerFactory;

/**
 * This is the codec for reading and writing JFLAP structures as XML
 * documents.
 * 
 * @author Thomas Finley
 */

public class XMLCodec extends Codec {
    /**
     * Given a file, this will return a JFLAP structure associated
     * with that file.
     * @param file the file to decode into a structure
     * @param parameters these parameters are ignored
     * @return a JFLAP structure resulting from the interpretation of
     * the file
     * @throws ParseException if there was a problem reading the file
     */
    public Serializable decode(File file, Map parameters) {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	try {
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(file);
	    Transducer transducer = TransducerFactory.getTransducer(doc);
	    return transducer.fromDOM(doc);
	} catch (ParserConfigurationException e) {
	    throw new ParseException
		("Java could not create the parser!");
	} catch (IOException e) {
	    throw new ParseException("Could not open file to read!");
	} catch (org.xml.sax.SAXException e) {
	    throw new ParseException("Could not parse XML!\n"+
				     e.getMessage());
	} catch (ExceptionInInitializerError e) {
	    // Hmm.  That shouldn't be.
	    System.err.println("STATIC INIT:");
	    e.getException().printStackTrace();
	    throw new ParseException("Unexpected Error!");
	}
    }

    /**
     * Given a structure, this will attempt to write the structure as
     * a serialized object to a file.
     * @param structure the structure to encode
     * @param file the file to save the structure to
     * @param parameters implementors have the option of accepting
     * custom parameters in the form of a map
     * @return the file to which the structure was written
     * @throws EncodeException if there was a problem writing the file
     */
    public File encode(Serializable structure, File file, Map parameters) {
	Transducer transducer = null;
	try {
	    transducer = TransducerFactory.getTransducer(structure);
	    Document dom = transducer.toDOM(structure);
	    DOMPrettier.makePretty(dom);
	    
	    PrintWriter writer = new PrintWriter(new FileWriter(file));
	    NodeList list = dom.getChildNodes();
	    for (int i=0; i<list.getLength(); i++) {
		Object itemText = null;
		if (list.item(i) instanceof ProcessingInstruction) {
		    ProcessingInstruction pi =
			(ProcessingInstruction) list.item(i);
		    itemText = "<?"+pi.getTarget()+" "+pi.getData()+"?>";
		} else {
		    itemText = list.item(i);
		}
		writer.println(itemText);
	    }
	    writer.flush();
	    writer.close();
	    return file;
	} catch (IllegalArgumentException e) {
	    throw new EncodeException
		("No XML transducer available for this structure!");
	} catch (IOException e) {
	    throw new EncodeException
		("Could not open file to write!");
	}
    }

    /**
     * Returns if this type of structure can be encoded with this
     * encoder.  This should not perform a detailed check of the
     * structure, since the user will have no idea why it will not be
     * encoded correctly if the {@link #encode} method does not throw
     * a {@link ParseException}.
     * @param structure the structure to check
     * @return if the structure, perhaps with minor changes, could
     * possibly be written to a file
     */
    public boolean canEncode(Serializable structure) {
	return true;
    }

    /**
     * Returns the description of this codec.
     * @return the description of this codec
     */
    public String getDescription() {
	return "JFLAP 4 File";
    }

    /**
     * Given a proposed filename, returns a new suggested filename.
     * JFLAP 4 saved files have the suffix <CODE>.jf4</CODE> appended
     * to them.
     * @param filename the proposed name
     * @param structure the structure that will be saved
     * @return the new suggestion for a name
     */
    public String proposeFilename(String filename, Serializable structure) {
	if (!filename.endsWith(SUFFIX))
	    return filename + SUFFIX;
	return filename;
    }

    /** The filename suffix. */
    public static final String SUFFIX = ".jff";
}
