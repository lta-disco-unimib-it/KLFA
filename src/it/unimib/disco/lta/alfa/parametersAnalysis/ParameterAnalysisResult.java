package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.utils.MathUtil;

import java.util.ArrayList;
import java.util.Collection;


public class ParameterAnalysisResult {

	private AnalysisResult analysisResult = new AnalysisResult(true);
	private double[] valuesAppearanceMeans;
	private double[] eventDistancesMean;
	private double[] eventDistancesStdDev;
	private double[] eventDistancesMedian;
	private double[] allParametersDistanceStdDev;
	private static final String commonKey = "";
	
	public ParameterAnalysisResult() {
		
	}

	public void addStat(ArrayList<String> parameters) {
		analysisResult.addStat(commonKey, parameters);
	}


	public double getGlobalParametersDistanceAvgMean() {
		double[] avgs = getAllParametersDistanceAvgs();
		return MathUtil.getMean(avgs);
	}

	public double getGlobalParametersDistanceAvgStdDev() {
		double[] avgs = getAllParametersDistanceAvgs();
		return MathUtil.getStdDev(avgs);
	}
	
	private double[] getAllParametersDistanceAvgs() {
		ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
		
		double avgs[] = new double[tracesData.size()];
		
		int i = 0;
		for ( TraceData traceData : tracesData ){
			//We have only one rule
			RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
			
			//We have only one parameter
			try {
				avgs[i]= stats.getParametersDistanceAvg()[0];
			} catch (RuleStatisticExceptionNoParameters e) {
				avgs[i]= 0;
			}
			++i;
			
		}
		return avgs;
	}

	public double getGlobalParametersDistanceMedianMean() {
		double[] avgs = getAllParametersMedians();
		return MathUtil.getMean(avgs);
	}
	
	private double[] getAllParametersMedians() {
		ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
		
		double avgs[] = new double[tracesData.size()];
		
		int i = 0;
		for ( TraceData traceData : tracesData ){
			//We have only one rule
			RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
			
			//We have only one parameter
			try {
				avgs[i]= stats.getParametersDistanceMedian()[0];
			} catch (RuleStatisticExceptionNoParameters e) {
				avgs[i]=0;
			}
			++i;
			
		}
		return avgs;
	}

	public double getGlobalParametersDistanceMedianStdDev() {
		double[] avgs = getAllParametersMedians();
		return MathUtil.getStdDev(avgs);
	}

	public void newLine() {
		analysisResult.newLine();
	}

	public double getGlobalParametersDistanceStdDevMean() {
		double[] avgs = getAllParametersDistanceStdDev();
		return MathUtil.getMean(avgs);
	}
	
	public double getGlobalParametersDistanceStdDevStdDev() {
		double[] avgs = getAllParametersDistanceStdDev();
		return MathUtil.getStdDev(avgs);
	}

