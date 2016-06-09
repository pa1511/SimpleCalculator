package dataModelInterfaces;

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import dataModels.VariableNameTranslator;

public abstract class AbstractCalculatorTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	protected static final int numberOfColumns = 10;
	protected static final int numberOfRows = 500;
	
	protected IVariableNameTranslator variableNameTranslator;
	
	public AbstractCalculatorTableModel() {
		variableNameTranslator = new VariableNameTranslator();
		listeners = new LinkedList<ICalculatorTableModelListener>();
	}
	
	@SuppressWarnings("rawtypes")
	protected Class[] columnClasses = { String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class };
	
	@Override
	public int getRowCount() {
		return numberOfRows;
	}

	@Override
	public int getColumnCount() {
		return numberOfColumns;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		return variableNameTranslator.getColumnName(column);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.columnClasses[columnIndex];
	}
		
	public abstract void calculateValueAt(int row,int column,String expression);
	public abstract String getCellExpression(int row, int column);
	
	private Collection<ICalculatorTableModelListener> listeners;
	
	public void addListener(ICalculatorTableModelListener listener){
		if(listener!=null)
			listeners.add(listener);
	}
	
	public void removeListener(ICalculatorTableModelListener listener){
		if(listener!=null)
			listeners.remove(listener);
	}
	
	protected void notifyAllListeners(){
		for(ICalculatorTableModelListener listener:listeners)
			listener.tableChanged();
	}
}
