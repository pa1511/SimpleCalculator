package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.FunctionSyntaxNode;

public class Sin extends FunctionSyntaxNode {

	public Sin(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public List<Double> getValues() {

		List<Double> results = new LinkedList<Double>();
		for (Double value : expression.getValues())
			results.add(Math.sin(value));

		return results;
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
