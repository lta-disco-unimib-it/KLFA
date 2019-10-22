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
 
package gui.environment.tag;

/**
 * A satisfier is a general object that takes an object and its tags,
 * and returns whether or not it satisfies some general property.
 * Usually the tagged object should be enough to satisfy the
 * requirements, but the object that is tagged is passed along as well
 * in case it is important.
 * 
 * @see gui.environment.tag.Tag
 * @see gui.environment.Environment#add
 * 
 * @author Thomas Finley
 */

public interface Satisfier {
    /**
     * Checks to see if an object and its tag satisfy some properties
     * @param object the object, in case it is useful
     * @param tag an object associated with <CODE>object</CODE>, which
     * presumably implements some varieties of <CODE>tag</CODE> to
     * identify the object
     * @return <CODE>true</CODE> if this object with this tag
     * satisfies whatever this satisfier wishes to satisfy, or
     * <CODE>false</CODE> if it does not
     */
    public boolean satisfies(Object object, Tag tag);
}
