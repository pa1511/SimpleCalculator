package analysis.syntax.nodeVisitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SyntaxNodeVisitorFactory {

	@SuppressWarnings("unchecked")
	public static ISyntaxNodeVisitor getSyntaxNodeVisitor(String visitorName) {
		ISyntaxNodeVisitor visitor = null;

		Class<ISyntaxNodeVisitor> clazz = null;

		try {
			clazz = (Class<ISyntaxNodeVisitor>) Class
					.forName("analysis.syntax.nodeVisitor.visitors."
							+ visitorName);
		} catch (ClassNotFoundException | SecurityException
				| IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}

		Constructor<?> ctr;

		try {
			ctr = clazz.getConstructor();
			visitor = (ISyntaxNodeVisitor) ctr.newInstance();
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}

		return visitor;
	}

}
