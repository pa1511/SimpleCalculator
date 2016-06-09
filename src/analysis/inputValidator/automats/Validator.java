package analysis.inputValidator.automats;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import analysis.UnitType;
import analysis.inputValidation.IValidator;
import analysis.lexical.ILexicalAutomat;

public class Validator implements IValidator{

	private ILexicalAutomat automat;
	private Boolean isValid = null;
	private Map<UnitType, ValidatorState<UnitType, UnitType>> states;

	public Validator(ILexicalAutomat automat) {
		this.automat = automat;
		states = new HashMap<UnitType, ValidatorState<UnitType, UnitType>>();
		initValidator();
	}

	private void initValidator() {
		initConstant();
		initVariable();
		initFunction();

		initOperators();
		initNot();
		initCommaAndColon();

		initLeftParanthesis();
		initRightParanthesis();

		initStates();
	}

	@Override
	public Boolean IsValid() {

		if (isValid == null)
			validate();

		return isValid;
	}

	private void validate() {
		UnitType startType = automat.getAtomAt(0).getType();
		ValidatorState<UnitType, UnitType> startState = states
				.get(startType);

		if (!startState.canBeStartState()) {
			isValid = false;
			return;
		}

		int leftParenthesisCount = 0;
		int rightParenthesisCount = 0;

		ValidatorState<UnitType, UnitType> currentState = startState;
		for (int i = 1; i < automat.getNumberOfAtoms(); i++) {
			if (currentState.equals(leftParenthesis))
				leftParenthesisCount++;
			if (currentState.equals(rightParenthesis))
				rightParenthesisCount++;

			currentState = currentState.getNextState(automat.getAtomAt(i)
					.getType());
			if (currentState == null) {
				isValid = false;
				return;
			}
		}

		if (currentState.equals(leftParenthesis))
			leftParenthesisCount++;
		if (currentState.equals(rightParenthesis))
			rightParenthesisCount++;
		
		if (currentState.equals(eof)) {
			isValid = true;
		} else {
			isValid = false;
		}
		
		if(leftParenthesisCount !=rightParenthesisCount)
			isValid = false;

	}

	private void initStates() {
		states.put(UnitType.AND, and);
		states.put(UnitType.BIT_AND, bitAnd);
		states.put(UnitType.BIT_OR, bitOr);
		states.put(UnitType.COLON, colon);
		states.put(UnitType.COMMA, comma);
		states.put(UnitType.CONSTANT, constant);
		states.put(UnitType.DIVIDE, divide);
		states.put(UnitType.EOF, eof);
		states.put(UnitType.EQUAL, equal);
		states.put(UnitType.FUNCTION, function);
		states.put(UnitType.LARGER, larger);
		states.put(UnitType.LARGER_OR_EQUAL, largerOrEqual);
		states.put(UnitType.LEFT_PARENTHESIS, leftParenthesis);
		states.put(UnitType.MINUS, minus);
		states.put(UnitType.MULTIPLY, multiply);
		states.put(UnitType.NOT, not);
		states.put(UnitType.NOT_EQUAL, notEqual);
		states.put(UnitType.OR, or);
		states.put(UnitType.PLUS, plus);
		states.put(UnitType.RIGHT_PARENTHESIS, rightParenthesis);
		states.put(UnitType.SMALLER, smaller);
		states.put(UnitType.SMALLER_OR_EQUAL, smallerOrEqual);
		states.put(UnitType.VARIABLE, variable);
		states.put(UnitType.XOR, xor);
	}

	private void initCommaAndColon() {
		comma.addNextState(UnitType.VARIABLE, variable);
		comma.addNextState(UnitType.CONSTANT, constant);
		comma.addNextState(UnitType.FUNCTION, constant);

		colon.addNextState(UnitType.VARIABLE, variable);
	}

	private void initNot() {
		not.addNextState(UnitType.CONSTANT, constant);
		not.addNextState(UnitType.VARIABLE, variable);
		not.addNextState(UnitType.FUNCTION, function);
		not.addNextState(UnitType.NOT,not);

	}

