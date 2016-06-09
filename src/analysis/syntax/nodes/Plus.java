package analysis.syntax.nodes;

import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.BinomialSyntaxNode;

public class Plus extends BinomialSyntaxNode {

	public Plus(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	protected void operatorsOFSameSize(List<Double> results) {

		List<Double> leftValues = leftNode.getValues();
		List<Double> rightValues = rightNode.getValues();

		for (int i = 0, size = leftValues.size(); i < size; i++)
			results.add(leftValues.get(i) + rightValues.get(i));
	}

	@Override
	protected void differentOperatorSizesAndBothNotOne(List<Double> results) {
		throw new IllegalArgumentException("Can not add vectors of different size");
	}

	@Override
	protected void leftOperatorSizeOne(List<Double> results) {
		Double leftValue = leftNode.getValues().get(0);
		for(Double rightValue:rightNode.getValues())
			results.add(leftValue+rightValue);
	}

	@Override
	protected void rightOperatorSizeOne(List<Double> results) {
		Double rightValue = rightNode.getValues().get(0);
		for(Double leftValue:leftNode.getValues())
			results.add(leftValue+rightValue);
	}
	
	@Override
	public boolean isBoolean() {
		return false;
	}
}