	private double[] getAllParametersDistanceStdDev() {
		if ( allParametersDistanceStdDev == null ){
			ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();

			allParametersDistanceStdDev = new double[tracesData.size()];

			int i = 0;
			for ( TraceData traceData : tracesData ){
				//We have only one rule
				RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);

				//We have only one parameter
				try {
					allParametersDistanceStdDev[i]= stats.getParametersDistanceStdDev()[0];
				} catch (RuleStatisticExceptionNoParameters e) {
					allParametersDistanceStdDev[i]= 0;
				}
				++i;

			}
		}
		return allParametersDistanceStdDev;
	}

	public void newTrace() {
		analysisResult.newTrace();
	}

	public double getParametersDistanceAvgMean(int trace) {
		TraceData traceData = analysisResult.getTracesDatas().get(trace);
		RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
		
		//We have only one parameter
		try {
			return stats.getParametersDistanceAvg()[0];
		} catch (RuleStatisticExceptionNoParameters e) {
			return 0;
		}
		
	}

	public double getParametersDistanceStdDevMean(int trace) {
		TraceData traceData = analysisResult.getTracesDatas().get(trace);
		RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
		
		//We have only one parameter
		try {
			return stats.getParametersDistanceStdDev()[0];
		} catch (RuleStatisticExceptionNoParameters e) {
			return 0;
		}
	}

	public double getParametersDistanceMedianMean(int trace) {
		TraceData traceData = analysisResult.getTracesDatas().get(trace);
		RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
		
		//We have only one parameter
		try {
			return stats.getParametersDistanceStdDev()[0];
		} catch (RuleStatisticExceptionNoParameters e) {
			return 0;
		}
	}

	
	/**
	 * Return the mean of the distances between the events in the different traces
	 * 
	 * @return
	 */
	public double getEventDistancesMedianMean(){
		double[] distances = getAllEventDistancesMedian();
		return MathUtil.getMean(distances);
	}

	/**
	 * Return the std dev of the distances between the events in the different traces
	 * 
	 * @return
	 */
	public double getEventDistancesMedianStdDev(){
		double[] distances = getAllEventDistancesMedian();
		return MathUtil.getStdDev(distances);
	}
	
	
	/**
	 * Return the mean of the distances between the events in the different traces
	 * 
	 * @return
	 */
	public double getEventDistancesStdDevMean(){
		double[] distances = getAllEventDistancesStdDev();
		return MathUtil.getMean(distances);
	}

	/**
	 * Return the std dev of the distances between the events in the different traces
	 * 
	 * @return
	 */
	public double getEventDistancesStdDevStdDev(){
		double[] distances = getAllEventDistancesStdDev();
		return MathUtil.getStdDev(distances);
	}
	
	
	/**
	 * Return the mean of the distances between the events in the different traces
	 * 
	 * @return
	 */
	public double getEventDistancesMeanMean(){
		double[] distances = getAllEventDistancesMean();
		return MathUtil.getMean(distances);
	}
	
	/**
	 * Return the std dev of the distances between the events in the different traces
	 * 
	 * @return
	 */
	public double getEventDistancesMeanStdDev(){
		double[] distances = getAllEventDistancesMean();
		return MathUtil.getStdDev(distances);
	}
	
	/**
	 * Return the median of the distances between the events in the different traces
	 * 
	 * @return
	 */
	public double getEventDistancesMeanMedian(){
		double[] distances = getAllEventDistancesMean();
		return MathUtil.getMedian(distances);
	}

	
	/**
	 * For each trace return the median of the distances between two events
	 * @return
	 */
	private double[] getAllEventDistancesMedian() {
		if ( eventDistancesMedian == null ){
			ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
			eventDistancesMedian = new double[tracesData.size()];

			for( int i = 0; i < tracesData.size(); ++i ){
				TraceData traceData = tracesData.get(i);
				RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
				eventDistancesMedian[i] = stats.getEventDistancesMedian();
			}
		}
		return eventDistancesMedian;
	}

	
	
	/**
	 * For each trace return the std dev of the distance between two events
	 * @return
	 */
	private double[] getAllEventDistancesStdDev() {
		if ( eventDistancesStdDev == null ){
			ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
			eventDistancesStdDev = new double[tracesData.size()];

			for( int i = 0; i < tracesData.size(); ++i ){
				TraceData traceData = tracesData.get(i);
				RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
				eventDistancesStdDev[i] = stats.getEventDistancesStdDev();
			}
		}
		return eventDistancesStdDev;
	}
	
	
	/**
	 * Return for each trace the distance mean between two events
	 * 
	 * @return
	 */
	private double[] getAllEventDistancesMean() {
		if ( eventDistancesMean == null ){
			ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
			eventDistancesMean = new double[tracesData.size()];

			for( int i = 0; i < tracesData.size(); ++i ){
				TraceData traceData = tracesData.get(i);
				RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
				eventDistancesMean[i] = stats.getEventDistancesMean();
			}
		}
		return eventDistancesMean;
	}
	
	/**
	 * Return for each trace the distance mean between two events
	 * 
	 * @return
	 * @throws RuleStatisticExceptionNoParameters 
	 */
	private double[] getValuesOccurrenciesMeans() {
		if ( valuesAppearanceMeans == null ){
			ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
			valuesAppearanceMeans = new double[tracesData.size()];

			for( int i = 0; i < tracesData.size(); ++i ){
				TraceData traceData = tracesData.get(i);
				RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
				
				
				try {
					valuesAppearanceMeans[i] = stats.getParametersOccurrenciesMean()[0];
				} catch (RuleStatisticExceptionNoParameters e) {
					valuesAppearanceMeans[i] = 0;
				}
				
			}
		}
		return valuesAppearanceMeans;
	}



	/**
	 * Return the mean of the mean of the number of time a different value appear
	 *  
	 * @return
	 * @throws RuleStatisticExceptionNoParameters 
	 */
	public double getValuesOccurrenciesMeanMean() {
		double[] means = getValuesOccurrenciesMeans();
		return MathUtil.getMean(means);
	}

	/**
	 * Return the std dev of the mean of the number of time a different value appear
	 *  
	 * @return
	 * @throws RuleStatisticExceptionNoParameters 
	 */
	public double getValuesOccurrenciesMeanStdDev() {
		double[] means = getValuesOccurrenciesMeans();
		return MathUtil.getStdDev(means);
	}
	
	
	
	/**
	 * Return the median of the mean of the number of time a different value appear
	 *  
	 * @return
	 * @throws RuleStatisticExceptionNoParameters 
	 */
	public double getValueApperanceMeanMedian() {
		double[] means = getValuesOccurrenciesMeans();
		return MathUtil.getMedian(means);
	}

	public double getDistancesThirdQuartileMean() {
		double[] pcs = getAllPercentiles();
		return MathUtil.getMean(pcs);
	}
	
	public double getDistancesThirdQuartileStdDev() {
		double[] pcs = getAllPercentiles();
		return MathUtil.getStdDev(pcs);
	}
		
	public double[] getAllPercentiles(){	
		ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
		
		double avgs[] = new double[tracesData.size()];
		
		int i = 0;
		for ( TraceData traceData : tracesData ){
			//We have only one rule
			RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
			
			//We have only one parameter
			try {
				avgs[i]= stats.getParametersDistancePercentile(75)[0];
			} catch (RuleStatisticExceptionNoParameters e) {
				avgs[i]=0;
			}
			++i;
			
		}
		return avgs;
	}

	public double[] getRiMedians() {
		
		ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
		
		double avgs[] = new double[tracesData.size()];
		
		int i = 0;
		for ( TraceData traceData : tracesData ){
			//We have only one rule
			RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
			
			//We have only one parameter
			try {
				avgs[i]= stats.getRiMedians()[0];
			} catch (RuleStatisticExceptionNoParameters e) {
				avgs[i]=0;
			}
			++i;
			
		}
		return avgs;
		
	}

	public double getRiMedianMean() {
		return MathUtil.getMean(getRiMedians());
	}


	public double[] getRaMedians() {
		
		ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
		
		double avgs[] = new double[tracesData.size()];
		
		int i = 0;
		for ( TraceData traceData : tracesData ){
			//We have only one rule
			RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
			
			//We have only one parameter
			try {
				avgs[i]= stats.getRaMedians()[0];
			} catch (RuleStatisticExceptionNoParameters e) {
				avgs[i]=0;
			}
			++i;
			
		}
		return avgs;
		
	}

	public double getRaMedianMean() {
		return MathUtil.getMean(getRaMedians());
	}
	
	
	public double[] getGoMedians() {
		
		ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
		
		double avgs[] = new double[tracesData.size()];
		
		int i = 0;
		for ( TraceData traceData : tracesData ){
			//We have only one rule
			RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
			
			//We have only one parameter
			try {
				avgs[i]= stats.getGoMedians()[0];
			} catch (RuleStatisticExceptionNoParameters e) {
				avgs[i]=0;
			}
			++i;
			
		}
		return avgs;
		
	}
	
	public double[] getSameMedians() {
		
		ArrayList<TraceData> tracesData = analysisResult.getTracesDatas();
		
		double avgs[] = new double[tracesData.size()];
		
		int i = 0;
		for ( TraceData traceData : tracesData ){
			//We have only one rule
			RuleStatistics stats = traceData.getRulesStatistics().get(commonKey);
			
			//We have only one parameter
			try {
				avgs[i]= stats.getSameMedians()[0];
			} catch (RuleStatisticExceptionNoParameters e) {
				avgs[i]=0;
			}
			++i;
			
		}
		return avgs;
		
	}

	public double getGoMedianMean() {
		return MathUtil.getMean(getGoMedians());
	}

	public double getSameMedianMean() {
		return MathUtil.getMean(getSameMedians());
	}

}
