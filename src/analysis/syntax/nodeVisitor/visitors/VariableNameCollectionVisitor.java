package analysis.syntax.nodeVisitor.visitors;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import dataModelInterfaces.IVariableTable;
import analysis.UnitType;
import analysis.syntax.nodeVisitor.SyntaxNodeVisitorAdaper;
import analysis.syntax.nodes.Colon;
import analysis.syntax.nodes.Variable;
import analysis.syntax.nodesCore.ISyntaxNode;

public class VariableNameCollectionVisitor extends SyntaxNodeVisitorAdaper{

	private Set<String> variables = new HashSet<>();
	private IVariableTable table ;
	
	public VariableNameCollectionVisitor(IVariableTable table) {
		this.table = table;
	}
	
	
	@Override
	public void onEntry(ISyntaxNode node) {
		if(node.getNodeType()==UnitType.VARIABLE){
			Variable v = (Variable)node;
			variables.add(v.getVariableName());
		}
		
		if(node.getNodeType()==UnitType.COLON){
			Colon c = (Colon)node;
			Variable start = (Variable)c.getLeftNode();
			Variable end = (Variable)c.getRightNode();
			
			variables.addAll(table.getVariableFromTo(start.getVariableName(), end.getVariableName()));
		}
	}
	
	public Collection<String> getVariables() {
		return variables;
	}
	
}
