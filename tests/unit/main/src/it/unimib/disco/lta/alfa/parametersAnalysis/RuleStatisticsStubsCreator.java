package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatistics;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * Creator for RuleStatistics Stubs
 * 
 * RS1 and RS2 share all values, but parameters are inverted
 * 
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class RuleStatisticsStubsCreator  extends TestCase{

	public static RuleStatistics getRS1_NoCount(String clusterName){
		RuleStatistics rs = new RuleStatistics(clusterName+"_R1",false);

		int line=1;
		rs.addAll(createParameters(new String[]{"1","2"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"5","6"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"7","8"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"9","10"}), line);


		return rs;
	}
	
	public static RuleStatistics getRS1(){
		return getRS1("C");
	}
	
	public static RuleStatistics getRS1(String clusterName){
		RuleStatistics rs = new RuleStatistics(clusterName+"_R1",true);

		int line=1;
		rs.addAll(createParameters(new String[]{"1","2"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"5","6"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"7","8"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"9","10"}), line);


		return rs;
	}
	
	public static RuleStatistics getRS2(){
		return getRS2("C");
	}
	
	public static RuleStatistics getRS2(String clusterName){
		RuleStatistics rs = new RuleStatistics(clusterName+"_R2",true);
		
		int line = 2;
		rs.addAll(createParameters(new String[]{"2","1"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"4","3"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"6","5"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"8","7"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"10","9"}), line);


		return rs;
	}

	public static RuleStatistics getRS3(){
		return getRS3("C_");
	}
	
	/**
	 * Returns a RS with
	 * events occurring every 2 lines
	 * 2 parameters
	 * 5 different values for every parameter
	 * identical values occurring every 10 lines
	 * identical values occurring 3 times
	 * 
	 * @return
	 */
	public static RuleStatistics getRS3(String clusterName){
		RuleStatistics rs = new RuleStatistics(clusterName+"_R1",true);

		int line=1;
		rs.addAll(createParameters(new String[]{"1","2"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"5","6"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"7","8"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"9","10"}), line);

		line+=2;
		rs.addAll(createParameters(new String[]{"1","2"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"5","6"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"7","8"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"9","10"}), line);

		line+=2;
		rs.addAll(createParameters(new String[]{"1","2"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"5","6"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"7","8"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"9","10"}), line);
		
		return rs;
	}
	
	/**
	 * Returns a RS with
	 * events mean 1,5
	 * 2 parameters
	 * 5 different values for every parameter
	 * identical values occurring every 9.53 lines
	 * identical values occurring 4,2 times 
	 * 
	 * @return
	 */
	public static RuleStatistics getRS4(){
		return getRS4("C");
	}
	
	public static RuleStatistics getRS4(String componentName){
		RuleStatistics rs = new RuleStatistics(componentName+"_R4",true);

		int line=1;
		rs.addAll(createParameters(new String[]{"1","2"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		
		

		line+=2;
		rs.addAll(createParameters(new String[]{"1","2"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		

		line+=2;
		rs.addAll(createParameters(new String[]{"1","2"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);
		
		
		return rs;
	}

	public static RuleStatistics getRS5(){
		RuleStatistics rs = new RuleStatistics("R1",true);

		int line=1;
		rs.addAll(createParameters(new String[]{"1","2"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"11","12"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"13","14"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"15","16"}), line);
		


		return rs;
	}

	public static RuleStatistics getRS6(){
		RuleStatistics rs = new RuleStatistics("R1",true);

		int line=1;
		rs.addAll(createParameters(new String[]{"11","12"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"13","14"}), line);
		line+=2;
		rs.addAll(createParameters(new String[]{"15","16"}), line);
		


		return rs;
	}

	/**
	 * This rule partially complements RS4
	 * @return
	 */
	public static RuleStatistics getRS7(){
		return getRS7("C");
	}
	
	public static RuleStatistics getRS7(String componentPrefix){
		RuleStatistics rs = new RuleStatistics(componentPrefix+"_R7",true);

		int line=5;

		rs.addAll(createParameters(new String[]{"1","A"}), line);
		line+=5;
		rs.addAll(createParameters(new String[]{"1","B"}), line);
		
		
		
		
		return rs;
	}
	
	/**
	 * This rule partially complements RS4 and RS7
	 * @return
	 */
	public static RuleStatistics getRS8(){
		return getRS8("C");
	}
	
	public static RuleStatistics getRS8(String componentPrefix){
		RuleStatistics rs = new RuleStatistics(componentPrefix+"_R8",true);

		int line=15;

		rs.addAll(createParameters(new String[]{"999","A"}), line);
		line+=1;
		rs.addAll(createParameters(new String[]{"999","B"}), line);
		
		
		
		
		return rs;
	}
	
	public static RuleStatistics getRS9(){
		return getRS9("C"); 
	}
	
	public static RuleStatistics getRS9(String componentName){
		RuleStatistics rs = new RuleStatistics(componentName+"_R9",true);

		int line=1;
		rs.addAll(createParameters(new String[]{"1","2"}), line); //1
		line+=1;
		rs.addAll(createParameters(new String[]{"3"}), line);		//2
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);	//3
		line+=1;
		rs.addAll(createParameters(new String[]{"3"}), line);	//4
		
		

		line+=2;
		rs.addAll(createParameters(new String[]{"1"}), line);	//6
		line+=1;
		rs.addAll(createParameters(new String[]{"3"}), line);	//7
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);	//8
		line+=1;
		rs.addAll(createParameters(new String[]{"3"}), line);	//9
		

		line+=2;
		rs.addAll(createParameters(new String[]{"1","2"}), line);	//11
		line+=1;
		rs.addAll(createParameters(new String[]{"3"}), line);	//12
		line+=1;
		rs.addAll(createParameters(new String[]{"3"}), line);	//13
		line+=1;
		rs.addAll(createParameters(new String[]{"3","4"}), line);	//14
		
		
		return rs;
	}
	
	public static List<String> createParameters(String[] is) {
		ArrayList<String> pars = new ArrayList<String>();
		for ( String val : is ){
			pars.add(val);
		}
		return pars;
	}
}
