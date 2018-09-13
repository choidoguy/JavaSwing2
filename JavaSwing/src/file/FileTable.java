package file;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class FileTable {
	
	private DefaultTableModel dm = new DefaultTableModel();
	private JTable table;
	
	public FileTable() {
		dm.setDataVector(null, new Object[] { "File Path", "Del" });
		initTable();
	} // end public FileTable()

	public FileTable(Object[][] arg0) {
		dm.setDataVector(arg0, new Object[] { "File Path", "Del" });
		initTable();
	} // end public FileTable()
	
	private void initTable() {
		table = new JTable(dm);
		table.getColumn("Del").setCellRenderer(new ButtonRenderer());
		table.getColumn("Del").setCellEditor(new ButtonEditor(new JCheckBox()));
		
		table.getColumn("File Path").setPreferredWidth(630);
		//table.getColumn("Del").setPreferredWidth(10);
	}
	
	public DefaultTableModel getDefaultTableModel() { return dm; }
	
	public JTable getTable() { return table; }
} // end public class FileTable extends JTable

/**
 * @version 1.0 11/09/98
 */

class ButtonRenderer extends JButton implements TableCellRenderer {
	public ButtonRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(UIManager.getColor("Button.background"));
		}
		setText((value == null) ? "" : value.toString());
		return this;
	}
} // end class ButtonRenderer extends JButton implements TableCellRenderer

class ButtonEditor extends DefaultCellEditor {
	protected JButton button;
	private String label;
	private boolean isPushed;
	
	private JTable table;
	private Object value;
	private boolean isSelected;
	private int row;
	private int column;

	public ButtonEditor(JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		isPushed = true;
		
		
		this.table = table;
		this.value = value;
		this.isSelected = isSelected;
		this.row = row;
		this.column = column;
		
		return button;
	}

	public Object getCellEditorValue() {
		if (isPushed) {
			//
			//
			//JOptionPane.showMessageDialog(button, label + ": Ouch!");
			// System.out.println(label + ": Ouch!");
			
			System.out.println("row : "+ this.row);
			((DefaultTableModel) this.table.getModel()).removeRow(this.row);
		}
		isPushed = false;
		return new String(label);
	}

	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
} // end class ButtonEditor extends DefaultCellEditor
