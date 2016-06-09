package analysis;

public class ExpressionException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private Exception original ;
	
	public ExpressionException(Exception ex) {
		original = ex;
	}
	
	public Exception getOriginalException(){
		return original; 
	}
	
	@Override
	public String getMessage() {
		return "Invalid expression";
	}
	
}
