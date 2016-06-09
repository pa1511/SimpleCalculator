package analysis.syntax.nodes;

import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.BinomialSyntaxNode;

public class Minus extends BinomialSyntaxNode {


	public Minus(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	protected void operatorsOFSameSize(List<Double> results) {

		List<Double> leftValues = leftNode.getValues();
		List<Double> rightValues = rightNode.getValues();

		for (int i = 0, size = leftValues.size(); i < size; i++)
			results.add(leftValues.get(i) - rightValues.get(i));

	}
	
	@Override
	protected void differentOperatorSizesAndBothNotOne(List<Double> results) {
		throw new IllegalArgumentException("Invalid vector subtraction");
	}
	
	@Override
	protected void leftOperatorSizeOne(List<Double> results) {
		throw new IllegalArgumentException("Can not substract vector from scalar");
	}
	
	@Override
	protected void rightOperatorSizeOne(List<Double> results) {
		Double rightValue = rightNode.getValues().get(0);
		for(Double value:leftNode.getValues())
			results.add(value-rightValue);
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
