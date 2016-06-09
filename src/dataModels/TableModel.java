package dataModels;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import booleanOperationsSupport.BooleanOperationsSupport;
import analysis.ExpressionHandlerFactory;
import analysis.IExpressionHandler;
import analysis.syntax.nodeVisitor.visitors.VariableNameCollectionVisitor;
import dataModelInterfaces.AbstractCalculatorTableModel;
import dataModelInterfaces.IVariableTable;

public class TableModel extends AbstractCalculatorTableModel  implements IVariableTable{

	private static final long serialVersionUID = 1L;
	
	private Map<Point, Cell> cells;

	public TableModel() {
		cells = new HashMap<>();
		initalizeCells();
	}
	
	private void initalizeCells() {
		for(int i=0; i<numberOfRows;i++){
			//Columns start from one because the first column is used to display the column index
			for(int j=1; j<numberOfColumns;j++){
				Point position = new Point(i, j);
				Cell cell = new Cell(position);
				cells.put(position, cell);
			}
		}
	}

	//===================================================================================
	//AbstractcalculatorTableModel implementation

	@Override
	public void calculateValueAt(int row, int column, String expression) {
		IExpressionHandler handler = ExpressionHandlerFactory.gettSyntaxAutomat("ExpressionHandler", this);
		
		//can throw exception if the expression is not valid
		handler.setExpression(expression);

		VariableNameCollectionVisitor visitor = new VariableNameCollectionVisitor(this);
		handler.analyze(visitor);
		
		Collection<String> usedVariables = visitor.getVariables();
		
		Cell selectedCell = cells.get(new Point(row, column));
	
		if(isCircularReference(selectedCell,handler, new LinkedList<Cell>()))
			throw new IllegalArgumentException("Circular reference not allowed");
		
		Collection<Cell> subjects = new LinkedList<>();
		
		if(!usedVariables.isEmpty()){
			for(String usedVariableName:usedVariables){
				Point position = variableNameTranslator.getPoint(usedVariableName);
				
				if(!cells.containsKey(position))
					throw new IllegalArgumentException("No such variable. Given variable name: " + usedVariableName);
				
				subjects.add(cells.get(position));
				
			}
		}
		
		selectedCell.removeAllObservableSubjects();

		for(Cell subject:subjects)
			selectedCell.addVariableObservableSubject(subject);
		
		selectedCell.setHandler(handler);
		
		notifyAllListeners();
	}
	
	private boolean isCircularReference(Cell selectedCell,
			IExpressionHandler handler, Collection<Cell> visited) {
		
		visited.add(selectedCell);
		
		VariableNameCollectionVisitor visitor = new VariableNameCollectionVisitor(this);
		handler.analyze(visitor);
		
		Collection<String> usedVariables = visitor.getVariables();
		
		for(String variable : usedVariables){
			Cell cell = cells.get(variableNameTranslator.getPoint(variable));
			
			if(cell==null)
				throw new IllegalArgumentException("Unknown variable: " + variable);
			
			if(visited.contains(cell))
				return true;
			
			Collection<Cell> visitedCopy = new LinkedList<Cell>(visited);
			IExpressionHandler cellHandler = cell.getHandler();
			
			
			if(cellHandler!=null && isCircularReference(cell, cellHandler, visitedCopy))
				return true;
		}
		
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(columnIndex==0)
			return rowIndex+1;

		Point position = new Point(rowIndex, columnIndex);
		
		Collection<Double> result = null;
		
		if(cells.containsKey(position)){
			Cell cell = cells.get(position);
			result = cell.getValue();
			if(cell.isBooleanExpression()){
				Collection<Boolean> booleanResult = new LinkedList<>();
				for(Double value:result)
					booleanResult.add(BooleanOperationsSupport.getBooleanValue(value));
				return booleanResult;
			}
		}
		
		return result;
	}
	
	@Override
	public String getCellExpression(int row, int column) {
		Point position = new Point(row, column);
		Cell cell = cells.get(position);
		
		if(cell==null)
			return null;

		return cell.getExpression();
	}

	@Override
	public Collection<Double> getVariableValue(String value) {
		Point position = variableNameTranslator.getPoint(value);
		
		if(!cells.containsKey(position))
			throw new IllegalArgumentException("No such variable! Given variable name: " +value);
		
		Collection<Double> result = getCellValue(position);
		
		return result;
	}
	
	@Override
	public Collection<Collection<Double>> getVariableValuesFromTo(
			String start, String finish) {
	
		Point startPoint = variableNameTranslator.getPoint(start);
		Point endPoint = variableNameTranslator.getPoint(finish);
	
		if(!cells.containsKey(startPoint) || !cells.containsKey(endPoint))
			throw new IllegalArgumentException("");
		
		LinkedList<Collection<Double>> result = new LinkedList<Collection<Double>>();
		
		if(startPoint.equals(endPoint)){
			result.add(getCellValue(startPoint));
		}
		//same row
		else if(startPoint.getX()==endPoint.getX() && startPoint.getY()<endPoint.getY()){
			for(int j=startPoint.getY();j<=endPoint.getY();j++)
				result.add(getCellValue(new Point(startPoint.getX(), j)));
		}
		//same column
		else if(startPoint.getY()==endPoint.getY() && startPoint.getX()<endPoint.getX()){
			for(int i=startPoint.getX();i<=endPoint.getX();i++)
				result.add(getCellValue(new Point(i, startPoint.getY())));		
		}
		else{
			throw new IllegalArgumentException("Illegal variable range:"+start+"-"+finish);
		}
		
		return result;
	}

	@Override
	public Set<String> getVariableFromTo(
			String start, String finish) {
	
		Point startPoint = variableNameTranslator.getPoint(start);
		Point endPoint = variableNameTranslator.getPoint(finish);
	
		if(!cells.containsKey(startPoint) || !cells.containsKey(endPoint))
			throw new IllegalArgumentException("");
		
		Set<String> result = new HashSet<String>();
		
		if(startPoint.equals(endPoint)){
			result.add(start);
		}
		
		//same row
		else if(startPoint.getX()==endPoint.getX() && startPoint.getY()<endPoint.getY()){
			for(int j=startPoint.getY();j<=endPoint.getY();j++)
				result.add(variableNameTranslator.getVariableName(new Point(startPoint.getX(), j)));
		}
		//same column
		else if(startPoint.getY()==endPoint.getY() && startPoint.getX()<endPoint.getX()){
			for(int i=startPoint.getX();i<=endPoint.getX();i++)
				result.add(variableNameTranslator.getVariableName(new Point(i, startPoint.getY())));		
		}
		else{
			throw new IllegalArgumentException("Illegal variable range:"+start+"-"+finish);
		}
		
		return result;
	}
	
	private Collection<Double> getCellValue(Point position) {
		Collection<Double> result =  cells.get(position).getValue();
		
		if(result==null || result.isEmpty()){
			result = new LinkedList<>();
			result.add(0.0);
		}
		return result;
	}

}
