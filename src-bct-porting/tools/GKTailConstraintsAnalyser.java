package tools;

import java.util.ArrayList;

import database.DataLayerException;

public interface GKTailConstraintsAnalyser {
	
	public ArrayList analyseConstraints(ArrayList al, int idMethodCall, int marker) throws DataLayerException;

}
