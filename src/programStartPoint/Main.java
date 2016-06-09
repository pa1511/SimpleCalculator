package programStartPoint;

import java.awt.EventQueue;
import gui.SimpleTableCalculator;
import javax.swing.JFrame;

public class Main {
	
	private Main() {
	}

	public static void main(String[] args) {
		EventQueue.invokeLater( () ->{
				JFrame frame = new SimpleTableCalculator();
				frame.setVisible(true);
			});
	}
}
