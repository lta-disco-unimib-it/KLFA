package it.unimib.disco.lta.alfa.tools;

import it.unimib.disco.lta.alfa.tools.FSAInspector.TransitionPath;

public class ContainmentStatistics {

	private TransitionPath tp;
	private int missing;
	private int out;
	private int archs;

	public ContainmentStatistics(TransitionPath tp, int missing, int out,
			int archs) {
		this.tp = tp;
		this.missing = missing;
		this.out = out;
		this.archs = archs;
	}

	public TransitionPath getTp() {
		return tp;
	}

	public void setTp(TransitionPath tp) {
		this.tp = tp;
	}

	public int getMissing() {
		return missing;
	}

	public void setMissing(int missing) {
		this.missing = missing;
	}

	public int getOut() {
		return out;
	}

	public void setOut(int out) {
		this.out = out;
	}

	public int getArchs() {
		return archs;
	}

	public void setArchs(int archs) {
		this.archs = archs;
	}

}
