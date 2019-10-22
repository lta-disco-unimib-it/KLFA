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
 
package gui.editor;

import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.util.List;

/**
 * The <CODE>DefaultToolBox</CODE> has all the tools for general
 * editing of an automaton.
 */

public class DefaultToolBox implements ToolBox {
    /**
     * Returns a list of tools including a <CODE>ArrowTool</CODE>,
     * <CODE>StateTool</CODE>, <CODE>TransitionTool</CODE> and
     * <CODE>DeleteTool</CODE>, in that order.
     * @param view the component that the automaton will be drawn in
     * @param drawer the drawer that will draw the automaton in the
     * view
     * @return a list of <CODE>Tool</CODE> objects.
     */
    public List tools(AutomatonPane view, AutomatonDrawer drawer) {
	List list = new java.util.ArrayList();
	list.add(new ArrowTool(view, drawer));
	list.add(new StateTool(view, drawer));
	list.add(new TransitionTool(view, drawer));
	list.add(new DeleteTool(view, drawer));
	return list;
    }
}
