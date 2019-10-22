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
 * A LimitingLinearCalculator is a LinearCalculator where the
 * data points given in the constructor limit the extrapoltation.
 *
 * The X value of a LimitingLinearCalculator will never be
 * below the minimum of xFrom and xTo, and will never be above
 * the maximum of these two.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class LimitingLinearCalculator extends LinearCalculator {
/**
 * Create a LimitingLinearCalculator from known points on two scales.
 *
 * @param double xFrom
 * @param double xTo
 * @param double yFrom
 * @param double yTo
 */
public LimitingLinearCalculator(double xFrom, double xTo, double yFrom, double yTo) {
	super(xFrom, xTo, yFrom, yTo);
}
/**
 * Return the value on the first scale, corresponding to the given
 * value on the second scale. Limit the X value to be between xFrom
 * and xTo.
 *
 * @return the value on the first scale, corresponding to the given
 *         value on the second scale
 */
public double calculateXforGivenY(double y) {
	if (y <= yTo && y <= yFrom) {
		return yFrom <= yTo ? xFrom : xTo;
	}
	if (y >= yTo && y >= yFrom) {
		return yFrom >= yTo ? xFrom : xTo;
	}
	return super.calculateXforGivenY(y);
}
/**
 * Return the value on the second scale, corresponding to the given
 * value on the first scale. Limit the Y value to be between yFrom
 * and yTo.
 *
 * @return the value on the second scale, corresponding to the given
 *         value on the first scale
 */
public double calculateYforGivenX(double x) {
	if (x <= xTo && x <= xFrom) {
		return xFrom <= xTo ? yFrom : yTo;
	}
	if (x >= xTo && x >= xFrom) {
		return xFrom >= xTo ? yFrom : yTo;
	}
	return super.calculateYforGivenX(x);
}
}
