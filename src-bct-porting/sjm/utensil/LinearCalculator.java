package sjm.utensil;

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

/**
 * A LinearCalculator models two variables that vary 
 * linearly with each other.
 *
 * For example, Fahrenheit and Celsius temperate scales vary 
 * linearly. Fahrenheit temperature varies from 32 to 212 as 
 * Celsius varies 0 to 100. A LinearCalculator can model the 
 * whole scale, if it is created as:
 *
 * <blockquote><pre>
 * 
 *     LinearCalculator lc = 
 *         new LinearCalculator(32, 212, 0, 100);
 *     System.out.println(lc.calculateYforGivenX(68));
 *
 *  </pre></blockquote> 
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class LinearCalculator {
	double xFrom;
	double xTo;
	double yFrom;
	double yTo;
/**
 * Create a LinearCalculator from known points on two scales.
 *
 * @param double xFrom
 * @param double xTo
 * @param double yFrom
 * @param double yTo
 */
public LinearCalculator(double xFrom, double xTo, double yFrom, double yTo) {
	this.xFrom = xFrom;
	this.xTo = xTo;
	this.yFrom = yFrom;
	this.yTo = yTo;
}
/**
 * Return the value on the first scale, corresponding to the given
 * value on the second scale.
 *
 * @return the value on the first scale, corresponding to the given
 *         value on the second scale
 */
public double calculateXforGivenY(double y) {
	if (yTo == yFrom) {
		return (xFrom + xTo) / 2;
	}
	return (y - yFrom) / (yTo - yFrom) * (xTo - xFrom) + xFrom;
}
/**
 * Return the value on the second scale, corresponding to the given
 * value on the first scale.
 *
 * @return the value on the second scale, corresponding to the given
 *         value on the first scale
 */
public double calculateYforGivenX(double x) {
	if (xTo == xFrom) {
		return (yFrom + yTo) / 2;
	}
	return (x - xFrom) / (xTo - xFrom) * (yTo - yFrom) + yFrom;
}
/**
 * Show the example in the class comment.
 * 
 * @param  args  ignored.
 */
public static void main(String args[]) {
	LinearCalculator lc = new LinearCalculator(32, 212, 0, 100);
	System.out.println(lc.calculateYforGivenX(68));
}
/**
 * Return a textual description of this object.
 * 
 * @return a textual description of this object
 */
public String toString() {
	return   "" + xFrom + ":" + xTo +
	       "::" + yFrom + ":" + yTo;
}
}
