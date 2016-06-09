package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.FunctionSyntaxNode;

public class Bincoeff extends FunctionSyntaxNode {

	public Bincoeff(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public List<Double> getValues() {

		List<Double> expressionValues = expression.getValues();

		if (expressionValues.size() != 2)
			throw new IllegalArgumentException("Bincoeff takes two scalars");

		Double arg1 = expressionValues.get(0);
		Double arg2 = expressionValues.get(1);

		checkArgument(arg1);
		checkArgument(arg2);

		int result = 1;

		result = getFact(arg1.intValue())
				/ (
						getFact(arg2.intValue()) * 
						getFact(arg1.intValue()- arg2.intValue())
				);

		List<Double> results = new LinkedList<Double>();

		results.add(new Double(result));

		return results;
	}

	private Integer getFact(int intValue) {
		
		int result = 1;
		
		for(int i=2; i<=intValue;i++)
			result*=i;
		
		return result;
	}

	@Override
	public boolean isBoolean() {
		return false;
	}

	private void checkArgument(Double value) {
		if (!((value == Math.floor(value)) && !Double.isInfinite(value)))
			throw new IllegalArgumentException(
					"Bincoeff operation not definied on non integer values.");
	}
}
