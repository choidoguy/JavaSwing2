package file;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import step.STEP02;

public class Step02FileTable {
	
	@SuppressWarnings("serial")
	private DefaultTableModel dm = new DefaultTableModel() {
		public boolean isCellEditable(int r, int c) {
			if(c != 1 )return false; // edit 수정 불가 읽기 전용
			else return true;
		}
	};
	private STEP02 step02;
	private JTable table;
	
	private Object[] objColNms = new Object[] { "File Path", "Del"  };
	
	public Step02FileTable(STEP02 step02, Object[][] arg0) {
		this.step02 = step02;
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
	
	@SuppressWarnings("serial")
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		JButton jb;

		public TableCell() {
			jb = new JButton("Del");
			jb.addActionListener(e -> {
				if (table.getSelectedRow() == -1) return;
				step02.removeFilePath(table.getValueAt(table.getSelectedRow(), 0).toString());
			});
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

