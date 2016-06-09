package gui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataModelInterfaces.AbstractCalculatorTableModel;

public class FunctionLabel extends JLabel implements ListSelectionListener{

	private static final long serialVersionUID = 1L;
	
	private JTable table;

	private String defaultText = "f(x)";

	public FunctionLabel(JTable table, AbstractCalculatorTableModel tableModel) {
		
		setEnabled(false);
		
		this.table = table;
		
		table.getSelectionModel().addListSelectionListener(this);
		table.getColumnModel().getSelectionModel().addListSelectionListener(this);
			
		setText(defaultText);
		setFont(new Font(getFont().getName(), Font.ITALIC, 12));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		setEnabled(true);
		setText(table.getModel().getColumnName(table.getSelectedColumn())+table.getSelectedRow()+defaultText);		
	}
}
