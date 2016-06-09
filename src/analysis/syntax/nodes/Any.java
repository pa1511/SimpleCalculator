package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.FunctionSyntaxNode;

public class Any extends FunctionSyntaxNode {

	public Any(ILexicalUnit unit) {
		super(unit);
	}
	
	@Override
	public List<Double> getValues() {
		Double result = 0.0;
		for (Double value : expression.getValues())
			if (!(value <= 1e-7)) {
				result = 1.0;
				break;
			}

		List<Double> results = new LinkedList<Double>();
		results.add(result);

		return results;
	}

	@Override
	public boolean isBoolean() {
		return true;
	};
}
