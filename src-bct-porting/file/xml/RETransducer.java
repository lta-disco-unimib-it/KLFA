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

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import regular.RegularExpression;
import file.ParseException;

/**
 * This transducer is the codec for {@link regular.RegularExpression} objects.
 * 
 * @author Thomas Finley
 */

public class RETransducer extends AbstractTransducer {
    /**
     * Returns the type this transducer recognizes, "re".
     * @return the string "re"
     */
    public String getType() {
	return "re";
    }

    /**
     * Given a document, this will return the corresponding regular
     * expression encoded in the DOM document.
     * @param document the DOM document to convert
     * @return the {@link regular.RegularExpression} instance
     */
    public java.io.Serializable fromDOM(Document document) {
	Map e2t = elementsToText(document.getDocumentElement());
	String expression = (String) e2t.get(EXPRESSION_NAME);
	if (expression == null)
	    if (e2t.containsKey(EXPRESSION_NAME))
		throw new ParseException
		    ("Regular expression structure has no "
		     +EXPRESSION_NAME+" tag!");
	    else
		expression = "";
	return new RegularExpression(expression);
    }

    /**
     * Given a JFLAP regular expression, this will return the
     * corresponding DOM encoding of the structure.
     * @param structure the regular expression to encode
     * @return a DOM document instance
     */
    public Document toDOM(java.io.Serializable structure) {
	RegularExpression re = (RegularExpression) structure;
	Document doc = newEmptyDocument();
	Element se = doc.getDocumentElement();
	// Add the regular expression tag.
	se.appendChild(createComment(doc, COMMENT_EXPRESSION));
	se.appendChild(createElement(doc, EXPRESSION_NAME, null,
				     re.asString()));
	// Return the completed document.
	return doc;
    }

    /** The tag name for the regular expression itself. */
    public static final String EXPRESSION_NAME = "expression";

    /** The comment for the list of productions. */
    private static final String COMMENT_EXPRESSION =
	"The regular expression.";
}
