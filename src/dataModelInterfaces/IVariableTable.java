package dataModelInterfaces;

import java.util.Collection;
import java.util.Set;

public interface IVariableTable {

	public Collection<Double> getVariableValue(String value);	
	public Collection<Collection<Double>> getVariableValuesFromTo(
			String start, String finish);
	public Set<String> getVariableFromTo(
			String start, String finish);
}
