package it.unimib.disco.lta.alfa.parametersAnalysis;

public class ClusterStatistics {

	private double distinctValuesMean;		//mean of the number of distinct values in each trace
	private double distinctValuesStdDev;	//stddev		
	private double distinctValuesOccurrenciesMean;	//number of times a value occurs
	private double distinctValuesOccurrenciesStdDev;	//stddev
	private double distinctValuesDistanceMeanMean;		//the mean between the different traces of the mean of the distance of a same value per parameter 
	private double distinctValuesDistanceMeanStdDev;
	private double distinctValuesDistanceStdDevMean;
	private double distinctValuesDistanceStdDevStdDev;
	private double distinctValuesDistanceMedianMean;
	private double distinctValuesDistancesMedianStdDev;
	private double eventDistanceMeanMean;
	private double eventDistanceMeanStdDev;
	private double eventDistanceStdDevMean;
	private double eventDistanceStdDevStdDev;
	private double eventDistanceMedianMean;
	private double eventDistanceMedianStdDev;
	private Cluster<RuleParameter> cluster;
	private int sharedValues;						//number of shared values
	private int distinctValues;				//number of distinct values appearing in the different traces
	private int tracesNumber;
	private double distinctValuesDistanceThirdQuartileMean;
	private double distinctValuesDistanceThirdQuartileStdDev;
	private double raMedian;
	private double riMedian;
	private double goMedian;
	private double sameMedian;

	/**
	 * 
	 * @param cluster
	 * @param sharedValues			Number of values shared among different traces
	 * @param distinctValues		Number of distinct values in the different traces
	 * @param distinctValuesMean	Mean of the number of distinct values occurring in each trace
	 * @param distinctValuesStdDev	Standard deviation
	 * @param distinctValuesOccurrenciesMean	Mean of the number of occurrencies of each distinct value per trace	
	 * @param distinctValuesOccurrenciesStdDev	
	 * @param distinctValuesDistanceMeanMean	Mean of the mean distance between identical values per trace	
	 * @param distinctValuesDistanceMeanStdDev
	 * @param distinctValuesDistanceStdDevMean	Mean of the standard deviation of the mean distance between a same value per trace
	 * @param distinctValuesDistanceStdDevStdDev
	 * @param distinctValuesDistanceMedianMean	Mean of the median distance between identical values per trace  
	 * @param distinctValuesDistanceMedianStdDev
	 * @param eventDistanceMeanMean	Mean of the mean distance between identical events per trace
	 * @param eventDistanceMeanMeanStdDev
	 * @param eventDistanceStdDevMean	Mean of the standard deviation of the distance between identical events per trace
	 * @param eventDistanceStdDevMeanStdDev
	 * @param eventDistanceMedianMean Mean of the median of the distance between identical events per trace
	 * @param eventDistanceMedianMeanStdDev
	 * @param distancesThirdQuartileStdDev 
	 * @param distancesThirdQuartileMean 
	 */
	public ClusterStatistics(
			Cluster<RuleParameter> cluster,
			int tracesNumber,
			int sharedValues,
			int distinctValues,
			double distinctValuesMean, 
			double distinctValuesStdDev,
			double distinctValuesOccurrenciesMean,
			double distinctValuesOccurrenciesStdDev,
			double distinctValuesDistanceMeanMean,
			double distinctValuesDistanceMeanStdDev,
			double distinctValuesDistanceStdDevMean,
			double distinctValuesDistanceStdDevStdDev,
			double distinctValuesDistanceMedianMean, 
			double distinctValuesDistanceMedianStdDev,
			double eventDistanceMeanMean, 
			double eventDistanceMeanMeanStdDev,
			double eventDistanceStdDevMean,
			double eventDistanceStdDevMeanStdDev,
			double eventDistanceMedianMean,
			double eventDistanceMedianMeanStdDev, 
			double distinctValuesDistanceThirdQuartileMean, 
			double distinctValuesDistanceThirdQuartileStdDev,
			double riMedian,
			double raMedian,
			double goMedian,
			double sameMedian) {
		this.cluster = cluster;
		this.tracesNumber = tracesNumber;
		this.distinctValuesMean = distinctValuesMean;
		this.distinctValuesStdDev = distinctValuesStdDev;
		this.sharedValues = sharedValues;
		this.distinctValues = distinctValues;
		this.distinctValuesOccurrenciesMean = distinctValuesOccurrenciesMean;
		this.distinctValuesOccurrenciesStdDev = distinctValuesOccurrenciesStdDev;
		this.distinctValuesDistanceMeanMean = distinctValuesDistanceMeanMean;
		this.distinctValuesDistanceMeanStdDev = distinctValuesDistanceMeanStdDev;
		this.distinctValuesDistanceStdDevMean = distinctValuesDistanceStdDevMean;
		this.distinctValuesDistanceStdDevStdDev = distinctValuesDistanceStdDevStdDev;
		this.distinctValuesDistanceMedianMean = distinctValuesDistanceMedianMean;
		this.distinctValuesDistancesMedianStdDev = distinctValuesDistanceMedianStdDev;
		this.distinctValuesDistanceThirdQuartileMean = distinctValuesDistanceThirdQuartileMean;
		this.distinctValuesDistanceThirdQuartileStdDev = distinctValuesDistanceThirdQuartileStdDev;
		
		this.eventDistanceMeanMean = eventDistanceMeanMean;
		this.eventDistanceMeanStdDev = eventDistanceMeanMeanStdDev;
		this.eventDistanceStdDevMean = eventDistanceStdDevMean;
		this.eventDistanceStdDevStdDev = eventDistanceStdDevMeanStdDev;
		this.eventDistanceMedianMean = eventDistanceMedianMean;
		this.eventDistanceMedianStdDev = eventDistanceMedianMeanStdDev;
	
		this.raMedian = raMedian;
		this.riMedian = riMedian;
		this.goMedian = goMedian;
		this.sameMedian = sameMedian;
	}





