package analysis.lexical.automats;

import analysis.UnitType;
import analysis.lexical.ILexicalUnit;

/**
 * A single lexical unit.
 * @author Petar
 *
 */
public class LexicalUnit implements ILexicalUnit{

	private String value;
	private UnitType type;
	
	public LexicalUnit(String value, UnitType type) {
		this.value = value;
		this.type = type;
	}
	
	/**
	 * String representing the unit
	 * @return
	 */
	@Override
	public String getValue() {
		return value;
	}
	
	/**
	 * Type of lexical unit
	 * @return
	 */
	@Override
	public UnitType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
