package analysis.syntax.nodes;

import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.BinomialSyntaxNode;

public class Comma extends BinomialSyntaxNode {

	public Comma(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	protected void operatorsOFSameSize(List<Double> results) {
		addValues(results);

	}

	@Override
	protected void differentOperatorSizesAndBothNotOne(List<Double> results) {
		addValues(results);
	}

	@Override
	protected void leftOperatorSizeOne(List<Double> results) {
		addValues(results);
	}

	@Override
	protected void rightOperatorSizeOne(List<Double> results) {
		addValues(results);
	}

	private void addValues(List<Double> results) {
		results.addAll(leftNode.getValues());
		results.addAll(rightNode.getValues());
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
