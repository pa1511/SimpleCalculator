package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.SyntaxNodeLeaf;

public class Variable extends SyntaxNodeLeaf{
		
	public Variable(ILexicalUnit unit) {
		super(unit);
	}
	
	@Override
	public List<Double> getValues() {
		List<Double> value = new LinkedList<Double>();
		value.addAll(table.getVariableValue(unit.getValue()));
		return value;
	}
	
	public String getVariableName(){
		return unit.getValue();
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
