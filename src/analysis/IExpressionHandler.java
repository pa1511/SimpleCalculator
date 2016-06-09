package analysis;

import java.util.List;

import analysis.syntax.nodeVisitor.ISyntaxNodeVisitor;

public interface IExpressionHandler {

	public void setExpression(String expression);
	public String getExpression();
	public List<Double> getValues();
	public boolean isBooleanExpression();
	public void analyze(ISyntaxNodeVisitor visitor);
	public int getNumberOfUnits();
}
