package gui;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataModelInterfaces.AbstractCalculatorTableModel;

public class Console extends JTextField implements ListSelectionListener{

	private static final long serialVersionUID = 1L;

	private JTable table;
	private	AbstractCalculatorTableModel tableModel;
	
	public Console(JTable table,AbstractCalculatorTableModel tableModel) {
		this.tableModel = tableModel;
		this.table = table;
		
		table.getSelectionModel().addListSelectionListener(this);
		table.getColumnModel().getSelectionModel().addListSelectionListener(this);
		
		setEditable(false);
		setFont(new Font(getFont().getName(), Font.PLAIN, 12));
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		setEditable(true);
		String expression = tableModel.getCellExpression(table.getSelectedRow(), table.getSelectedColumn());
		setText(expression);
		requestFocus();
	}
}
