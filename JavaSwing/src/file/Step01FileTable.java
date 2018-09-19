package file;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import step.STEP01;

public class Step01FileTable {
	
	@SuppressWarnings("serial")
	private DefaultTableModel dm = new DefaultTableModel() {
		public boolean isCellEditable(int r, int c) {
			if(c != 1 )return false; // edit ���� �Ұ� �б� ����
			else return true;
		}
	};
	private STEP01 step01;
	private JTable table;
	
	private Object[] objColNms = new Object[] { "���ϰ��", "����"  };
	
	public Step01FileTable(STEP01 step01, Object[][] arg0) {
		this.step01 = step01;
		dm.setDataVector(arg0, objColNms);
		initTable();
	} // end public FileTable()
	
	private void initTable() {
		table = new JTable(dm);
		table.getColumn("���ϰ��").setPreferredWidth(630);
		table.getColumn("����").setCellRenderer(new TableCell());
		table.getColumn("����").setCellEditor(new TableCell());
	} // end private void initTable()
	
	public DefaultTableModel getDefaultTableModel() { return dm; }
	
	public JTable getTable() { return table; }
	
	@SuppressWarnings("serial")
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		JButton jb;

		public TableCell() {
			jb = new JButton("����");
			jb.addActionListener(e -> {
				if (table.getSelectedRow() == -1) return;
				step01.removeFilePath(table.getValueAt(table.getSelectedRow(), 0).toString());
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
