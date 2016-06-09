package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.FunctionSyntaxNode;

public class Mean extends FunctionSyntaxNode {

	public Mean(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public List<Double> getValues() {
		List<Double> expressionValues = expression.getValues();
		int size = expressionValues.size();

		Double sum = 0.0;
		for (Double value : expressionValues) {
			sum += value;
		}

		List<Double> result = new LinkedList<Double>();
		result.add(sum / size);

		return result;
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
