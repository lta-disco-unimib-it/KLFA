package it.unimib.disco.lta.alfa.eventsDetection.slct;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unimib.disco.lta.alfa.eventsDetection.EventTypesDetectorException;
import it.unimib.disco.lta.alfa.logging.Logger;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;
import it.unimib.disco.lta.alfa.utils.FileUtils;

import org.junit.Ignore;
import org.junit.Test;

public class Bug164 {
	
	@Test
	public void testRunner() throws SlctRunnerException{
		File fileN = ArtifactsProvider.getBugFile("164-native");
		fileN.mkdir();
		File fileJ = ArtifactsProvider.getBugFile("164-java");
		fileJ.mkdir();
		SlctRunner rj = new SlctRunner(fileJ);
		SlctRunner rn = new SlctRunner(fileN);
		rn.setSlctExecutablePath("/opt/slct/slct");
		
		File file = ArtifactsProvider.getBugFile("164.log");
		List<Pattern> rulesJ = rj.getRules(file);
		
		List<Pattern> rulesN = rn.getRules(file);
		
		assertEquals(rulesJ, rulesN);
	}
	
	
	@Test
	public void testLauncher() throws SlctRunnerException, IOException{
		//SlctJavaLauncher launcher = new SlctJavaLauncher();
		SlctLauncher slauncher = new SlctNativeLauncher("/opt/slct/slct", true);
		File file = ArtifactsProvider.getBugFile("164.log");
		//		File outFile = ArtifactsProvider.getBugFile("164.outliers.log");
		File nativeOutFile = ArtifactsProvider.getBugFile("164.native-outliers.log");
		int linesNumber = FileUtils.getNumberOfLines(file);
		
		int support = (int) (0.05*linesNumber);
		if ( support == 0 ){
			support = 2;
		}


		//List<Pattern> res = launcher.run(support, file, outFile);

		List<Pattern> nativeRes = slauncher.run(support, file, nativeOutFile);

		int outliersNumber = FileUtils.getNumberOfLines(file);
		
		
		System.out.println("Patterns native: ");
		for ( Pattern pattern : nativeRes ){
			System.out.println(pattern);	
		}

		System.out.println("Outliers: "+outliersNumber);
		
		assertEquals(0,outliersNumber);
	}
	
	
	@Test
	public void testSlctRunner() throws SlctRunnerException, IOException, EventTypesDetectorException{
		
		File wdir = ArtifactsProvider.getBugFile("164-slctRunner");
		wdir.mkdirs();
		
		SlctRunner runner = new SlctRunner(wdir);
		runner.setSlctExecutablePath("/opt/slct/slct");
		
		
		File file = ArtifactsProvider.getBugFile("164.log");

		
		
		List<Pattern> patterns = runner.getRules(file);
		
		List<SlctPattern> slctPatterns = new ArrayList<SlctPattern>();
		
		System.out.println("Patterns slct runner: ");
		int i = 0;
		for ( Pattern pattern : patterns ){
			System.out.println(pattern);
			SlctPattern p = new SlctPattern(""+(i++), pattern);
			slctPatterns.add(p);
		}

		int outliersNumber = FileUtils.getNumberOfLines(runner.getOutliersFile());

		System.out.println("Outliers: "+outliersNumber);
		
		
		
		SlctEventsParser eventsParser = new SlctEventsParser();
		
		
		eventsParser.setSlctPattern(slctPatterns);
		
		eventsParser.setComponentId("44");
		eventsParser.setHashOutliers(true);
		File csvFile = new File(wdir,"44.csv");
		
		eventsParser.process(file, csvFile);
		
		
		HashMap<String, String> unmatchedLines = eventsParser.getUnmatchedLines();
		
		
		System.out.println("Unmateched: "+unmatchedLines);
		
		assertEquals(0,unmatchedLines.size());
	}
	
	
	@Test
	public void testSlctRunner39() throws SlctRunnerException, IOException, EventTypesDetectorException{
		
		File wdir = ArtifactsProvider.getBugFile("164-39-slctRunner");
		wdir.mkdirs();
		
		SlctRunner runner = new SlctRunner(wdir);
		runner.setSlctExecutablePath("/opt/slct/slct");
		
		
		File file = ArtifactsProvider.getBugFile("164.39.log");

		
		
		List<Pattern> patterns = runner.getRules(file);
		
		List<SlctPattern> slctPatterns = new ArrayList<SlctPattern>();
		
		System.out.println("Patterns slct runner: ");
		int i = 0;
		for ( Pattern pattern : patterns ){
			System.out.println(pattern);
			SlctPattern p = new SlctPattern(""+(i++), pattern);
			slctPatterns.add(p);
		}

		int outliersNumber = FileUtils.getNumberOfLines(runner.getOutliersFile());

		System.out.println("Outliers: "+outliersNumber);
		
		
		
		SlctEventsParser eventsParser = new SlctEventsParser();
		
		
		eventsParser.setSlctPattern(slctPatterns);
		
		eventsParser.setComponentId("39");
		eventsParser.setHashOutliers(true);
		File csvFile = new File(wdir,"39.csv");
		
		eventsParser.process(file, csvFile);
		
		
		HashMap<String, String> unmatchedLines = eventsParser.getUnmatchedLines();
		
		
		System.out.println("Unmatched: "+unmatchedLines);
		
		assertEquals(0,unmatchedLines.size());
	}

	
	@Test
	public void testSlctRunner37() throws SlctRunnerException, IOException, EventTypesDetectorException{
		
		File wdir = ArtifactsProvider.getBugFile("164-37-slctRunner");
		wdir.mkdirs();
		
		SlctRunner runner = new SlctRunner(wdir);
		runner.setSlctExecutablePath("/opt/slct/slct");
		
		
		File file = ArtifactsProvider.getBugFile("164.37.log");

		
		
		List<Pattern> patterns = runner.getRules(file);
		
		List<SlctPattern> slctPatterns = new ArrayList<SlctPattern>();
		
		System.out.println("Patterns slct runner: ");
		int i = 0;
		for ( Pattern pattern : patterns ){
			System.out.println(pattern);
			SlctPattern p = new SlctPattern(""+(i++), pattern);
			slctPatterns.add(p);
		}

		int outliersNumber = FileUtils.getNumberOfLines(runner.getOutliersFile());

		System.out.println("Outliers: "+outliersNumber);
		
		
		
		SlctEventsParser eventsParser = new SlctEventsParser();
		
		
		eventsParser.setSlctPattern(slctPatterns);
		
		eventsParser.setComponentId("37");
		eventsParser.setHashOutliers(true);
		File csvFile = new File(wdir,"37.csv");
		
		eventsParser.process(file, csvFile);
		
		
		HashMap<String, String> unmatchedLines = eventsParser.getUnmatchedLines();
		
		
		System.out.println("Unmatched: "+unmatchedLines);
		
		assertEquals(0,unmatchedLines.size());
	}
	
	
	@Test
	public void testSlctRunner37Java() throws SlctRunnerException, IOException, EventTypesDetectorException{
		
		File wdir = ArtifactsProvider.getBugFile("164-37-slctRunnerJava");
		wdir.mkdirs();
		
		SlctRunner runner = new SlctRunner(wdir);
		//runner.setSlctExecutablePath("/opt/slct/slct");
		
		
		File file = ArtifactsProvider.getBugFile("164.37.log");

		
		
		List<Pattern> patterns = runner.getRules(file);
		
		List<SlctPattern> slctPatterns = new ArrayList<SlctPattern>();
		
		System.out.println("Patterns slct runner: ");
		int i = 0;
		for ( Pattern pattern : patterns ){
			System.out.println(pattern);
			SlctPattern p = new SlctPattern(""+(i++), pattern);
			slctPatterns.add(p);
		}

		int outliersNumber = FileUtils.getNumberOfLines(runner.getOutliersFile());

		System.out.println("Outliers: "+outliersNumber);
		
		
		
		SlctEventsParser eventsParser = new SlctEventsParser();
		
		
		eventsParser.setSlctPattern(slctPatterns);
		
		eventsParser.setComponentId("37");
		eventsParser.setHashOutliers(true);
		File csvFile = new File(wdir,"37.csv");
		
		eventsParser.process(file, csvFile);
		
		
		HashMap<String, String> unmatchedLines = eventsParser.getUnmatchedLines();
		
		
		System.out.println("Unmatched: "+unmatchedLines);
		
		assertEquals(0,unmatchedLines.size());
	}
	
	
	
