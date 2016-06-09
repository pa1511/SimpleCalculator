package analysis.lexical.automats;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import analysis.UnitType;
import analysis.lexical.ILexicalAutomat;
import analysis.lexical.ILexicalUnit;

/**
 * Lexical automat.
 * <br/> 
 * Objects of this class perform a lexical analysis of the given input. 
 * <br/>
 * Lexical analysis breaks the input into atoms which are lexical units.
 * @author Petar Afriæ
 *
 */
public class LexicalAutomat implements ILexicalAutomat{

	private char[] expression;
	private List<ILexicalUnit> atoms = new LinkedList<ILexicalUnit>();
	
	/**
	 * Class constructor which take the expression for lexical analysis
	 * @param expression
	 */
	public LexicalAutomat(String expression) {
		this.expression = expression.trim().toUpperCase().toCharArray();
		calculateAtoms();
	}

	/**
	 * lexical analysis
	 */
	private void calculateAtoms() {
		int start = 0;
		int end = 0;
		int last = 0;
		
		if(expression.length==0){
			atoms.add( new LexicalUnit("EOF", UnitType.EOF));//TODO
			return;
		}
			
		
		do{

			if(Character.isWhitespace(expression[last])){
				start++;
				last++;
			}
			
			UnitType currentType = null;
			UnitType lastType = null;
			
			do{
				last++;
				String currentSubString = new String(expression, start, last-start);
				currentType = UnitType.getAtomTypeFor(currentSubString);
				
				if(currentType!=null){
					end = last;
					lastType = currentType;
				}else if(last+1<expression.length && UnitType.getAtomTypeFor(new String(expression,start, last-start+1))!=null){
					currentType = UnitType.EOF;//just has to be different than null
				}
				else if(lastType == null){
					throw new IllegalArgumentException("Unknown symbol expression:" + currentSubString);
				}
					
			}while(currentType != null && last<expression.length);
			
			String lexicalUnit = new String(expression, start, end-start);
			atoms.add(new LexicalUnit(lexicalUnit, UnitType.getAtomTypeFor(lexicalUnit)));
			start = end;
			last = end;
		
		}while(last<expression.length);
		
		atoms.add( new LexicalUnit("EOF", UnitType.EOF));//TODO
	}

	/**
	 * Returns the number of atoms
	 * @return <code>int</code> number of atoms 
	 */
	@Override
	public int getNumberOfAtoms(){
		return atoms.size();
	}
	
	/**
	 * Returns the atom at the given position. If the index is out of range a IllegalArgumentException is thrown.
	 * @param index - position of the atom
	 * @return Atom
	 */
	@Override
	public ILexicalUnit getAtomAt(int index){
		
		if(atoms.size()<=index)
			throw new IllegalArgumentException();
		
		return atoms.get(index);
	}
	
	//============================================================================================================
	
	/**
	 * Implementation of the {@link Iterable} interface
	 */
	@Override
	public Iterator<ILexicalUnit> iterator() {
		return new LexicalUnitsIterator();
	}
	
	/**
	 * Implementation of the {@link Iterator} interface
	 *
	 */
	private class LexicalUnitsIterator implements Iterator<ILexicalUnit>{
		
		private int currentPosition = 0;
		
		@Override
		public boolean hasNext() {
			
			if(atoms.size()>currentPosition)
				return true;
			
			return false;
		}
		
		@Override
		public ILexicalUnit next() {
			return getAtomAt(currentPosition++);
		}
	}
}
