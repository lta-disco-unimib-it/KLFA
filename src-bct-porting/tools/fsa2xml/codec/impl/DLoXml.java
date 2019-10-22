package tools.fsa2xml.codec.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import automata.fsa.FiniteStateAutomaton;
import tools.fsa2xml.codec.api.FSACodec;

public class DLoXml implements FSACodec {

	public static final FSACodec INSTANCE = new DLoXml();

	public FiniteStateAutomaton loadFSA(String filename) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public FiniteStateAutomaton loadFSA(File file) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveFSA(FiniteStateAutomaton fsa, String filename)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

	public void saveFSA(FiniteStateAutomaton o, File file)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

}
