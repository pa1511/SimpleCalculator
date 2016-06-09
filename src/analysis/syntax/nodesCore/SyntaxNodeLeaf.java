package analysis.syntax.nodesCore;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodeVisitor.ISyntaxNodeVisitor;

public abstract class  SyntaxNodeLeaf extends ISyntaxNode{

	private static int numberOfChildren = 0;

	public SyntaxNodeLeaf(ILexicalUnit unit) {
		super(unit);
	}
	
	@Override
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
		
	@Override
	public void analyze(ISyntaxNodeVisitor visitor) {
		visitor.onEntry(this);
		visitor.beforeEntry(this);
		visitor.beforeExit(this);
	}
	
	@Override
	public void addChildNode(ISyntaxNode node) {
		throw new IllegalArgumentException("Leafs cannot have children");	
	}
}
