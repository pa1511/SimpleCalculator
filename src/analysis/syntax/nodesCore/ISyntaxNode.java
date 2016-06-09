package analysis.syntax.nodesCore;

import java.util.List;

import dataModelInterfaces.IVariableTable;
import analysis.UnitType;
import analysis.lexical.ILexicalUnit;
import analysis.syntax.nodeVisitor.ISyntaxNodeVisitor;

public abstract class ISyntaxNode {

	protected ILexicalUnit unit;
	protected IVariableTable table;
	
	public ISyntaxNode(ILexicalUnit unit) {
		this.unit = unit;
	}
	
	public UnitType getNodeType(){
		return unit.getType();
	}
	
	public abstract List<Double> getValues();
	//======================================================
	public abstract void analyze(ISyntaxNodeVisitor visitor);
	//======================================================	
	public abstract boolean isBoolean();
	public abstract boolean isValueType();
	//======================================================
	public abstract void addChildNode(ISyntaxNode node);
	public abstract int getNumberOfChildren();
	
	public void setTable(IVariableTable table) {
		this.table = table;
	}
}
