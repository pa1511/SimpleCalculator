package analysis;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dataModelInterfaces.IVariableTable;

public class ExpressionHandlerFactory {
	
	@SuppressWarnings("unchecked")
	public static IExpressionHandler gettSyntaxAutomat(String expressionHandlerName,
			IVariableTable table) {
		IExpressionHandler handler = null;

		Class<IExpressionHandler> clazz = null;

		try {
			clazz = (Class<IExpressionHandler>) Class
					.forName("analysis.expressionHandlers."
							+ expressionHandlerName);
		} catch (ClassNotFoundException | SecurityException
				| IllegalArgumentException e) {
			throw new ExpressionException(e);
		}

		Constructor<?> ctr;

		try {
			ctr = clazz.getConstructor(IVariableTable.class);
			handler = (IExpressionHandler) ctr.newInstance(table);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new ExpressionException(e);
		}

		return handler;
	}

}
