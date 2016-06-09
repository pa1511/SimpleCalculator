package analysis.inputValidator.automats;

import java.util.HashMap;
import java.util.Map;

class ValidatorState<StateType,TransitionSymbolType> {

	private StateType state;
	private boolean canBeStartState;
	private Map<TransitionSymbolType,ValidatorState<StateType,TransitionSymbolType>> nextStates;
	
	public ValidatorState(StateType state,boolean canBeStartState) {
		this.state = state;
		this.canBeStartState = canBeStartState;
		nextStates = new HashMap<>();
	}
	
	public void addNextState(TransitionSymbolType transitionSymbol,ValidatorState<StateType,TransitionSymbolType> nextState){
		
		if(transitionSymbol==null || nextState==null)
			throw new NullPointerException();
		
		nextStates.put(transitionSymbol, nextState);
	}
	
	public void removeNextState(TransitionSymbolType transitionSymbol){
		if(transitionSymbol==null)
			throw new NullPointerException();
		
		nextStates.remove(transitionSymbol);
	}
	
	public ValidatorState<StateType,TransitionSymbolType> getNextState(TransitionSymbolType transitionSymbol){
		return nextStates.get(transitionSymbol);
	}
	
	public boolean canBeStartState(){
		return canBeStartState;
	}
	
	public StateType getState() {
		return state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		@SuppressWarnings("rawtypes")
		ValidatorState other = (ValidatorState) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return state.toString();
	}
}
