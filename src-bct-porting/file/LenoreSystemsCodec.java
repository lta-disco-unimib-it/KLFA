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
import java.io.Serializable;
import java.util.Map;

/**
 * This is the codec for reading JFLAP structures in the
 * Lenore-Systems format, making any conversions necessary between the
 * two formats.  Lenore-Systems is my name for the L-systems tool
 * developed at Duke by Lenore Ramm.  Its real name is Lsys, and was
 * never widely distributed.
 * 
 * @author Thomas Finley
 */

public class LenoreSystemsCodec extends Codec {
    /**
     * Given a file, this will return an L-system associated with that
     * file.  This method should always return a structure, or throw a
     * {@link ParseException} in the event of failure with a message
     * detailing the nature of why the decoder failed.
     * @param file the file to decode into a structure
     * @param parameters this decoder ignores all parameters
     * @return a JFLAP structure resulting from the interpretation of
     * the Lenore-Systems file
     * @throws ParseException if there was a problem reading the file
     */
    public Serializable decode(File file, Map parameters) {
	throw new ParseException("This codec is not implemented yet.");
    }

    /**
     * This method does nothing at all.
     * @param structure the structure to encode
     * @param file the file to write to -- nothing is written, of
     * course
     * @param parameters implementors have the option of accepting
     * custom parameters in the form of a map
     * @return the file to which the structure was written
     * @throws EncodeException if there was a problem writing the file
     */
    public File encode(Serializable structure, File file,
		       Map parameters) {
	return file;
    }

    /**
     * This encoder can encode nothing.
     * @param structure the structure to check
     * @return if the structure, perhaps with minor changes, could
     * possibly be written to a file
     */
    public boolean canEncode(Serializable structure) {
	return false;
    }

    /**
     * Returns the description of this codec.
     * @return the description of this codec
     */
    public String getDescription() {
	return "Lenore-Systems File";
    }
    
    /** The assignment of Lenore-Systems color names to L-systems names. */
}
