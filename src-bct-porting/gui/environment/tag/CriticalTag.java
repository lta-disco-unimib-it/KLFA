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
 * A critical tag is used to mark a component whose "stability"
 * requires that the object which is part of an environment remain
 * uneditable for the course of an action.  In short, those components
 * with tags marked as <CODE>EditorTag</CODE> must be deactivated.
 * The intention is that an <CODE>Environment</CODE> will detect the
 * presence of critical tagged objects, and will not allow other
 * <CODE>EditorTag</CODE> objects to be selectable.
 * 
 * @see gui.environment.Environment
 * @see gui.environment.tag.EditorTag
 * 
 * @author Thomas Finley
 */

public interface CriticalTag extends Tag {
    
}