	public Cluster<RuleParameter> getCluster() {
		return cluster;
	}

	public void setCluster(Cluster<RuleParameter> cluster) {
		this.cluster = cluster;
	}



	public double getDistinctValuesMean() {
		return distinctValuesMean;
	}



	public double getDistinctValuesStdDev() {
		return distinctValuesStdDev;
	}



	public double getDistinctValuesOccurrenciesMean() {
		return distinctValuesOccurrenciesMean;
	}



	public double getDistinctValuesOccurrenciesStdDev() {
		return distinctValuesOccurrenciesStdDev;
	}



	public double getDistinctValuesDistanceMeanMean() {
		return distinctValuesDistanceMeanMean;
	}



	public double getDistinctValuesDistanceMeanStdDev() {
		return distinctValuesDistanceMeanStdDev;
	}



	public double getDistinctValuesDistanceStdDevMean() {
		return distinctValuesDistanceStdDevMean;
	}



	public double getDistinctValuesDistanceStdDevStdDev() {
		return distinctValuesDistanceStdDevStdDev;
	}



	public double getDistinctValuesDistanceMedianMean() {
		return distinctValuesDistanceMedianMean;
	}



	public double getDistinctValuesDistancesMedianStdDev() {
		return distinctValuesDistancesMedianStdDev;
	}



	public double getEventDistanceMeanMean() {
		return eventDistanceMeanMean;
	}



	public double getEventDistanceMeanStdDev() {
		return eventDistanceMeanStdDev;
	}



	public double getEventDistanceStdDevMean() {
		return eventDistanceStdDevMean;
	}



	public double getEventDistanceStdDevStdDev() {
		return eventDistanceStdDevStdDev;
	}



	public double getEventDistanceMedianMean() {
		return eventDistanceMedianMean;
	}



	public double getEventDistanceMedianStdDev() {
		return eventDistanceMedianStdDev;
	}



	public int getSharedValues() {
		return sharedValues;
	}



	public int getDistinctValues() {
		return distinctValues;
	}

	public int getTracesNumber() {
		return tracesNumber;
	}


	
	//
	//Derived values
	//
	
	public double getValuesMeanOnValuesOccurrenciesMean(){
		return distinctValuesMean/distinctValuesOccurrenciesMean;
	}
	
	public double getValuesNumberOnValuesOccurrenciesPerDistanceMedian(){
		return distinctValuesMean/(distinctValuesOccurrenciesMean*distinctValuesDistanceMedianMean);
	}
	
	public double getValuesNumberOnValuesDistanceMedian(){
		return distinctValuesMean/distinctValuesDistanceMedianMean;	
	}
	
	/**
	 * Returns the percentage of distinct values shared among the different traces.
	 * Calculated as sharedValues/distinctValues.
	 * 
	 * @return
	 */
	public double getSharing(){
		return (double)sharedValues/(double)distinctValues;
	}





	public double getDistinctValuesDistancesThirdQuartileMean() {
		return distinctValuesDistanceThirdQuartileMean;
	}





	public double getDistinctValuesDistancesThirdQuartileStdDev() {
		return distinctValuesDistanceThirdQuartileStdDev;
	}





	public double getDistinctValuesDistanceThirdQuartileMean() {
		return distinctValuesDistanceThirdQuartileMean;
	}





	public double getDistinctValuesDistanceThirdQuartileStdDev() {
		return distinctValuesDistanceThirdQuartileStdDev;
	}





	public double getRaMedian() {
		return raMedian;
	}





	public double getRiMedian() {
		return riMedian;
	}





	public double getGoMedian() {
		return goMedian;
	}





	public double getSameMedian() {
		return sameMedian;
	}






	
}
