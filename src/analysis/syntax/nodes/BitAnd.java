package analysis.syntax.nodes;

import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.BinomialSyntaxNode;

public class BitAnd extends BinomialSyntaxNode{

	public BitAnd(ILexicalUnit unit) {
		super(unit);
	}
	
	@Override
	protected void operatorsOFSameSize(List<Double> results) {
		List<Double> leftValues = leftNode.getValues();
		List<Double> rightValues = rightNode.getValues();

		for (int i = 0, size = leftValues.size(); i < size; i++){
			Double leftValue = leftValues.get(i);
			Double rightValue = rightValues.get(i);
			
			checkArgument(leftValue);
			checkArgument(rightValue);

			results.add(new Double(leftValue.intValue() & rightValue.intValue()));
		}	
	}
	
	@Override
	protected void differentOperatorSizesAndBothNotOne(List<Double> results) {
		throw new IllegalArgumentException("Bit and cannot be done between vectors of different size");
	}
	
	@Override
	protected void leftOperatorSizeOne(List<Double> results) {
		Double leftValue = leftNode.getValues().get(0);

		checkArgument(leftValue);
		
		for(Double rightValue:rightNode.getValues()){
			checkArgument(rightValue);
			
			results.add(new Double(leftValue.intValue() & rightValue.intValue()));
		}
	}
	
	@Override
	protected void rightOperatorSizeOne(List<Double> results) {
		Double rightValue = rightNode.getValues().get(0);

		checkArgument(rightValue);
		
		for(Double leftValue:leftNode.getValues()){
			checkArgument(leftValue);
			
			results.add(new Double(leftValue.intValue() & rightValue.intValue()));
		}
	}
	
	private void checkArgument(Double value) {
		if(!((value == Math.floor(value)) && !Double.isInfinite(value)))
			throw new IllegalArgumentException("Bit operation And not definied on non integer values.");
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
