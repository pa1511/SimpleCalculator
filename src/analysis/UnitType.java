package analysis;

import java.util.regex.Pattern;

/**
 * Enumeration specifying possible lexical units
 * @author Petar Afriæ
 *
 */
public enum UnitType {
	
	VARIABLE("[A-Z][0-9]+",null,"Variable",null,null),
	CONSTANT("[0-9]+[.][0-9]*|[0-9]+",null,"Constant",null,null),
	FUNCTION("[A-Z]+",null,"Function",0,50),
	COLON(":",":","Colon",22,21),
	LEFT_PARENTHESIS("\\(","(","LeftParenthesis",0,50),
	RIGHT_PARENTHESIS("\\)",")","RightParenthesis",null,null),
	NOT("!","!","Not",21,20),
	MULTIPLY("\\*","*","Multiply",19,18),
	DIVIDE("/","/","Divide",19,18),
	PLUS("\\+","+","Plus",17,16),
	MINUS("\\-","-","Minus",17,16),
	LARGER(">",">","Larger",15,14),
	LARGER_OR_EQUAL(">=",">=","LargerOrEqual",15,14),
	SMALLER("<","<","Smaller",15,14),
	SMALLER_OR_EQUAL("<=","<=","SmallerOrEqual",15,14),
	EQUAL("==","==","Equal",13,12),
	NOT_EQUAL("!=","!=","NotEqual",13,12),
	XOR("\\^","^","Xor",11,10),
	BIT_OR("\\|","|","BitOr",9,8),
	BIT_AND("&","&","BitAnd",7,6),
	AND("&&","&&","And",5,4),
	OR("\\|\\|","||","Or",3,2),
	COMMA(",",",","Comma",2,1),
	EOF("_EOF_",null,"Eof",null,null);
	
	private Pattern pattern;
	private String symbol;
	private String name;
	private Integer stackPriority;
	private Integer inputPriority;
		
	private UnitType(String regex,String symbol,String name,Integer stackPriority, Integer inputPriority){
		pattern = Pattern.compile(regex);
		this.symbol = symbol;
		this.name = name;
		this.stackPriority = stackPriority;
		this.inputPriority = inputPriority;
	}
	
	public String getPattern() {
		return pattern.pattern();
	}
	
	public static UnitType getAtomTypeFor(String pattern){
		pattern = pattern.trim();
		
		for(UnitType atom: UnitType.values()){
			if(atom.pattern.matcher(pattern).matches()){
				return atom;
			}
		}
		
		return null;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Integer getStackPriority() {
		return stackPriority;
	}
	
	public Integer getInputPriority() {
		return inputPriority;
	}
}
