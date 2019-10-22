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
