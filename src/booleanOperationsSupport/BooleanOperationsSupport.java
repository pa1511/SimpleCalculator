package booleanOperationsSupport;

public class BooleanOperationsSupport {

	
	public static boolean getBooleanValue(Double number){
		if(number<=1e-7)
			return false;
		
		return true;
	}
	
	public static Double getDoubleValue(boolean value){
		if(value==true)
			return 1.0;
		
		return 0.0;
	}
}
