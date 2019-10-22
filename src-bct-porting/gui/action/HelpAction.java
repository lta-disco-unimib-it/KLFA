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
 
package gui.action;

import gui.WebFrame;

import java.awt.event.KeyEvent;
import java.util.WeakHashMap;

import javax.swing.KeyStroke;

/**
 * The <CODE>HelpAction</CODE> is an abstract action that is meant to
 * bring up a help page appropriate to whatever context.  It also
 * serves as a general sort of database to relate types of objects to
 * a particular URL to bring up in the help web frame for the various
 * subclasses that will implement this action.
 * 
 * @see gui.WebFrame
 * 
 * @author Thomas Finley
 */

public abstract class HelpAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>HelpAction</CODE>.
     */
    public HelpAction() {
	super("Help...", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_SLASH, MAIN_MENU_MASK));	
    }

    /**
     * Associates a particular object with a URL.
     * @param key the key which will map to a URL
     * @param url the string representation of the URL to visit
     */
    public static void setURL(Object object, String url) {
	HELP_MAP.put(object, url);
    }

    /**
     * Returns the URL for a particular object.  If there is a direct
     * mapping from object to a URL, that URL is returned.  If there
     * is no direct mapping, a mapping from this object's class to a
     * URL is attempted.  If that yields nothing, then
     * <CODE>null</CODE> is returned.
     * @param object the object to get help for
     * @return a URL of help for this object, or <CODE>null</CODE> if
     * no help for this object exists
     */
    public static String getURL(Object object) {
	String url = (String) HELP_MAP.get(object);
	if (url != null) return url;
	Class c = object instanceof Class ? (Class)object : object.getClass();
	while (c != null) {
	    url = (String) HELP_MAP.get(c);
	    if (url != null) return url;
	    url = "/DOCS/"+c.getName()+".html";
	    if (c.getResource(url) != null) return url;
	    c = c.getSuperclass();
	}
	return null;
    }

    /**
     * Displays help for this object.  If there is no particular help
     * for this object available according to <CODE>getURL</CODE>,
     * then the URL indicated by <CODE>DEFAULT_HELP</CODE> will be
     * displayed in a <CODE>WebFrame</CODE>.
     * @param object the object to display help for
     * @see #getURL(Object)
     * @see gui.WebFrame
     */
    public static void displayHelp(Object object) {
	String url = getURL(object);
	if (url == null) url = DEFAULT_HELP;
	FRAME.gotoURL(url);
	FRAME.setVisible(true);
    }

    /** The mapping of objects to URLs. */
    private static final WeakHashMap HELP_MAP = new WeakHashMap();
    /** The default URL in case there is no help for a subject. */
    public static final String DEFAULT_HELP = "/DOCS/nohelp.html";
    /** The web frame. */
    private static final WebFrame FRAME = new WebFrame("/DOCS/index.html");

    /**
     * This manually adds the help topics.  The preferred method is to
     * make the HTML file name (before .html) the name of the class,
     * i.e., gui.editor.EditorPane.html.
     */
    static {
	/*setURL(gui.editor.EditorPane.class,
	  "/DOCS/automatonEditor.html");*/
    }
}
