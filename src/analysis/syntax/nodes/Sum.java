package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.FunctionSyntaxNode;

public class Sum extends FunctionSyntaxNode{

	public Sum(ILexicalUnit unit) {
		super(unit);
	}
	
	@Override
	public List<Double> getValues() {
		
		Double sum = 0.0;
		for(Double value:expression.getValues())
			sum+=value;
		
		List<Double> results = new LinkedList<Double>();
		results.add(sum);	
		
		return results;
	}
	
	@Override
	public boolean isBoolean() {
		return false;
	}
}
