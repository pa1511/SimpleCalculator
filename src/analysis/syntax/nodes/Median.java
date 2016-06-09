package analysis.syntax.nodes;

import java.util.Collections;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.FunctionSyntaxNode;

public class Median extends FunctionSyntaxNode {

	public Median(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public List<Double> getValues() {

		List<Double> results = expression.getValues();

		Collections.sort(results);

		return results;
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
