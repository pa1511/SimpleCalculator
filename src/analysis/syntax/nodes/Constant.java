package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.SyntaxNodeLeaf;

public class Constant extends SyntaxNodeLeaf {
	
	public Constant(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public List<Double> getValues() {
		List<Double> value = new LinkedList<>();
		value.add(Double.parseDouble(this.unit.getValue()));
		return value;
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
	
	@Override
	public boolean isValueType() {
		return true;
	}
	
}
