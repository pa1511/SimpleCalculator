package dataModels;

import java.util.NoSuchElementException;

import dataModelInterfaces.IVariableNameTranslator;

public class VariableNameTranslator implements IVariableNameTranslator{
	
	private String[] columnNames = { "", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };

	@Override
	public String getColumnName(int column){
		if(column<columnNames.length)
			return columnNames[column];
		return "";
	}
	
	@Override
	public Point getPoint(String variableName) {
		
		String arguments[]= variableName.replaceAll("([A-Z])([0-9]+)", "$1 $2").split(" ");
		
		String columnName = arguments[0].trim().toUpperCase();
		
		int column = -1;
		
		for(int i=0; i<columnNames.length;i++)
			if(columnNames[i].equals(columnName)){
				column = i;
				break;
			}
		
		int row = Integer.parseInt(arguments[1].trim())-1;
		
		
		return new Point(row, column);
	}
	
	@Override
	public String getVariableName(Point point){

		String columnName = getColumnName(point.getY()).trim();

		if(columnName.isEmpty())
			throw new NoSuchElementException();
		
		return columnName+(point.getX()+1);
	}
}
