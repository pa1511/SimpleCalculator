package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import booleanOperationsSupport.BooleanOperationsSupport;
import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodeVisitor.ISyntaxNodeVisitor;
import analysis.syntax.nodesCore.ISyntaxNode;

public class Not extends ISyntaxNode {

	private ISyntaxNode expression;
	private static int numberOfChildren = 1;

	public Not(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	@Override
	public List<Double> getValues() {
		List<Double> results = new LinkedList<Double>();

		for (Double value : expression.getValues()) {
			boolean result = !BooleanOperationsSupport.getBooleanValue(value);
			results.add(BooleanOperationsSupport.getDoubleValue(result));
		}

		return results;
	}

	@Override
	public void addChildNode(ISyntaxNode node) {
		if (expression == null)
			expression = node;
		else
			throw new IllegalArgumentException("Not can only have one cihld");
	}

	@Override
	public void analyze(ISyntaxNodeVisitor visitor) {
		visitor.onEntry(this);
		visitor.beforeEntry(this);
		expression.analyze(visitor);
		visitor.beforeExit(this);
	}

	public ISyntaxNode getExpression() {
		return expression;
	}

	@Override
	public boolean isBoolean() {
		return true;
	}

	@Override
	public boolean isValueType() {
		return false;
	}
}
