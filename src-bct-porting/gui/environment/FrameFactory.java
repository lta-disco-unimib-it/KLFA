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
 
package gui.environment;

import java.awt.Dimension;
import java.io.Serializable;

/**
 * The <CODE>FrameFactory</CODE> is a factory for creating environment
 * frames.
 * 
 * @author Thomas Finley
 */

public class FrameFactory {
    /**
     * This creates an environment frame for a new item.
     * @param object the object that we are to edit
     * @return the environment frame for this new item, or
     * <CODE>null</CODE> if an error occurred
     */
    public static EnvironmentFrame createFrame(Serializable object) {
	Environment environment = EnvironmentFactory.getEnvironment(object);
	if (environment == null) return null; // No environment could be found.
//	environment.setFile(new File("./environment.log"));
	EnvironmentFrame frame = new EnvironmentFrame(environment);
	frame.pack();
	
	// Make sure that the size of the frame is above a certain
	// threshold.
	int width=600, height=400;
	width = Math.max(width, frame.getSize().width);
	height = Math.max(height, frame.getSize().height);
	frame.setSize(new Dimension(width, height));
	frame.setVisible(true);
	
	return frame;
    }
}
