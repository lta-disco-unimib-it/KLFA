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
package it.unimib.disco.lta.alfa.utils;

import java.io.File;

import util.FileUtil;

public class Cleaner {

	public static void main(String args[]){
		for ( String arg : args ){
			File file = new File(arg);
			System.out.println("Deleting :"+file);
			if ( file.exists() ){
				boolean res = FileUtil.deleteRecursively(file);
				if ( res ){
					System.out.println("\tdeleted ");
				} else {
					System.out.println("\tCannot delete ");
				}
					
					
			} else {
				System.out.println("\tdon't exist ");
			}
		}
	}
}
