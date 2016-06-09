package dataModelInterfaces;

import java.util.Collection;

public interface IVariableObservable {

	public void addVariableObserver(IVariableObserver observer);
	public void addVariableObservers(Collection<IVariableObserver> observers);
	public void removeVariableObserver(IVariableObserver obsoliteObserver);
	public void removeVariableObservers(Collection<IVariableObserver> obsoliteObservers);
	public void notifyAllVariableObservers();
}
