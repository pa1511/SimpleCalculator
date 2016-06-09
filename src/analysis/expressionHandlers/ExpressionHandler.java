package analysis.expressionHandlers;

import java.util.List;

import dataModelInterfaces.IVariableTable;
import analysis.AutomatFactory;
import analysis.ExpressionException;
import analysis.IExpressionHandler;
import analysis.inputPreparator.IPreparator;
import analysis.inputValidation.IValidator;
import analysis.lexical.ILexicalAutomat;
import analysis.syntax.ISyntaxAutomat;
import analysis.syntax.nodeVisitor.ISyntaxNodeVisitor;

public class ExpressionHandler implements IExpressionHandler{

	private IVariableTable table;
	private String expression;
	
	private ILexicalAutomat lexAutomat;
	private IValidator validator;
	private IPreparator preparator;
	private ISyntaxAutomat syxAutomt;
	
	public ExpressionHandler(IVariableTable table) {
		this.table = table;
	}

	@Override
	public void setExpression(String expression) {
		
		if(expression==null)
			throw new NullPointerException();
		
		this.expression = expression;
		try{
			initHandler();
		}
		catch(Exception e){
			throw new ExpressionException(e);
		}
	}
	
	@Override
	public String getExpression() {
		return expression;
	}
	
	private void initHandler() {
		lexAutomat = AutomatFactory.getLexAutomat("LexicalAutomat", expression);
		
		validator = AutomatFactory.getValidatorAutomat("Validator", lexAutomat);
		if(!validator.IsValid()){
			throw new IllegalArgumentException("Expression not valid.");
		}
		
		preparator = AutomatFactory.getPreparatorAutomat("Preparator", lexAutomat);
		
		syxAutomt = AutomatFactory.gettSyntaxAutomat("SyntaxAutomat", preparator.getPrepared(), table);
	}
	
	@Override
	public List<Double> getValues(){
		return syxAutomt.getValues();
	}
	
	@Override
	public boolean isBooleanExpression(){
		return syxAutomt.isBooleanExpression();
	}
	
	@Override
	public void analyze(ISyntaxNodeVisitor visitor){
		syxAutomt.analyze(visitor);
	}
	
	@Override
	public int getNumberOfUnits(){
		return lexAutomat.getNumberOfAtoms();
	}
}
