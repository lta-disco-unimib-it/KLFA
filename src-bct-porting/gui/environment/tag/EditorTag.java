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
 * An editor tag is a tag intended for use to tag some object that is
 * intended to be used as some sort of editor.  In general the tag is
 * meant for something that can change the inner workings of some sort
 * of object, presumably for the purpose, one would assume, of keeping
 * it from doing so if such an edit would be inconvenient or hazardous
 * at some critical time.
 * 
 * @author Thomas Finley
 */

public interface EditorTag extends Tag {
    
}
