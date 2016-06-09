package analysis.syntax.nodes;

import java.util.List;

import booleanOperationsSupport.BooleanOperationsSupport;
import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.BinomialSyntaxNode;

public class Larger extends BinomialSyntaxNode {

	public Larger(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	protected void operatorsOFSameSize(List<Double> results) {
		performCalculation(results);
	}

	@Override
	protected void leftOperatorSizeOne(List<Double> results) {
		performCalculation(results);
	}

	@Override
	protected void rightOperatorSizeOne(List<Double> results) {
		performCalculation(results);
	}

	@Override
	protected void differentOperatorSizesAndBothNotOne(List<Double> results) {
		performCalculation(results);
	}

	private void performCalculation(List<Double> results) {
		for (Double valueLeft : leftNode.getValues())
			for (Double valueRight : rightNode.getValues()) {
				boolean result = valueLeft > valueRight;
				results.add(BooleanOperationsSupport.getDoubleValue(result));
			}
	}

	@Override
	public boolean isBoolean() {
		return true;
	}
}