	private void initRightParanthesis() {
		for (ValidatorState<UnitType, UnitType> operator : binomialOperators) {
			rightParenthesis.addNextState(operator.getState(), operator);
		}
		rightParenthesis.addNextState(UnitType.RIGHT_PARENTHESIS,
				rightParenthesis);
		rightParenthesis.addNextState(UnitType.EOF, eof);
	}

	private void initLeftParanthesis() {
		leftParenthesis.addNextState(UnitType.PLUS, plus);
		leftParenthesis.addNextState(UnitType.MINUS, minus);
		leftParenthesis.addNextState(UnitType.LEFT_PARENTHESIS,
				leftParenthesis);
		leftParenthesis.addNextState(UnitType.CONSTANT, constant);
		leftParenthesis.addNextState(UnitType.VARIABLE, variable);
		leftParenthesis.addNextState(UnitType.FUNCTION, function);
		leftParenthesis.addNextState(UnitType.RIGHT_PARENTHESIS,
				rightParenthesis);
		leftParenthesis.addNextState(UnitType.NOT, not);
	}

	private void initOperators() {
		binomialOperators.add(plus);
		binomialOperators.add(minus);
		binomialOperators.add(multiply);
		binomialOperators.add(divide);

		binomialOperators.add(and);
		binomialOperators.add(or);
		binomialOperators.add(xor);

		binomialOperators.add(bitAnd);
		binomialOperators.add(bitOr);

		binomialOperators.add(smaller);
		binomialOperators.add(smallerOrEqual);
		binomialOperators.add(larger);
		binomialOperators.add(largerOrEqual);
		binomialOperators.add(equal);
		binomialOperators.add(notEqual);

		for (ValidatorState<UnitType, UnitType> operator : binomialOperators) {
			operator.addNextState(UnitType.CONSTANT, constant);
			operator.addNextState(UnitType.VARIABLE, variable);
			operator.addNextState(UnitType.FUNCTION, function);
			operator.addNextState(UnitType.LEFT_PARENTHESIS,
					leftParenthesis);
			operator.addNextState(UnitType.NOT, not);
		}

	}

	private void initFunction() {
		function.addNextState(UnitType.LEFT_PARENTHESIS, leftParenthesis);
		function.addNextState(UnitType.COMMA, comma);
	}

	private void initVariable() {
		variable.addNextState(UnitType.PLUS, plus);
		variable.addNextState(UnitType.MINUS, minus);
		variable.addNextState(UnitType.MULTIPLY, multiply);
		variable.addNextState(UnitType.DIVIDE, divide);

		variable.addNextState(UnitType.BIT_AND, bitAnd);
		variable.addNextState(UnitType.BIT_OR, bitOr);

		variable.addNextState(UnitType.AND, and);
		variable.addNextState(UnitType.OR, or);
		variable.addNextState(UnitType.XOR, xor);

		variable.addNextState(UnitType.LARGER, larger);
		variable.addNextState(UnitType.LARGER_OR_EQUAL, largerOrEqual);
		variable.addNextState(UnitType.SMALLER, smaller);
		variable.addNextState(UnitType.SMALLER_OR_EQUAL, smallerOrEqual);
		variable.addNextState(UnitType.EQUAL, equal);
		variable.addNextState(UnitType.NOT_EQUAL, notEqual);

		variable.addNextState(UnitType.COLON, colon);
		variable.addNextState(UnitType.COMMA, colon);

		variable.addNextState(UnitType.RIGHT_PARENTHESIS,
				rightParenthesis);

		variable.addNextState(UnitType.EOF, eof);
	}

	private void initConstant() {
		constant.addNextState(UnitType.PLUS, plus);
		constant.addNextState(UnitType.MINUS, minus);
		constant.addNextState(UnitType.MULTIPLY, multiply);
		constant.addNextState(UnitType.DIVIDE, divide);

		constant.addNextState(UnitType.BIT_AND, bitAnd);
		constant.addNextState(UnitType.BIT_OR, bitOr);

		constant.addNextState(UnitType.AND, and);
		constant.addNextState(UnitType.OR, or);
		constant.addNextState(UnitType.XOR, xor);

		constant.addNextState(UnitType.LARGER, larger);
		constant.addNextState(UnitType.LARGER_OR_EQUAL, largerOrEqual);
		constant.addNextState(UnitType.SMALLER, smaller);
		constant.addNextState(UnitType.SMALLER_OR_EQUAL, smallerOrEqual);
		constant.addNextState(UnitType.EQUAL, equal);
		constant.addNextState(UnitType.NOT_EQUAL, notEqual);

		constant.addNextState(UnitType.COMMA, comma);

		constant.addNextState(UnitType.RIGHT_PARENTHESIS,
				rightParenthesis);

		constant.addNextState(UnitType.EOF, eof);
	}

