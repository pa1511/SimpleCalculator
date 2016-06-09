package analysis.syntax.nodes;

import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.BinomialSyntaxNode;

public class Divide extends BinomialSyntaxNode {

	public Divide(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	protected void operatorsOFSameSize(List<Double> results) {

		List<Double> leftValues = leftNode.getValues();
		List<Double> rightValues = rightNode.getValues();

		if (leftValues.size() != 1)
			throw new IllegalArgumentException(
					"Dividing vectors is not supported");

		for (int i = 0, size = leftValues.size(); i < size; i++) {
			Double rightValue = rightValues.get(i);

			if (rightValue < 1e-7)
				throw new IllegalArgumentException("Dividing by zero");

			results.add(leftValues.get(i) / rightValue);
		}
	}

	@Override
	protected void differentOperatorSizesAndBothNotOne(List<Double> results) {
		throw new IllegalArgumentException("Dividing vectors is not supported");
	}

	@Override
	protected void leftOperatorSizeOne(List<Double> results) {
		throw new IllegalArgumentException(
				"A scalar cannot be divided by a vector.");
	}

	@Override
	protected void rightOperatorSizeOne(List<Double> results) {
		Double rightValue = rightNode.getValues().get(0);

		if (rightValue == 0)
			throw new IllegalArgumentException("Dividing by zero");

		for (Double value : leftNode.getValues())
			results.add(value / rightValue);
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
