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
 
package regular;

import java.util.EventListener;

/**
 * The expression change listener should be implemented by objects
 * that wish to be notified when a regular expression changes.
 * 
 * @author Thomas Finley
 */

public interface ExpressionChangeListener extends EventListener {
    /**
     * This method is called when a regular expression changes.
     * @param event the event object that was changed
     */
    public void expressionChanged(ExpressionChangeEvent event);
}
