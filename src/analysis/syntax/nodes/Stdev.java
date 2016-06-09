package analysis.syntax.nodes;

import java.util.LinkedList;
import java.util.List;

import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodesCore.FunctionSyntaxNode;

public class Stdev extends FunctionSyntaxNode {
	
	public Stdev(ILexicalUnit unit) {
		super(unit);
	}

	@Override
	public List<Double> getValues() {
		
		List<Double> expressionValues = expression.getValues();
		int size = expressionValues.size();
		
		Double sum = 0.0;
		for(Double value:expressionValues){
			sum+=value;
		}
		
		Double u = sum/size;
		
		
		sum = 0.0;
		
		for(int i=0; i<size;i++){
			sum+=Math.pow((expressionValues.get(i)-u), 2);
		}
		
		List<Double> result = new LinkedList<Double>();
		result.add(Math.sqrt(sum/size));
		
		return result;
	}

	@Override
	public boolean isBoolean() {
		// TODO Auto-generated method stub
		return false;
	}
}
