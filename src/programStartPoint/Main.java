package programStartPoint;

import java.awt.EventQueue;
import gui.SimpleTableCalculator;

public final class Main {

	/**
	 * Instances of this class should never be created
	 */
	private Main() {
	}

	/**
	 * Starts the simple calculator program
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater( () ->{
				new SimpleTableCalculator().setVisible(true);
			});
	}
}
