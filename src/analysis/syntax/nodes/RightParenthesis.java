package analysis.syntax.nodes;

import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.SyntaxNodeLeaf;

public class RightParenthesis extends SyntaxNodeLeaf{

	public RightParenthesis(ILexicalUnit unit) {
		super(unit);
	}
	
	@Override
	public List<Double> getValues() {
		return null;
	}
	
	@Override
	public boolean isBoolean() {
		return false;
	}
	

	@Override
	public boolean isValueType() {
		return false;
	}
	
}
