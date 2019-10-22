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
 * A <CODE>ToolBox</CODE> is an object used for defining what tools
 * are in a <CODE>ToolBar</CODE> object.
 * 
 * @see gui.editor.ToolBar
 * @see gui.editor.Tool
 * 
 * @author Thomas Finley
 */

public interface ToolBox {
    /**
     * Returns a list of tools in the order they should be in the tool
     * bar.
     * @param view the view that the automaton will be drawn in
     * @param drawer the automaton drawer for the view
     */
    public List tools(AutomatonPane view, AutomatonDrawer drawer);
}
