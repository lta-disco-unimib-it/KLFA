package it.unimib.disco.lta.alfa.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LogMapper {
	private File original;
	private File trans;
	private HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
	
	
	
	
	
	
	public LogMapper( File original, File trans ) throws IOException{
		//System.out.println(original.getName()+" "+trans.getName());
		this.original = original;
		this.trans = trans;
		
		BufferedReader r = new BufferedReader(new FileReader(trans));
		String line;
		int i = 0;
		while (  ( line = r.readLine() ) != null ){
			++i;
			String[] ls = line.split(" : ");
			map.put(i,Integer.valueOf(ls[1]));
		}
		r.close();
		System.out.println("Mapper map: "+map);
	}
	
	public Integer getLineNumber( int i ){
		//System.out.println(i);
		return map.get(i) ;
	}
	
	public String getLineValue( int n ) throws IOException{
		Integer realLine = getLineNumber(n);
		if ( realLine == null ){
			System.out.println("No real line for "+n);
		}
		BufferedReader r = new BufferedReader(new FileReader(original));
		String line = null;
		int i = 0;
		while (  i < realLine ){
			line = r.readLine();
			++i;
		}
		r.close();
		return line;
	}
}
