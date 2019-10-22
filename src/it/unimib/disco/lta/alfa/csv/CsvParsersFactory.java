package it.unimib.disco.lta.alfa.csv;

/**
 * This factory manages the creation of csv parsers
 * 
 * @author Fabrizio Pastore
 *
 */
public class CsvParsersFactory {

	public static CsvReader createNewCsvReader() {
		return new OpenCsvReader();
	}

	public static CsvWriter createNewCsvWriter() {
		return new OpenCsvWriter();
	}

}
