package analysis.syntax.nodes;

import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.SyntaxNodeLeaf;

public class LeftParenthesis extends SyntaxNodeLeaf {

	public LeftParenthesis(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public boolean isValueType() {
		return false;
	}

	@Override
	public List<Double> getValues() {
		return null;
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
