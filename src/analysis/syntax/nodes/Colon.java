package analysis.syntax.nodes;

import java.util.Collection;
import java.util.List;

import analysis.UnitType;
import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.BinomialSyntaxNode;

public class Colon extends BinomialSyntaxNode {

	public Colon(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	protected void operatorsOFSameSize(List<Double> results) {
		if (leftNode.getNodeType() != UnitType.VARIABLE
				|| rightNode.getNodeType() != UnitType.VARIABLE)
			throw new IllegalArgumentException(
					"Colon can only be used with variables");

		Collection<Collection<Double>> allVariablesInRangeValues = table.getVariableValuesFromTo(
				((Variable) leftNode).getVariableName(),
				((Variable) rightNode).getVariableName());
		
		for(Collection<Double> variableValues:allVariablesInRangeValues){
			if(variableValues.size()!=1)
				throw new IllegalArgumentException(
						"Colon operation not defined between vectors");
			
			results.addAll(variableValues);				
		}
		
	}

	@Override
	protected void differentOperatorSizesAndBothNotOne(List<Double> results) {
		throwException();
	}

	@Override
	protected void leftOperatorSizeOne(List<Double> results) {
		throwException();
	}

	@Override
	protected void rightOperatorSizeOne(List<Double> results) {
		throwException();
	}

	private void throwException() {
		throw new IllegalArgumentException(
				"Colon operation can be used only with scalars");
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
