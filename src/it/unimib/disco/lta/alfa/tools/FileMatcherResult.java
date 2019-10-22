package it.unimib.disco.lta.alfa.tools;

public class FileMatcherResult {

	private String content;
	private int startLine;

	public FileMatcherResult(int startLine, String content) {
		this.startLine = startLine;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public int getStartLine() {
		return startLine;
	}

}
