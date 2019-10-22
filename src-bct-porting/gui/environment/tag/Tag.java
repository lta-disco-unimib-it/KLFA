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
 * A tag is simply an interface that can be applied to an object to
 * indicate that it satisfies some sort of property.  The intention is
 * for a tag object to have absolutely no methods to implement.  In
 * this way a tag object functions much like the bitfield vectors of
 * yore for identifying them with particular characteristics, but
 * without the inconvinience of having particular bits tied to certain
 * values that absolutely everybody and his mother had to be made
 * aware of.  A tag object may simply be something that implements
 * <CODE>Tag</CODE> only to indicate that it has no tag.
 * 
 * @see gui.environment.Environment#add
 * 
 * @author Thomas Finley
 */

public interface Tag {
    
}
