package jvmMon.preprocessors;
import it.unimib.disco.lta.alfa.dataTransformation.Preprocessor;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerNone;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToAccess;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerSame;

import java.io.File;
import java.io.IOException;

import jvmMon.preprocessorRules.GCRule;
import jvmMon.preprocessorRules.MemoryRule;
import jvmMon.preprocessorRules.RuntimeRule;
import jvmMon.preprocessorRules.ThreadRule;



public class SeparatorNoClassLastTag extends Preprocessor {


	public static Preprocessor getVMNormalizer(){
		Preprocessor normalizer = new Preprocessor();
		normalizer.addRule( new GCRule() );
		normalizer.addRule( new MemoryRule() );
		return normalizer;
	}




	public static Preprocessor getTHNormalizer(){
		Preprocessor normalizer = new Preprocessor();

		ValueTransformer threadKeyDispenser = new ValueTransformerNone( );
		ValueTransformer excKeyDispenser = new ValueTransformerNone( );
		ValueTransformer tidMonitorNameKeyDispenser = new ValueTransformerNone( );
		ValueTransformer monitorTagKeyDispenser = new ValueTransformerRelativeToAccess("T");


		normalizer.addRule( new RuntimeRule( threadKeyDispenser, excKeyDispenser ) );
		normalizer.addRule( new ThreadRule( tidMonitorNameKeyDispenser, monitorTagKeyDispenser ) );
		return normalizer;

	}


	
	public static void main(String[] args) {
		if ( args.length != 2 ){
			printUsage();
		}
		File input = new File ( args[0] );
		File VMoutput = new File ( "VM"+args[1] );
		File THoutput = new File ( "TH"+args[1] );
		File VMoutputLog = new File ( "VM"+args[1]+".extlog" );
		File THoutputLog = new File ( "TH"+args[1]+".extlog" );
		Preprocessor vmn = getVMNormalizer();
		try {
			vmn.process(input, VMoutput,VMoutputLog);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Preprocessor thn = getTHNormalizer();
		try {
			thn.process(input, THoutput, THoutputLog);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
