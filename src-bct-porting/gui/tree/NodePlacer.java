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
 
package gui.tree;

import java.util.Map;

import javax.swing.tree.TreeModel;

/**
 * A <CODE>NodePlacer</CODE> object is used to assign locations to
 * nodes in a tree.
 * 
 * @author Thomas Finley
 */

public interface NodePlacer {
    /**
     * Given a <CODE>TreeModel</CODE> that contains
     * <CODE>TreeNode</CODE> objects, this method returns a map from
     * <CODE>TreeNode</CODE> objects to <CODE>Dimension2D</CODE>
     * points.  The points should be in the domain ([0,1],[0,1]),
     * where (0,0) is the upper left corner and (1,0) the upper right.
     * A node placer may optionally not place an entry for each node
     * if a particular node should not be drawn.
     * @param tree the tree model
     * @param drawer the object that draws the nodes in the tree
     * @return a map from the nodes of the tree to points where those
     * nodes should be drawn
     */
    public Map placeNodes(TreeModel tree, NodeDrawer drawer);
}
