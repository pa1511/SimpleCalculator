package analysis.syntax.nodes;

import java.util.List;

import booleanOperationsSupport.BooleanOperationsSupport;
import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.BinomialSyntaxNode;

public class Xor extends BinomialSyntaxNode {

	public Xor(ILexicalUnit unit) {
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
				boolean result = Boolean.logicalXor(
						BooleanOperationsSupport.getBooleanValue(valueLeft),
						BooleanOperationsSupport.getBooleanValue(valueRight));

				results.add(BooleanOperationsSupport.getDoubleValue(result));
			}
	}
	
	@Override
	public boolean isBoolean() {
		return true;
	}
}
