package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.FunctionSyntaxNode;

public class Count extends FunctionSyntaxNode {

	public Count(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public List<Double> getValues() {

		List<Double> result = new LinkedList<Double>();
		result.add(new Double(expression.getValues().size()));

		return result;
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
