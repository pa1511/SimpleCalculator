package analysis;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dataModelInterfaces.IVariableTable;
import analysis.inputPreparator.IPreparator;
import analysis.inputValidation.IValidator;
import analysis.lexical.ILexicalAutomat;
import analysis.syntax.ISyntaxAutomat;

public class AutomatFactory {

	@SuppressWarnings("unchecked")
	public static ILexicalAutomat getLexAutomat(String lexAutomatName,String expression) {

		Class<ILexicalAutomat> clazz = null;

		try {
			clazz = (Class<ILexicalAutomat>) Class.forName("analysis.lexical.automats."
					+ lexAutomatName);
		} catch (ClassNotFoundException | SecurityException
				| IllegalArgumentException e) {
		
			throw new ExpressionException(e);
		}

		try {
			Constructor<?> ctr = clazz.getConstructor(String.class);
			return (ILexicalAutomat) ctr.newInstance(expression);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new ExpressionException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static IValidator getValidatorAutomat(String validatorName,ILexicalAutomat automat) {


		Class<IValidator> clazz = null;

		try {
			clazz = (Class<IValidator>) Class.forName("analysis.inputValidator.automats."
					+ validatorName);
		} catch (ClassNotFoundException | SecurityException
				| IllegalArgumentException e) {
			throw new ExpressionException(e);
		}

		try {
			Constructor<?>	ctr = clazz.getConstructor(ILexicalAutomat.class);
			return (IValidator) ctr.newInstance(automat);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new ExpressionException(e);
		}

	}

	@SuppressWarnings("unchecked")
	public static IPreparator getPreparatorAutomat(String preparatorName,ILexicalAutomat automat) {

		Class<IPreparator> clazz = null;

		try {
			clazz = (Class<IPreparator>) Class.forName("analysis.inputPreparator.automats."
					+ preparatorName);
		} catch (ClassNotFoundException | SecurityException
				| IllegalArgumentException e) {
			throw new ExpressionException(e);
		}

		try {
			Constructor<?> ctr = clazz.getConstructor(ILexicalAutomat.class);
			return (IPreparator) ctr.newInstance(automat);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new ExpressionException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static ISyntaxAutomat gettSyntaxAutomat(String syxAutomatName,ILexicalAutomat lexAutomat,IVariableTable table) {

		Class<ISyntaxAutomat> clazz = null;

		try {
			clazz = (Class<ISyntaxAutomat>) Class.forName("analysis.syntax.automats."
					+ syxAutomatName);
		} catch (ClassNotFoundException | SecurityException
				| IllegalArgumentException e) {
			throw new ExpressionException(e);
		}

		try {
			Constructor<?> ctr = clazz.getConstructor(ILexicalAutomat.class,IVariableTable.class);
			return (ISyntaxAutomat) ctr.newInstance(lexAutomat,table);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new ExpressionException(e);
		}
		
	}

}
