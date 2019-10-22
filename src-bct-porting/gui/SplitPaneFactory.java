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
 
package gui;

import gui.environment.Environment;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSplitPane;

/**
 * This is a convinience factory class for doing the annoying
 * repetitive task of setting up a split pane.  Basically, all split
 * panes should behave alike: they should maintain their proportions,
 * have a certain amount set up for each side when shown, and soforth.
 * If the idiots at Sun had actually defined JSplitPane's behavior in
 * such a way as actually MADE SENSE this would be unnecessary, but
 * apparently the designers of the split pane came from the shallow
 * end of the gene pool, so here we are.
 * 
 * @author Thomas Finley
 */

public class SplitPaneFactory {
    /**
     * This class ain't for instantiation!
     */
    private SplitPaneFactory() {
	
    }

    /**
     * Comes up with a new split pane.
     * @param environment the environment that this split pane will be
     * added to; note that the pane is not added at this time
     * @param horizontal <CODE>true</CODE> if this should be a
     * horizontal split (i.e. the divider is vertical, left-right), or
     * <CODE>false</CODE> if this should be a vertical split (one
     * component above another)
     * @param ratio what the left pane should take up of all the space
     * @param left the left/top pane
     * @param right the right/bottom pane
     */
    public static JSplitPane createSplit(Environment environment, 
					 boolean horizontal,
					 double ratio,
					 Component left, Component right) {
    	JSplitPane split =
	    new JSplitPane(horizontal ? JSplitPane.HORIZONTAL_SPLIT :
			   JSplitPane.VERTICAL_SPLIT, true, left, right);
	Dimension dim = environment.getSize();
	Component[] comps = environment.getComponents();
	if (comps.length != 0) dim = comps[0].getSize();
	int size = horizontal ? dim.width : dim.height;
	split.setDividerLocation((int)((double)size *ratio));
	split.setResizeWeight(ratio);
	return split;
    }
}
