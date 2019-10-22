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
 
package gui.environment.tag;

/**
 * A critical tag is used to mark a component whose "stability"
 * requires that the object which is part of an environment remain
 * uneditable for the course of an action.  In short, those components
 * with tags marked as <CODE>EditorTag</CODE> must be deactivated.
 * The intention is that an <CODE>Environment</CODE> will detect the
 * presence of critical tagged objects, and will not allow other
 * <CODE>EditorTag</CODE> objects to be selectable.
 * 
 * @see gui.environment.Environment
 * @see gui.environment.tag.EditorTag
 * 
 * @author Thomas Finley
 */

public interface CriticalTag extends Tag {
    
}