	private Collection<ValidatorState<UnitType, UnitType>> binomialOperators = new LinkedList<>();

	private ValidatorState<UnitType, UnitType> constant = new ValidatorState<UnitType, UnitType>(
			UnitType.CONSTANT, true);

	private ValidatorState<UnitType, UnitType> variable = new ValidatorState<UnitType, UnitType>(
			UnitType.VARIABLE, true);

	private ValidatorState<UnitType, UnitType> function = new ValidatorState<UnitType, UnitType>(
			UnitType.FUNCTION, true);

	private ValidatorState<UnitType, UnitType> plus = new ValidatorState<UnitType, UnitType>(
			UnitType.PLUS, true);

	private ValidatorState<UnitType, UnitType> minus = new ValidatorState<UnitType, UnitType>(
			UnitType.MINUS, true);

	private ValidatorState<UnitType, UnitType> multiply = new ValidatorState<UnitType, UnitType>(
			UnitType.MULTIPLY, false);

	private ValidatorState<UnitType, UnitType> divide = new ValidatorState<UnitType, UnitType>(
			UnitType.DIVIDE, false);

	private ValidatorState<UnitType, UnitType> leftParenthesis = new ValidatorState<UnitType, UnitType>(
			UnitType.LEFT_PARENTHESIS, true);

	private ValidatorState<UnitType, UnitType> rightParenthesis = new ValidatorState<UnitType, UnitType>(
			UnitType.RIGHT_PARENTHESIS, false);

	private ValidatorState<UnitType, UnitType> larger = new ValidatorState<UnitType, UnitType>(
			UnitType.LARGER, false);

	private ValidatorState<UnitType, UnitType> largerOrEqual = new ValidatorState<UnitType, UnitType>(
			UnitType.LARGER_OR_EQUAL, false);

	private ValidatorState<UnitType, UnitType> smaller = new ValidatorState<UnitType, UnitType>(
			UnitType.SMALLER, false);

	private ValidatorState<UnitType, UnitType> smallerOrEqual = new ValidatorState<UnitType, UnitType>(
			UnitType.SMALLER_OR_EQUAL, false);

	private ValidatorState<UnitType, UnitType> equal = new ValidatorState<UnitType, UnitType>(
			UnitType.EQUAL, false);

	private ValidatorState<UnitType, UnitType> notEqual = new ValidatorState<UnitType, UnitType>(
			UnitType.NOT_EQUAL, false);

	private ValidatorState<UnitType, UnitType> not = new ValidatorState<UnitType, UnitType>(
			UnitType.NOT, true);

	private ValidatorState<UnitType, UnitType> and = new ValidatorState<UnitType, UnitType>(
			UnitType.AND, false);

	private ValidatorState<UnitType, UnitType> or = new ValidatorState<UnitType, UnitType>(
			UnitType.OR, false);

	private ValidatorState<UnitType, UnitType> xor = new ValidatorState<UnitType, UnitType>(
			UnitType.XOR, false);

	private ValidatorState<UnitType, UnitType> colon = new ValidatorState<UnitType, UnitType>(
			UnitType.COLON, false);

	private ValidatorState<UnitType, UnitType> comma = new ValidatorState<UnitType, UnitType>(
			UnitType.COMMA, false);

	private ValidatorState<UnitType, UnitType> bitAnd = new ValidatorState<UnitType, UnitType>(
			UnitType.BIT_AND, false);

	private ValidatorState<UnitType, UnitType> bitOr = new ValidatorState<UnitType, UnitType>(
			UnitType.BIT_OR, false);

	private ValidatorState<UnitType, UnitType> eof = new ValidatorState<UnitType, UnitType>(
			UnitType.EOF, true);

}
