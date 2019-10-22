package jvmMon.preprocessors;
import it.unimib.disco.lta.alfa.dataTransformation.Preprocessor;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerNone;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerSame;
import jvmMon.preprocessorRules.ClassLoaderRule;
import jvmMon.preprocessorRules.CompilerRule;
import jvmMon.preprocessorRules.GCRule;
import jvmMon.preprocessorRules.MemoryRule;
import jvmMon.preprocessorRules.RuntimeRule;
import jvmMon.preprocessorRules.ThreadRule;
import jvmMon.preprocessorRules.VMRule;


public class PreprocessorTag {
	
	public static void main(String[] args){
		
		Preprocessor normalizer = new Preprocessor();
		
		ValueTransformer excKeyDispenser = new ValueTransformerNone( );
		ValueTransformer threadKeyDispenser = new ValueTransformerNone( );
		ValueTransformer tidMonitorNameKeyDispenser = new ValueTransformerNone( );
		ValueTransformer monitorTagKeyDispenser = new ValueTransformerSame(  );
		
		
		normalizer.addRule( new ClassLoaderRule() );
		
		normalizer.addRule( new RuntimeRule( threadKeyDispenser, excKeyDispenser ) );
		normalizer.addRule( new CompilerRule() );
		normalizer.addRule( new GCRule() );
		normalizer.addRule( new MemoryRule() );
		normalizer.addRule( new ThreadRule( tidMonitorNameKeyDispenser, monitorTagKeyDispenser ) );
		normalizer.addRule( new VMRule() );
		
		normalizer.run(args);
	}
	
	
	
}
