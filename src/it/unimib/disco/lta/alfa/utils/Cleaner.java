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
