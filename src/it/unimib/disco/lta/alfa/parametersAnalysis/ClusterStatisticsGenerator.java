package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctPattern;
import it.unimib.disco.lta.alfa.logging.Logger;
import it.unimib.disco.lta.alfa.utils.MathUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;



/**
 * This class generate statistics on clusters of RuleParameters elements.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ClusterStatisticsGenerator {
	
	private int linesToAnalyzePerCluster = -1;

	public int getLinesToAnalyzePerCluster() {
		return linesToAnalyzePerCluster;
	}

	public void setLinesToAnalyzePerCluster(int linesToAnalyzePerCluster) {
		this.linesToAnalyzePerCluster = linesToAnalyzePerCluster;
	}

	public List<ClusterStatistics> calculateClustersStatistics(Collection<Cluster<RuleParameter>> clusters,File file, RTableExporter tableExporter, ArrayList<TraceData> tracesData, List<SlctPattern> slctPatterns) throws IOException{

		


		HashMap<String,Pattern> rulesMap = new HashMap<String, Pattern>();
		
		if ( slctPatterns != null ){
			for ( SlctPattern slctPattern : slctPatterns ){
				rulesMap.put(slctPattern.getId(), slctPattern.getPattern());
			}
		}
		
		List<ClusterStatistics> statistics = new ArrayList<ClusterStatistics>(clusters.size());
		
		for ( Cluster<RuleParameter> c : clusters ){
			ClusterStatistics cs = calculateClusterStatistics(c,file,tableExporter,tracesData,slctPatterns);
			statistics.add(cs);
		}
		
		return statistics;
	}

	public ClusterStatistics calculateClusterStatistics(Cluster<RuleParameter> c, File file, RTableExporter tableExporter, ArrayList<TraceData> tracesData, List<SlctPattern> slctPatterns) throws IOException{
		Logger.info("Calculating statistics for cluster "+c.getSignature());

		ClusterParameterAnalyzer ca = new ClusterParameterAnalyzer(tableExporter,c);
		ca.setMaxLinesToAnalyze(linesToAnalyzePerCluster);

		ParameterAnalysisResult res;

		res = ca.analyze(file);



		double distinctValuesMean = -1;

		double distinctValuesOccurrenciesMean = -1;
		double distinctValuesOccurrenciesMeanStdDev = -1;

		double distinctValuesOccurrenciesMedian = -1;
		double distinctValuesOccurrenciesMedianStdDev = -1;

		double distinctValuesDistanceAvgMean = -1;
		double distinctValuesDistanceAvgMeanStdDev = -1;

		double distinctValuesDistanceStdDevMean = -1;
		double distinctValuesDistanceStdDevMeanStdDev = -1;

		double distancesMedianMean = -1;
		double distancesMedianMeanStdDev = -1;

		double distancesThirdQuartileMean = -1;
		double distancesThirdQuartileStdDev = -1;

		//Calculate the values that the parameter can assume
		double[] totalValues = new double[tracesData.size()];
		int trace = 0;

		Set<String> sharedValues = new HashSet<String>();
		Set<String> allValues = new HashSet<String>();

		int tracesNumber = tracesData.size();

		for ( TraceData traceData : tracesData ){
			
			if ( traceData.getLineCount() == 0 ){
				continue;
			}
			
			Set<String> values = new HashSet<String>();
			for ( RuleParameter r : c.getElements()){
				RuleStatistics rs = traceData.getRulesStatistics().get(r.getRuleName());
				Set<String> parvalues;
				try {
					parvalues = rs.getParameterValues(r.getParameterPos());
					values.addAll(parvalues);
				} catch (RuleStatisticsException e) {

				}

			}
			//System.out.println("Values size "+values.size());
			allValues.addAll(values);

			if ( trace == 0 ){
				sharedValues.addAll(values);
			} else {
				sharedValues.retainAll(values);
			}

			totalValues[trace] = values.size();

			++trace;
		}


		distinctValuesMean = MathUtil.getMean(totalValues); 

		double distinctValuesStDed = MathUtil.getStdDev(totalValues);

		int sharedValuesNumber = sharedValues.size();
		int distinctValuesNumber = allValues.size();


		distinctValuesOccurrenciesMean = res.getValuesOccurrenciesMeanMean(); 
		distinctValuesOccurrenciesMeanStdDev = res.getValuesOccurrenciesMeanStdDev();


		distinctValuesDistanceAvgMean = res.getGlobalParametersDistanceAvgMean();
		distinctValuesDistanceAvgMeanStdDev = res.getGlobalParametersDistanceAvgStdDev();



		distinctValuesDistanceStdDevMean = res.getGlobalParametersDistanceStdDevMean();
		distinctValuesDistanceStdDevMeanStdDev = res.getGlobalParametersDistanceStdDevStdDev();



		distancesMedianMean = res.getGlobalParametersDistanceMedianMean();
		distancesMedianMeanStdDev = res.getGlobalParametersDistanceMedianStdDev();

		distancesThirdQuartileMean = res.getDistancesThirdQuartileMean();
		distancesThirdQuartileStdDev = res.getDistancesThirdQuartileStdDev();

		double eventDistancesMeanMean = res.getEventDistancesMeanMean();
		double eventDistancesMeanMeanStdDev = res.getEventDistancesMeanStdDev();


		double eventDistancesStdDevMean = res.getEventDistancesStdDevMean();
		double eventDistancesStdDevMeanStdDev = res.getEventDistancesStdDevStdDev();


		double eventDistancesMedianMean = res.getEventDistancesMedianMean();
		double eventDistancesMedianMeanStdDev = res.getEventDistancesMedianStdDev();









		double riMedian = res.getRiMedianMean();
		double raMedian = res.getRaMedianMean();
		double goMedian = res.getGoMedianMean();
		double sameMedian = res.getSameMedianMean();

			return new ClusterStatistics(
					c,
					tracesNumber,
					sharedValuesNumber,
					distinctValuesNumber,
					distinctValuesMean,
					distinctValuesStDed,
					distinctValuesOccurrenciesMean,
					distinctValuesOccurrenciesMeanStdDev,
					distinctValuesDistanceAvgMean,
					distinctValuesDistanceAvgMeanStdDev,
					distinctValuesDistanceStdDevMean,
					distinctValuesDistanceStdDevMeanStdDev,
					distancesMedianMean,
					distancesMedianMeanStdDev,
					eventDistancesMeanMean,
					eventDistancesMeanMeanStdDev,
					eventDistancesStdDevMean,
					eventDistancesStdDevMeanStdDev,
					eventDistancesMedianMean,
					eventDistancesMedianMeanStdDev,
					distancesThirdQuartileMean,
					distancesThirdQuartileStdDev,
					riMedian,
					raMedian,
					goMedian,
					sameMedian
			);
		}
	
}
