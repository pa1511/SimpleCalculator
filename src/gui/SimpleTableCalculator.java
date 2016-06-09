package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import analysis.ExpressionException;
import dataModelInterfaces.AbstractCalculatorTableModel;
import dataModels.TableModel;

public class SimpleTableCalculator extends JFrame{

	private static final long serialVersionUID = 1L;

	public SimpleTableCalculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		setMaximumSize(new Dimension(500, 300));
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		initGUI();

	}

	//=====================================================================================================================
	
	private JTable table;
	private AbstractCalculatorTableModel tableModel;
	
	private JButton execute;
	private JLabel label;
	private JTextField console;
	private JLabel value;
	
	private void initGUI() {
		setLayout(new BorderLayout());
		
		initTable();
		
		initLowerPanel();
		
		pack();
	}

	private void initTable() {
		tableModel = new TableModel();
		table = new JTable(tableModel) {

			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int col) {
				if (col == 0) {
					return this
							.getTableHeader()
							.getDefaultRenderer()
							.getTableCellRendererComponent(this,
									this.getValueAt(row, col), true, true, row,
									col);
				} else {
					return super.prepareRenderer(renderer, row, col);
				}
			}

			@Override
			public void setValueAt(Object aValue, int row, int column) {
				super.setValueAt(aValue, row, column);
			}

		};
		
		table.setAutoCreateRowSorter(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		
		table.setPreferredSize(table.getPreferredSize());
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		add(scrollPane, BorderLayout.CENTER);

	}

	private void initLowerPanel() {
		
		//execute button initialization
		FlowLayout buttonHolderLayout = new FlowLayout();
		buttonHolderLayout.setAlignment(FlowLayout.RIGHT);
		JPanel buttonHolder = new JPanel(buttonHolderLayout);
		execute = new JButton(new CalculationAction());
		buttonHolder.add(execute);
		
		//lower panel initialization
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());
		
		label = new FunctionLabel(table,tableModel);
		lowerPanel.add(label,BorderLayout.PAGE_START);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));

		console = new Console(table, tableModel);
		
		console.getInputMap()
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		console.getActionMap().put("Enter", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				execute.doClick();
			}
		});
		value = new ValueLabel(table, tableModel);

		panel.add(console);
		panel.add(value);
		
		lowerPanel.add(panel,BorderLayout.CENTER);
		lowerPanel.add(buttonHolder,BorderLayout.PAGE_END);
		
		//adding lowerPanel to the main frame
		add(lowerPanel,BorderLayout.PAGE_END);
	}
	
	private class CalculationAction extends AbstractAction implements ListSelectionListener{
		
		private static final long serialVersionUID = 1L;

		public CalculationAction() {
			super("Calculate");
			table.getSelectionModel().addListSelectionListener(this);
			table.getColumnModel().getSelectionModel().addListSelectionListener(this);
			setEnabled(false);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				tableModel.calculateValueAt(table.getSelectedRow(),table.getSelectedColumn(),console.getText());
			}
			catch(ExpressionException | IllegalArgumentException ex){
				JOptionPane.showMessageDialog(SimpleTableCalculator.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//				ex.printStackTrace();
			}
			
			repaint();

		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			setEnabled(true);
		}
	}
	
}
