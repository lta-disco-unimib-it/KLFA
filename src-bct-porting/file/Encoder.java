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
 * description of the format of file this can encode.
 * 
 * @author Thomas Finley
 */

public interface Encoder {
    /**
     * Given a structure, this will attempt to write the structure to
     * a file.  This method should always return a file, or throw an
     * {@link EncodeException} in the event of failure with a message
     * detailing the nature of why the encoding failed.
     * @param structure the structure to encode
     * @param file the file to save to
     * @param parameters implementors have the option of accepting
     * custom parameters in the form of a map
     * @return the file to which the structure was written
     * @throws EncodeException if there was a problem writing the file
     */
    public File encode(Serializable structure, File file, Map parameters);

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
    public boolean canEncode(Serializable structure);

    /**
     * Proposes a file name for a given structure.  This encoder
     * should return either the file name, or a file name more
     * amenable to the format this encoder will encode in.  The file
     * name suggested should be a fixed point for this method, i.e.
     * <code>x.proposeFilename(x.proposeFilename(name,S),S)</code>
     * should always equal <code>x.proposeFilename(name,S)</code>,
     * where <code>S</code> is any structure.
     * @param filename the proposed file name
     * @param structure the structure that will be saved
     * @return the file name, either original or modified
     */
    public String proposeFilename(String filename, Serializable structure);
}
