package dataModelInterfaces;

public interface IVariableObserver {

	public void addVariableObservableSubject(IVariableObservable subject);
	public void removeVariableObservableSubject(IVariableObservable obsoliteSubject);
	public void removeAllObservableSubjects();
	public void variableValueChanged();
}
