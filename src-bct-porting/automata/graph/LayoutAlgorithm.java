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
 
package automata.graph;

import java.util.Set;

/**
 * This interface defines an algorithm that lays out a graph.
 * 
 * @author Thomas Finley
 */

public interface LayoutAlgorithm {
    /**
     * Moves the vertices of the states of the graph so that they are
     * in some pleasing position.
     * @param graph the graph whose states this method shall move
     * @param isovertices the set of vertices that will not move at
     * all
     */
    public void layout(Graph graph, Set isovertices);
}
