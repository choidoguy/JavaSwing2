package file;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import step.STEP01;

public class FileTable {
	
	private DefaultTableModel dm = new DefaultTableModel();
	private STEP01 step01;
	private JTable table;
	
	private Object[] objColNms = new Object[] { "File Path", "Del"  };
	
	public FileTable(STEP01 step01, Object[][] arg0) {
		this.step01 = step01;
		dm.setDataVector(arg0, objColNms);
		initTable();
	} // end public FileTable()
	
	private void initTable() {
		table = new JTable(dm);
		table.getColumn("File Path").setPreferredWidth(630);
		table.getColumn("Del").setCellRenderer(new TableCell());
		table.getColumn("Del").setCellEditor(new TableCell());
	} // end private void initTable()
	
	public DefaultTableModel getDefaultTableModel() { return dm; }
	
	public JTable getTable() { return table; }
	
	public void JTableRemoveRow() {
		int row = table.getSelectedRow();
		if (row == -1) return;

		//DefaultTableModel model = (DefaultTableModel) table.getModel();
		step01.removeFilePath(table.getValueAt(row, 0).toString());
		
		return;
		
		/*
		model.removeRow(row);

		int rowCnt = table.getRowCount();

		JTable clonTable;
		
		if (rowCnt > 0) {
			Vector vector = model.getDataVector();
			Object[][] objData = new Object[vector.size()][((Vector) vector.get(0)).size()];
			for (int i = 0; i < vector.size(); i++) {
				Vector vec = (Vector) vector.get(i);
				for (int j = 0; j < vec.size(); j++) {
					objData[i][j] = vec.get(j);
				}
			}

			DefaultTableModel clonModel = new DefaultTableModel(objData, objColNms);
			clonTable = new JTable(clonModel);
			clonTable.getColumn("Del").setCellRenderer(new TableCell());
			clonTable.getColumn("Del").setCellEditor(new TableCell());
			
			
		} else {
			DefaultTableModel clonModel = new DefaultTableModel(null, objColNms);
			clonTable = new JTable(clonModel);
		}
		
		table = clonTable;
		table.getColumn("File Path").setPreferredWidth(630);
		
		step01.repaint(table);
		*/
		
	} // end public void JTableRemoveRow()
	
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		JButton jb;

		public TableCell() {
			jb = new JButton("Del");
			jb.addActionListener(e -> { JTableRemoveRow(); });
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return jb;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return jb;
		}
	} // end class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer
	
} // end public class FileTable extends JTable