	@Test
	public void testSlctRunner34Java() throws SlctRunnerException, IOException, EventTypesDetectorException{
		
		File wdir = ArtifactsProvider.getBugFile("164-34-slctRunnerJava");
		wdir.mkdirs();
		
		SlctRunner runner = new SlctRunner(wdir);
		//runner.setSlctExecutablePath("/opt/slct/slct");
		
		
		File file = ArtifactsProvider.getBugFile("164.34.log");

		
		
		List<Pattern> patterns = runner.getRules(file);
		
		List<SlctPattern> slctPatterns = new ArrayList<SlctPattern>();
		
		System.out.println("Patterns slct runner: ");
		int i = 0;
		for ( Pattern pattern : patterns ){
			System.out.println(pattern);
			SlctPattern p = new SlctPattern(""+(i++), pattern);
			slctPatterns.add(p);
		}

		System.out.println("Patterns number: "+patterns.size());
		
		int outliersNumber = FileUtils.getNumberOfLines(runner.getOutliersFile());

		System.out.println("Outliers: "+outliersNumber);
		
		
		
		SlctEventsParser eventsParser = new SlctEventsParser();
		
		
		eventsParser.setSlctPattern(slctPatterns);
		
		eventsParser.setComponentId("34");
		eventsParser.setHashOutliers(true);
		File csvFile = new File(wdir,"34.csv");
		
		eventsParser.process(file, csvFile);
		
		
		HashMap<String, String> unmatchedLines = eventsParser.getUnmatchedLines();
		
		
		System.out.println("Unmatched: "+unmatchedLines);
		
		assertEquals(0,unmatchedLines.size());
	}
	
