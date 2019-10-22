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
 
package gui.event;

import java.util.EventListener;

/**
 * A <CODE>SelectionListener</CODE> is an interface for objects that
 * want to listen to <CODE>SelectionEvent</CODE>s.
 * 
 * @see gui.event.SelectionEvent
 * @author Thomas Finley
 */

public interface SelectionListener extends EventListener {
    /**
     * This method is called when a selection in an object is changed.
     * @param event the selection event
     */
    public void selectionChanged(SelectionEvent event);
}
