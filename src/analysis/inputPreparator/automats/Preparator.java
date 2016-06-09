package analysis.inputPreparator.automats;

import analysis.UnitType;
import analysis.inputPreparator.IPreparator;
import analysis.lexical.ILexicalAutomat;
import analysis.lexical.ILexicalUnit;
import analysis.lexical.automats.LexicalAutomat;

public class Preparator implements IPreparator{

	private ILexicalAutomat automat;
	private ILexicalAutomat prepared;

	public Preparator(ILexicalAutomat automat) {
		this.automat = automat;
	}

	@Override
	public ILexicalAutomat getPrepared() {

		if (prepared != null)
			return prepared;

		StringBuilder preparedExpression = new StringBuilder();

		for (int i = 0; i < automat.getNumberOfAtoms(); i++) {

			ILexicalUnit unit = automat.getAtomAt(i);

			if ((unit.getType() == UnitType.PLUS || unit.getType() == UnitType.MINUS)
					&& (i == 0 || automat.getAtomAt(i - 1).getType() == UnitType.LEFT_PARENTHESIS)) {
				preparedExpression.append("0 ");
			}

			if (unit.getType() != UnitType.EOF)
				preparedExpression.append(unit.getValue() + " ");
		}

		String preparedExpressionString = preparedExpression.toString();
		CharSequence cs = "( )";
		
		while(!preparedExpressionString.isEmpty() && preparedExpressionString.contains(cs))
			preparedExpressionString = preparedExpressionString.replaceAll("\\( \\)", "").replaceAll("\\s+", " ");
		
		prepared = new LexicalAutomat(preparedExpressionString);

		return prepared;
	}
}
