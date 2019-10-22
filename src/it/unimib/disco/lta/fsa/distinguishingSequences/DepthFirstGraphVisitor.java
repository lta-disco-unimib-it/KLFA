package it.unimib.disco.lta.fsa.distinguishingSequences;

import java.util.Collection;

public interface DepthFirstGraphVisitor < T extends Object> {
	
	public static interface NodeVisitor<Node extends Object>{
		
		public Collection<Node> getChildren( Node node );
		
	}

	public <Node extends Object> void  visit  ( T graph, NodeVisitor<Node> nodeVisitor ); 
	
}
