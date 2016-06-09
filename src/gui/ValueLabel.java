package gui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataModelInterfaces.AbstractCalculatorTableModel;
import dataModelInterfaces.ICalculatorTableModelListener;

public class ValueLabel extends JLabel implements ListSelectionListener,
		ICalculatorTableModelListener {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private AbstractCalculatorTableModel tableModel;

	private String valueString = "";

	public ValueLabel(JTable table, AbstractCalculatorTableModel tableModel) {
		this.tableModel = tableModel;
		this.table = table;

		table.getSelectionModel().addListSelectionListener(this);
		table.getColumnModel().getSelectionModel()
				.addListSelectionListener(this);

		tableModel.addListener(this);

		setFont(new Font(getFont().getName(), Font.PLAIN, 12));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		readValues();
	}

	@Override
	public void tableChanged() {
		readValues();
	}

	private void readValues() {

		if (!(tableModel == null || table == null)) {

			Object value = tableModel.getValueAt(table.getSelectedRow(),
					table.getSelectedColumn());

			if (value == null)
				valueString = "0";
			else
				valueString = value.toString();
		}

		setText("Value: " + valueString);
	}

}
