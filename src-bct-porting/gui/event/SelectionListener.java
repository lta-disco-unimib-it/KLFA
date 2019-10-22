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
