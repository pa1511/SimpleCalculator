package analysis.syntax;

import java.util.List;

import analysis.syntax.nodeVisitor.ISyntaxNodeVisitor;

public interface ISyntaxAutomat {

	public void analyze(ISyntaxNodeVisitor visitor);
	public List<Double> getValues();
	public boolean isBooleanExpression();
}
