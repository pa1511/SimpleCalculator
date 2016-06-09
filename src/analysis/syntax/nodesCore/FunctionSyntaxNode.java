package analysis.syntax.nodesCore;

import analysis.UnitType;
import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodeVisitor.ISyntaxNodeVisitor;

public abstract class FunctionSyntaxNode extends ISyntaxNode {

	private static int numberOfChildren = 3;
	protected ISyntaxNode leftParenthesis;
	protected ISyntaxNode expression;
	protected ISyntaxNode rightParesnthesis;

	public FunctionSyntaxNode(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	@Override
	public void addChildNode(ISyntaxNode node) {
		if (rightParesnthesis == null
				&& node.getNodeType().equals(UnitType.RIGHT_PARENTHESIS))
			rightParesnthesis = node;
		else if (leftParenthesis == null
				&& node.getNodeType().equals(UnitType.LEFT_PARENTHESIS))
			leftParenthesis = node;
		else if (expression == null)
			expression = node;
		else
			throw new IllegalArgumentException(
					"Invalid number of function child nodes");

	}

	@Override
	public boolean isValueType() {
		return false;
	}

	@Override
	public void analyze(ISyntaxNodeVisitor visitor) {
		visitor.onEntry(this);

		visitor.beforeEntry(this);
		leftParenthesis.analyze(visitor);

		visitor.beforeEntry(this);
		expression.analyze(visitor);

		visitor.beforeEntry(this);
		rightParesnthesis.analyze(visitor);

		visitor.beforeExit(this);
	}

}
