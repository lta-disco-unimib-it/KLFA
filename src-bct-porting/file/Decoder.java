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
 * This specifies the common interface for objects that parse
 * documents and produce a corresponding structure.  Ideally the
 * <CODE>toString</CODE> method should be implemented with a brief
 * description of the format of file this can decode.
 * 
 * @author Thomas Finley
 */

public interface Decoder {
    /**
     * Given a file, this will return a JFLAP structure associated
     * with that file.  This method should always return a structure,
     * or throw a {@link ParseException} in the event of failure with
     * a message detailing the nature of why the decoder failed.
     * @param file the file to decode into a structure
     * @param parameters implementors have the option of accepting
     * custom parameters in the form of a map
     * @return a JFLAP structure resulting from the interpretation of
     * the file
     * @throws ParseException if there was a problem reading the file
     */
    public Serializable decode(File file, Map parameters);

    /**
     * Returns an instance of a corresponding encoder.  In many cases
     * the returned will be <CODE>this</CODE>.
     * @return an encoder that encodes in the same format this decodes
     * in, or <CODE>null</CODE> if there is no such encoder
     */
    public Encoder correspondingEncoder();
}
