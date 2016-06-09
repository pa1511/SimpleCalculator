package analysis.syntax.nodes;

import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.BinomialSyntaxNode;

public class Multiply extends BinomialSyntaxNode {

	public Multiply(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	protected void operatorsOFSameSize(List<Double> results) {

		List<Double> leftValues = leftNode.getValues();
		List<Double> rightValues = rightNode.getValues();

		Double sum = 0.0;

		for (int i = 0, size = leftValues.size(); i < size; i++)
			sum += leftValues.get(i) * rightValues.get(i);

		results.add(sum);
	}

	@Override
	protected void differentOperatorSizesAndBothNotOne(List<Double> results) {
		throw new IllegalArgumentException(
				"Can not multiply diferent sized vectors");
	}

	@Override
	protected void leftOperatorSizeOne(List<Double> results) {
		Double leftValue = leftNode.getValues().get(0);
		for (Double value : rightNode.getValues())
			results.add(leftValue * value);
	}

	@Override
	protected void rightOperatorSizeOne(List<Double> results) {
		Double rightValue = rightNode.getValues().get(0);
		for (Double value : leftNode.getValues())
			results.add(value * rightValue);
	}
	
	@Override
	public boolean isBoolean() {
		return false;
	}
}
