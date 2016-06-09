package dataModelInterfaces;

import dataModels.Point;

public interface IVariableNameTranslator {

	public String getColumnName(int column);
	public String getVariableName(Point point);
	public Point getPoint(String variableName);
}
