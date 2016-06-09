package analysis.syntax.nodesCore;

import java.util.LinkedList;
import java.util.List;

import analysis.UnitType;
import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodeVisitor.ISyntaxNodeVisitor;

public abstract class BinomialSyntaxNode extends ISyntaxNode {

	private static int numberOfChildren = 2;
	protected ISyntaxNode leftNode;
	protected ISyntaxNode rightNode;
	protected UnitType type;

	public BinomialSyntaxNode(ILexicalUnit unit) {
		super(unit);
	}
	
	@Override
	public void addChildNode(ISyntaxNode node) {
		if(rightNode==null)
			rightNode = node;
		else if(leftNode == null)
			leftNode = node;
		else
			throw new IllegalArgumentException("Binomial nodes cannot have more than two children");
	}

	@Override
	public boolean isValueType() {
		return false;
	}

	public void setLeftNode(ISyntaxNode leftNode) {
		this.leftNode = leftNode;
	}

	public void setRightNode(ISyntaxNode rightNode) {
		this.rightNode = rightNode;
	}

	public ISyntaxNode getLeftNode() {
		return leftNode;
	}

	public ISyntaxNode getRightNode() {
		return rightNode;
	}

	@Override
	public void analyze(ISyntaxNodeVisitor visitor) {
		visitor.onEntry(this);

		visitor.beforeEntry(this);
		leftNode.analyze(visitor);

		visitor.beforeEntry(this);
		rightNode.analyze(visitor);

		visitor.beforeExit(this);
	}

	@Override
	public List<Double> getValues() {

		List<Double> results = new LinkedList<Double>();

		int leftValuesSize = leftNode.getValues().size();
		int rightValuesSize = rightNode.getValues().size();

		if (leftValuesSize == rightValuesSize)
			operatorsOFSameSize(results);
		else if (leftValuesSize == 1 && rightValuesSize > 1)
			leftOperatorSizeOne(results);
		else if (leftValuesSize > 1 && rightValuesSize == 1)
			rightOperatorSizeOne(results);
		else
			differentOperatorSizesAndBothNotOne(results);

		return results;
	}

	protected void operatorsOFSameSize(List<Double> results) {
		// no implementation
	}

	protected void leftOperatorSizeOne(List<Double> results) {
		// no implementation
	}

	protected void rightOperatorSizeOne(List<Double> results) {
		// no implementation
	}

	protected void differentOperatorSizesAndBothNotOne(List<Double> results) {
		// no implementation
	}

	@Override
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
}
