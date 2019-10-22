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
 * A satisfier is a general object that takes an object and its tags,
 * and returns whether or not it satisfies some general property.
 * Usually the tagged object should be enough to satisfy the
 * requirements, but the object that is tagged is passed along as well
 * in case it is important.
 * 
 * @see gui.environment.tag.Tag
 * @see gui.environment.Environment#add
 * 
 * @author Thomas Finley
 */

public interface Satisfier {
    /**
     * Checks to see if an object and its tag satisfy some properties
     * @param object the object, in case it is useful
     * @param tag an object associated with <CODE>object</CODE>, which
     * presumably implements some varieties of <CODE>tag</CODE> to
     * identify the object
     * @return <CODE>true</CODE> if this object with this tag
     * satisfies whatever this satisfier wishes to satisfy, or
     * <CODE>false</CODE> if it does not
     */
    public boolean satisfies(Object object, Tag tag);
}
