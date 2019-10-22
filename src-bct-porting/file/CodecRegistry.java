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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This is a registry of the codec, both {@link Encoder} and {@link
 * Decoder} objects.  It helps in the selection of a codec for
 * structures, and imposes an order of what codecs should be tried.
 * 
 * @author Thomas Finley
 */

public class CodecRegistry {
    /**
     * Adds a codec to the registry.
     * @param codec the codec to add
     */
    public void add(Codec codec) {
	addEncoder(codec);
	addDecoder(codec);
    }
    
    /**
     * Add a {@link Encoder} to the registry.
     * @param encoder the encoder to add
     */
    private void addEncoder(Encoder encoder) {
	encoders.add(encoder);
    }

    /**
     * Add a {@link Decoder} to the registry.
     * @param decoder the decoder to add
     */
    private void addDecoder(Encoder decoder) {
	decoders.add(decoder);
    }

    /**
     * Returns a list of encoders that could encode a structure.  The
     * encoders are returned in the order they were placed in the
     * registry with the {@link #add} method.
     * @param structure the structure the encoders should be able to
     * possibly encode, or <CODE>null</CODE> if all encoders should be
     * returned
     * @return the immutable list of encoders
     */
    public List getEncoders(Serializable structure) {
	if (structure == null)
	    return Collections.unmodifiableList(encoders);
	List validEncoders = new ArrayList();
	Iterator it = encoders.iterator();
	while (it.hasNext()) {
	    Codec enc = (Codec) it.next();
	    if (enc.canEncode(structure))
		validEncoders.add(enc);
	}
	return Collections.unmodifiableList(validEncoders);
    }

    /**
     * Returns a list of decoders.  All decoders are returned.  The
     * decoders are returned in the order they were placed in the
     * registry with the {@link #add} method.
     * @return the immutable list of decoders
     */
    public List getDecoders() {
	return Collections.unmodifiableList(decoders);
    }

    /** The encoders of the registry. */
    private List encoders = new ArrayList();
    /** The decoders of the registry. */
    private List decoders = new ArrayList();
}
