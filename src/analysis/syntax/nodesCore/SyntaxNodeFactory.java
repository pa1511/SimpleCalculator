package analysis.syntax.nodesCore;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import analysis.UnitType;
import analysis.lexical.ILexicalUnit;

public class SyntaxNodeFactory {

	@SuppressWarnings("unchecked")
	public static ISyntaxNode getNode(ILexicalUnit unit) {
		ISyntaxNode node = null;

		String nodeName = getNodeName(unit.getValue(), unit.getType());

		Class<ISyntaxNode> clazz = null;

		try {
			clazz = (Class<ISyntaxNode>) Class.forName("analysis.syntax.nodes."
					+ nodeName);
		} catch (ClassNotFoundException | SecurityException
				| IllegalArgumentException e) {
			throwException(nodeName, unit.getType());
		}

		Constructor<?> ctr;

		try {
			ctr = clazz.getConstructor(ILexicalUnit.class);
			node = (ISyntaxNode) ctr.newInstance(unit);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throwException(nodeName, unit.getType());
		}

		return node;
	}

	private static void throwException(String nodeName, UnitType type) {
		throw new IllegalArgumentException(
				"An error occurred while creating the wanted object: "
						+ nodeName + " " + type);
	}

	private static String getNodeName(String nodeName, UnitType type) {
		nodeName = nodeNames.get(nodeName.trim().toUpperCase());

		if (type == UnitType.CONSTANT)
			nodeName = "Constant";

		if (type == UnitType.VARIABLE)
			nodeName = "Variable";
		return nodeName;
	}

	private static String nodeFolderName = "bin/analysis/syntax/nodes";
	private static Map<String, String> nodeNames = new HashMap<>();

	static {
		readNodeNames();
		for (UnitType type : UnitType.values()) {
			if (type.getSymbol() != null)
				nodeNames.put(type.getSymbol(), type.toString());
		}
	}

	private static void readNodeNames() {
		for (final File fileEntry : new File(nodeFolderName).listFiles()) {
			String nodeName = fileEntry.getName().replaceAll(".class", "");
			nodeNames.put(nodeName.toUpperCase(), nodeName);
		}
	}

	public static Collection<String> getNodeNames() {
		return nodeNames.values();
	}
}
