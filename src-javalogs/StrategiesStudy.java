import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerGlobal;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToAccess;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToInstantiation;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatistics.ParameterStat;
import it.unimib.disco.lta.alfa.utils.MathUtil;


public class StrategiesStudy {

	public static void main(String args[]){
		//A B A B A B C A C A C A
		
		int dists[];
		
		dists = new int[]{2,2,2,2,3,2,2,2,2,2};
		
		System.out.println(
				
		MathUtil.getMean(dists) + " " + 
		MathUtil.getMedian(dists)+ " " + 
		MathUtil.getStdDev(dists)+ " " +
		";"
		
		);
		
		
		//A B A B A B A B A B C A C A C A C A C A
		dists = new int[]{2,2,2,2,2,2,2,2,3,2,2,2,2,2,2,2,2,};
		
		System.out.println(
				
		MathUtil.getMean(dists) + " " + 
		MathUtil.getMedian(dists)+ " " + 
		MathUtil.getStdDev(dists)+ " " +
		";"
		);
		
		//A B A B A B C A C A C A C A C A C A A B A B A B 
		dists = new int[]{2,2,2,2,3,2,2,2,2,2,2,2,2,2,1,14,2,2,2,2};
		
		System.out.println(
				
		MathUtil.getMean(dists) + " " + 
		MathUtil.getMedian(dists)+ " " + 
		MathUtil.getStdDev(dists)+ " " +
		";"
		
		);
		
		//A B A B A B C D C D C D
		
		dists = new int[]{2,2,2,2,2,2,2,2};
		
		System.out.println(
				
		MathUtil.getMean(dists) + " " + 
		MathUtil.getMedian(dists)+ " " + 
		MathUtil.getStdDev(dists)+ " " +
		";"
		
		);
		
		//A B B B A B C D D D C D
		
		dists = new int[]{1,1,2,2,1,1,2,2};
		
		System.out.println(
				
		MathUtil.getMean(dists) + " " + 
		MathUtil.getMedian(dists)+ " " + 
		MathUtil.getStdDev(dists)+ " " +
		";"
		
		);
		
		String[] values;
		
		//GO
		values = new String[]{
				"X","Y","Z",
				"A", "B", "C",
				"H","T","M","N",
				"A", "B", "C",
				"M","L","T","K",
				"A", "B", "C",
				"J","K","U",
				"A", "B", "C",
				"Z","Y",
				"A", "B", "C",
				"L","T","K","R","L","U",
				"A", "B", "C",
				
		};
		ParameterStat ps = getPS(values);
		System.out.println("\n\n\n");
		System.out.println("Value: "+getSame(values));
		System.out.println("RA: "+getRA(values));
		System.out.println("RI: "+getRI(values));
		System.out.println("GO: "+getGO(values));
		System.out.println(
		ps.getDistanceMean() + " " + 
		ps.getDistanceMedian() + " " + 
		ps.getDistanceStdDev() + " " +
		ps.getDistanceFirstMean() + " " +
		ps.getDistanceFirstMedian() + " " +
		ps.getRAMedian() + " " +
		ps.getRIMedian() + " " +
		
		ps.getGOMedian() + " " +
		";"
		
		);
		
		
		//X Y Z T A B A X Y B A B X Y Z C D C Y Z D C D
		values = new String[]{
				"X","Y","Z","T",
				"A","B","A","X","Y","B","A","B",
				"X","Y","Z",
				"C","D","C","Y","Z","D","C","D",
				"X","Y",
				"E","F","E","T","Z","F","E","F",
				"X","Y","Z","T",
				"G","H","G","Y","Z","G","H","G",
		};
		ps = getPS(values);
		System.out.println("\n\n\n");
		System.out.println("Value: "+getSame(values));
		System.out.println("RA: "+getRA(values));
		System.out.println("RI: "+getRI(values));
		System.out.println("GO: "+getGO(values));
		System.out.println(
		ps.getDistanceMean() + " " + 
		ps.getDistanceMedian() + " " + 
		ps.getDistanceStdDev() + " " +
		ps.getDistanceFirstMean() + " " +
		ps.getDistanceFirstMedian() + " " +
		ps.getRAMedian() + " " +
		ps.getRIMedian() + " " +
		
		ps.getGOMedian() + " " +
		";"
		
		);
		
		
		values = new String[]{"A","B","A","B","A","B","C","A","C","A","C","A"};
		ps = getPS(values);
		
		System.out.println("\n\n\n");
		System.out.println("Value: "+getSame(values));
		System.out.println("RA: "+getRA(values));
		System.out.println("RI: "+getRI(values));
		System.out.println("GO: "+getGO(values));
		System.out.println(
				
		ps.getDistanceMean() + " " + 
		ps.getDistanceMedian() + " " + 
		ps.getDistanceStdDev() + " " +
		ps.getDistanceFirstMean() + " " +
		ps.getDistanceFirstMedian() + " " +
		ps.getRAMedian() + " " +
		ps.getRIMedian() + " " +
		
		ps.getGOMedian() + " " +
		";"
		
		);
		
		values = new String[]{
				"A","B","A","B","A","B","C","D","C","D","C","D","E","F","E","F","E","F"};
		ps = getPS(values);
		
		System.out.println("\n\n\n");
		System.out.println("Value: "+getSame(values));
		System.out.println("RA: "+getRA(values));
		System.out.println("RI: "+getRI(values));
		System.out.println("GO: "+getGO(values));
		System.out.println(
				
		ps.getDistanceMean() + " " + 
		ps.getDistanceMedian() + " " + 
		ps.getDistanceStdDev() + " " +
		ps.getDistanceFirstMean() + " " +
		ps.getDistanceFirstMedian() + " " +
		ps.getRAMedian() + " " +
		ps.getRIMedian() + " " +
		
		ps.getGOMedian() + " " +
		";"
		
		);
		
		
		values = new String[]{
				"A","B","B","A","A","B",
				"C","D","D","C","C","D",
				"E","F","F","E","E","F",
				"E","D","D","E","E","D",
				"A","F","F","A","A","F",
				"C","B","B","C","C","B",
		};
		ps = getPS(values);
		
		System.out.println("\n\n\n");
		System.out.println("Value: "+getSame(values));
		System.out.println("RA: "+getRA(values));
		System.out.println("RI: "+getRI(values));
		System.out.println("GO: "+getGO(values));
		System.out.println(
				
		ps.getDistanceMean() + " " + 
		ps.getDistanceMedian() + " " + 
		ps.getDistanceStdDev() + " " +
		ps.getDistanceFirstMean() + " " +
		ps.getDistanceFirstMedian() + " " +
		ps.getRAMedian() + " " +
		ps.getRIMedian() + " " +
		ps.getGOMedian() + " " +
		";"
		
		);
		
		
		values = new String[]{
				"A","A","A",
				"D","D","D",
				"C","C","C",
				"F","F","F",
				"E","E","E",
				"C","C","C",
				"C","C","C",
				"D","D","D",
				"A","A","A",
				"F","F","F",
				"F","F","F",
				"E","E","E",
				"C","C","C",
				"C","C","C",
				"A","A","A",
				"D","D","D",
				"C","C","C",
				"F","F","F",
				"E","E","E",
				"C","C","C",
				"C","C","C",
				"D","D","D",
				"A","A","A",
				"F","F","F",
				"F","F","F",
				"E","E","E",
		};
		ps = getPS(values);
		
		System.out.println("\n\n\n");
		System.out.println("Value: "+getSame(values));
		System.out.println("RA: "+getRA(values));
		System.out.println("RI: "+getRI(values));
		System.out.println("GO: "+getGO(values));
		
		System.out.println(
				
		ps.getDistanceMean() + " " + 
		ps.getDistanceMedian() + " " + 
		ps.getDistanceStdDev() + " " +
		ps.getDistanceFirstMean() + " " +
		ps.getDistanceFirstMedian() + " " +
		ps.getRAMedian() + " " +
		ps.getRIMedian() + " " +
		ps.getGOMedian() + " " +
		";"
		
		);
		
	}

	private static String getGO(String[] values) {
		ValueTransformerGlobal ri = new ValueTransformerGlobal("");
		String res = "";
		for ( String val : values ){
			res+=" "+ri.getTransformedValue(val);
		}
		return res;
	}

	private static String getSame(String[] values) {
		
		String res = "";
		for ( String val : values ){
			res+=" "+val;
		}
		return res;
	}

	private static String getRI(String[] values) {
		ValueTransformerRelativeToInstantiation ri = new ValueTransformerRelativeToInstantiation("");
		String res = "";
		for ( String val : values ){
			res+=" "+ri.getTransformedValue(val);
		}
		return res;
	}

	private static String getRA(String[] values) {
		ValueTransformerRelativeToAccess ra = new ValueTransformerRelativeToAccess("");
		String res = "";
		for ( String val : values ){
			res+=" "+ra.getTransformedValue(val);
		}
		return res;
	}

	private static ParameterStat getPS(String[] strings) {
		ParameterStat ps = new ParameterStat(true);
		int c = 0;
		for(String s : strings ){
			++c;
			ps.add(s, c);
		}
		return ps;
	}
}
