package analysis.inputValidation;

import analysis.lexical.ILexicalAutomat;

public interface IValidator {

	/**
	 * Takes a {@link ILexicalAutomat} in the constructor
	 * @return true if the lexical units are syntactically valid, false if not
	 */
	public Boolean IsValid();
}