	@Test
	public void testPattern1Equal(){
		String patternString = "- Releasing hibernate session SessionImpl PersistenceContext entityKeys EntityKey com.xpn.xwiki.objects.LargeStringProperty component id,name id -1468381210, name groups , EntityKey com.xpn.xwiki.doc.XWikiDocument 104408758 , EntityKey com.xpn.xwiki.objects.LargeStringProperty component id,name id 1929900568, name users , EntityKey com.xpn.xwiki.objects.LargeStringProperty component id,name id -1468381210, name users , EntityKey com.xpn.xwiki.objects.StringProperty component id,name id -944455444, name webbgcolor , EntityKey com.xpn.xwiki.objects.StringProperty component id,name id -944455444, name colorTheme , EntityKey com.xpn.xwiki.doc.XWikiDocument 981637980 , EntityKey com.xpn.xwiki.objects.StringProperty component id,name id -944455444, name plugins , EntityKey com.xpn.xwiki.objects.IntegerProperty component id,name id 1929900568, name allow , EntityKey com.xpn.xwiki.objects.StringProperty component id,name id 1929900569, name levels , EntityKey com.xpn.xwiki.objects.BaseObject 1929900568 , EntityKey com.xpn.xwiki.objects.LargeStringProperty component id,name id 1929900568, name groups , EntityKey com.xpn.xwiki.objects.BaseObject 1929900569 , EntityKey com.xpn.xwiki.objects.LargeStringProperty component id,name id 1929900569, name users , EntityKey com.xpn.xwiki.objects.IntegerProperty component id,name id -944455444, name renderXWikiRadeoxRenderer , EntityKey com.xpn.xwiki.objects.BaseObject -1468381210 , EntityKey com.xpn.xwiki.objects.StringProperty component id,name id -944455444, name ad_clientid , EntityKey com.xpn.xwiki.objects.StringProperty component id,name id -944455444, name convertmail , EntityKey com.xpn.xwiki.objects.LongProperty component id,name id -944455444, name upload_maxsize , EntityKey com.xpn.xwiki.objects.StringProperty component id,name id -1468381210, name levels , EntityKey com.xpn.xwiki.objects.IntegerProperty component id,name id -944455444, name renderXWikiGroovyRenderer , EntityKey com.xpn.xwiki.objects.IntegerProperty component id,name id -944455444, name renderXWikiVelocityRenderer , EntityKey com.xpn.xwiki.objects.IntegerProperty component id,name id -1468381210, name allow , EntityKey com.xpn.xwiki.objects.LargeStringProperty component id,name id 1929900569, name groups , EntityKey com.xpn.xwiki.objects.StringProperty component id,name id 1929900568, name levels , EntityKey com.xpn.xwiki.objects.IntegerProperty component id,name id 1929900569, name allow , EntityKey com.xpn.xwiki.objects.BaseObject -944455444 , EntityKey com.xpn.xwiki.objects.StringProperty component id,name id -944455444, name macros_wiki ,collectionKeys ActionQueue insertions updates deletions collectionCreations collectionRemovals collectionUpdates";
		
		Pattern pattern = Pattern.compile(patternString);
		
		Matcher m = pattern.matcher(patternString);
		
		assertTrue ( m.find() );
		
		
		
	}
	
	@Test
	public void testPattern2Equal(){
		String patternString = "- Taking session from context SessionImpl PersistenceContext entityKeys EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 11 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 12 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 7 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 5 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 1 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 9 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 2 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 3 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 10 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 4 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 8 , EntityKey com.xpn.xwiki.doc.rcs.XWikiRCSNodeInfo component docId,version1,version2 docId 1785627952, version2 , 1, version1 , 6 ,collectionKeys ActionQueue insertions updates deletions collectionCreations collectionRemovals collectionUpdates ";
		
		Pattern pattern = Pattern.compile(patternString);
		
		Matcher m = pattern.matcher(patternString);
		
		assertTrue ( m.find() );
		
		
		
	}
	
}

