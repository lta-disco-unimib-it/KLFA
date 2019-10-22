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
 
package gui.viewer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * An invisible curved arrow is a curved arrow where the actual line
 * and arrow are not displayed, only the label.  This is used for
 * transitions where there are multiple transitions from one state to
 * another.
 * 
 * @author Thomas Finley
 */

public class InvisibleCurvedArrow extends CurvedArrow {
    /**
     * Instantiates an <CODE>InvisibleCurvedArrow</CODE> object.
     * @param x1 the x coordinate of the start point
     * @param y1 the y coordinate of the start point
     * @param x2 the x coordinate of the end point
     * @param y2 the y coordinate of the end point
     * @param curvy the curvi-ness factor; 0 will create a straight
     * line; 1 and -1 are rather curvy
     */
    public InvisibleCurvedArrow(int x1, int y1, int x2, int y2, float curvy) {
	super(x1, y1, x2, y2, curvy);
    }

    /**
     * Instantiates an <CODE>InvisibleCurvedArrow</CODE> object.
     * @param start the start point
     * @param end the end point
     * @param curvy the curvi-ness factor; 0 will create a straight
     * line; 1 and -1 are rather curvy
     */
    public InvisibleCurvedArrow(Point start, Point end, float curvy) {
	super(start, end, curvy);
    }

    /**
     * Draws the arrow on the indicated graphics environment.
     * @param g the graphics to draw this arrow upon
     */
    public void draw(Graphics2D g) {
	if (needsRefresh) refreshCurve();
	drawText(g);
    }
    
    /**
     * Draws a highlight of the curve.  This will only highlight the
     * label.
     * @param g the graphics to draw the highlight of the curve upon
     */
    public void drawHighlight(Graphics2D g) {
	if (needsRefresh) refreshCurve();
	Graphics2D g2 = (Graphics2D) g.create();
	g2.setStroke(new java.awt.BasicStroke(6.0f));
	g2.setColor(HIGHLIGHT_COLOR);
	g2.transform(affineToText);
	g2.fill(bounds);
	g2.dispose();
    }

    /**
     * Returns the bounds.
     * @return the rectangular bounds for this curved arrow
     */
    public Rectangle2D getBounds() {
	Area area = new Area(bounds);
	area.transform(affineToText);
	return area.getBounds();
    }

    /**
     * Determines if a point is on/near the curved arrow.  Since here
     * the arrow is not displayed, only points on the label are
     * identified.
     * @param point the point to check
     * @param fudge the radius around the point that should be checked
     * for the presence of the curve
     * @return <TT>true</TT> if the point is on the curve within a
     * certain fudge factor, <TT>false</TT> otherwise
     */
    public boolean isNear(Point point, int fudge) {
	if (needsRefresh) refreshCurve();
	try {
	    if (bounds.contains(affineToText.inverseTransform(point, null)))
		return true;
	} catch (java.awt.geom.NoninvertibleTransformException e) {
	    
	}
	return false;
    }
}
