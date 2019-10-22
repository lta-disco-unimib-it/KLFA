package it.unimib.disco.lta.alfa.inferenceEngines;

import java.util.zip.CRC32;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CRC32 c = new CRC32();
		c.update(3);
		System.out.println(c.getValue());
		c.update(5);
		System.out.println(c.getValue());
		
		c.reset();
		c.update(5);
		System.out.println(c.getValue());
		
		c.update(3);
		System.out.println(c.getValue());
		
		c.reset();
		c.update(3);
		System.out.println(c.getValue());
		
		c.update(5);
		System.out.println(c.getValue());
	}

}
