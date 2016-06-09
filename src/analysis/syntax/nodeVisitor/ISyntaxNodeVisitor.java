package analysis.syntax.nodeVisitor;

import analysis.syntax.nodesCore.ISyntaxNode;


public interface ISyntaxNodeVisitor {

	public void onEntry(ISyntaxNode node);
	public void beforeEntry(ISyntaxNode node);
	public void beforeExit(ISyntaxNode node);
}
