package analysis.lexical;

public interface ILexicalAutomat extends Iterable<ILexicalUnit>{
	
	public int getNumberOfAtoms();
	public ILexicalUnit getAtomAt(int index);


}
