package dataModels;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import dataModelInterfaces.IVariableObservable;
import dataModelInterfaces.IVariableObserver;
import analysis.IExpressionHandler;

public class Cell  implements IVariableObservable, IVariableObserver{

	private Point position;
	private IExpressionHandler handler;

	//cache of the value so that it does not have to be calculated every time
	private Collection<Double> value;

	public Cell(Point p) {
		position = p;
		observers = new LinkedList<>();
		roObservers = Collections.unmodifiableList(observers);
		subjects = new LinkedList<>();
	}

	public Cell(int row, int column) {
		this(new Point(row, column));
	}

	public void setHandler(IExpressionHandler handler) {
		if (handler != null){
			this.handler = handler;
			value = this.handler.getValues();
			notifyAllVariableObservers();
		}
	}

	public Collection<Double> getValue(){
		return value;
	}


	public String getExpression() {
		
		if(handler==null)
			return "";
		
		String expression = handler.getExpression();
		
		if(expression==null)
			expression = "";
		
		return expression;
	}

	
	public boolean isBooleanExpression(){
		
		if(handler==null)
			return false;
		
		return handler.isBooleanExpression();
	}
	
	public IExpressionHandler getHandler() {
		return handler;
	}
	//====================================================================================================================
	//IVariableObservable
	
	private List<IVariableObserver> observers;
	private List<IVariableObserver> roObservers;
	
	@Override
	public void addVariableObservers(Collection<IVariableObserver> observers) {
		if(observers!=null)
			this.observers.addAll(observers);
	}
	
	@Override
	public void addVariableObserver(IVariableObserver observer) {
		if(observer!=null)
			observers.add(observer);
	}
	
	@Override
	public void removeVariableObservers(
			Collection<IVariableObserver> obsoliteObservers) {
		if(obsoliteObservers!=null)
			observers.removeAll(obsoliteObservers);
	}
	
	@Override
	public void removeVariableObserver(IVariableObserver obsoliteObserver) {
		if(obsoliteObserver!=null)
			observers.remove(obsoliteObserver);
	}
	
	@Override
	public void notifyAllVariableObservers() {
		for(IVariableObserver observer:observers)
			observer.variableValueChanged();
	}
	
	public List<IVariableObserver> getObservers() {
		return roObservers;
	}
	
	//======================================================================================================================
	//IVariableObserver
	
	private Collection<IVariableObservable> subjects;
	
	@Override
	public void addVariableObservableSubject(IVariableObservable subject) {
		if(subject!=null){ 
			subjects.add(subject);
			subject.addVariableObserver(this);
		}
	}
	
	@Override
	public void removeVariableObservableSubject(
			IVariableObservable obsoliteSubject) {
		if(obsoliteSubject!=null){
			subjects.remove(obsoliteSubject);
			obsoliteSubject.removeVariableObserver(this);
		}
	}
	
	@Override
	public void removeAllObservableSubjects() {
		for(IVariableObservable obsoliteSubject:subjects)
			obsoliteSubject.removeVariableObserver(this);
		subjects.clear();
	}
	
	@Override
	public void variableValueChanged() {
		value = handler.getValues();
		notifyAllVariableObservers();
	}

	//=======================================================================================================================
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
}
