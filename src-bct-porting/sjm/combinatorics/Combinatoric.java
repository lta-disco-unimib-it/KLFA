package sjm.combinatorics;

import java.math.BigInteger;
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
 * The class Combinatoric contains methods for performing 
 * basic combinatoric operations such as counting numbers of 
 * permutations and combinations.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0
 */
public class Combinatoric {
/**
 * @return BigInteger, the number of unordered subsets of m 
 * objects chosen from a group of n objects.
 *
 * @param n int
 *
 * @param m int
 *
 * @exception CombinatoricException unless n >= m >= 0
 */
public static BigInteger c(int n, int m)
	throws CombinatoricException {
		
	check(n, m);	
	int r = Math.min(m, n - m);
	return p(n, r).divide(factorial(r));
}
/**
 * Check that 0 <= m <= n.
 *
 * @param n int
 *
 * @param m int
 *
 * @exception CombinatoricException unless n >= m >= 0
 */
static void check(int n, int m)
	throws CombinatoricException {
	if (n < 0) {
		throw new CombinatoricException(
			"n, the number of items, must be " +
			"greater than 0");
	}	
	if (n < m) {
		throw new CombinatoricException(
			"n, the number of items, must be >= m, "+
			"the number selected");
	}	
	if (m < 0) {
		throw new CombinatoricException(
			"m, the number of selected items, must be >= 0");
	}	
}
/**
 * @return BigInteger, the product of the numbers 1 ... n
 *
 * @param n int
 *
 * @exception CombinatoricException unless n >= 0
 */
public static BigInteger factorial(int n) 
	throws CombinatoricException {
	if (n < 0) {
		throw new CombinatoricException("n must be >= 0");
	}	
	BigInteger factorial = new BigInteger(new byte[]{1});
	for (int i = n; i > 1; i--) {
	    factorial = factorial.multiply(
	        new BigInteger(new byte[]{(byte)i}));
	}
	return factorial;
}
/**
 * @return BigInteger, the number of possible ways of 
 *         ordering n objects
 *
 * @param n int
 *
 * @exception CombinatoricException unless n >= 0
 */
public static BigInteger p(int n) 
	throws CombinatoricException {
		
	return factorial(n);
}
/**
 * @return BigInteger, the number of possible arrangements, 
 *         or orderings, of m objects chosen from a group of 
 *         n objects.
 *
 * @param n int
 *
 * @param m int
 *
 * @exception CombinatoricException unless n >= m >= 0
 */
public static BigInteger p(int n, int m) 
	throws CombinatoricException {
		
	check(n, m);
	
	BigInteger product = new BigInteger(new byte[]{1});
	for (int i = n; i > n - m; i--) {
	    product = product.multiply(
		    new BigInteger(new byte[]{(byte)i}));
	}
	return product;
}
}
