package analysis.syntax.automats;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import dataModelInterfaces.IVariableTable;
import analysis.UnitType;
import analysis.lexical.ILexicalAutomat;
import analysis.lexical.ILexicalUnit;
import analysis.syntax.ISyntaxAutomat;
import analysis.syntax.nodeVisitor.ISyntaxNodeVisitor;
import analysis.syntax.nodesCore.ISyntaxNode;
import analysis.syntax.nodesCore.SyntaxNodeFactory;

public class SyntaxAutomat implements ISyntaxAutomat{

	private ILexicalAutomat lexical;
	private IVariableTable table;
	private ISyntaxNode root;
	
	
	public SyntaxAutomat(ILexicalAutomat lexicalAutomat,IVariableTable table) {
		this.lexical = lexicalAutomat;
		this.table = table;
		createSyntaxTree();
	}

	private void createSyntaxTree() {

		Stack<ISyntaxNode> values = new Stack<>();
		Stack<ISyntaxNode> operators = new Stack<>();

		for (int i = 0, size = lexical.getNumberOfAtoms(); i < size; i++) {

			ILexicalUnit unit = lexical.getAtomAt(i);

			if (unit.getType().equals(UnitType.EOF)) {
				executeToEndOrParenthesis(values, operators);
				break;
			}

			ISyntaxNode currentNode = SyntaxNodeFactory.getNode(unit);
			currentNode.setTable(table);

			if (currentNode.isValueType()) {
				values.push(currentNode);
				continue;
			}

			if (currentNode.getNodeType() == UnitType.RIGHT_PARENTHESIS) {

				executeToEndOrParenthesis(values, operators);

				// left parenthesis
				ISyntaxNode leftParenthesis = operators.pop();
				if (!operators.isEmpty() && operators.peek().getNodeType() == UnitType.FUNCTION) {
					values.push(leftParenthesis);
					values.push(currentNode);
					executeOperator(values, operators);
				}
			} else if (operators.isEmpty()
					|| operators.peek().getNodeType().getStackPriority() < currentNode
							.getNodeType().getInputPriority()) {
				operators.push(currentNode);
			} else {
				executeOperator(values, operators);
				i--;
			}

		}
		
		if(!values.isEmpty())
			root = values.pop();
	}

	private void executeToEndOrParenthesis(Stack<ISyntaxNode> values,
			Stack<ISyntaxNode> operators) {
		while (!operators.isEmpty()
				&& !(operators.peek().getNodeType() == UnitType.LEFT_PARENTHESIS)) {
			executeOperator(values, operators);
		}
	}

	private void executeOperator(Stack<ISyntaxNode> values,
			Stack<ISyntaxNode> operators) {
		ISyntaxNode currentOperator = operators.pop();
		for (int j = 0; j < currentOperator.getNumberOfChildren(); j++)
			currentOperator.addChildNode(values.pop());
		values.push(currentOperator);
	}

	// =======================================================================================================
	@Override
	public void analyze(ISyntaxNodeVisitor visitor) {
	
		if(root==null)
			return;
		
		if(visitor!=null)
			root.analyze(visitor);
	}

	@Override
	public List<Double> getValues() {
		if (root != null)
			return root.getValues();

		List<Double> reslut = new LinkedList<Double>();
		reslut.add(0.0);
		return reslut;
	}
	
	@Override
	public boolean isBooleanExpression(){

		if(root==null)
			return false;
			
		return root.isBoolean();
	}

}
