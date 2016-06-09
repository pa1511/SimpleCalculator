package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.FunctionSyntaxNode;

public class Sqrt extends FunctionSyntaxNode{
	
	public Sqrt(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public List<Double> getValues() {

		List<Double> results = new LinkedList<Double>();
		for(Double value:expression.getValues())
			results.add(Math.sqrt(value));
		
		return results;
	}
	
	@Override
	public boolean isBoolean() {
		return false;
	}

}
