package file;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			if(c != 1 )return false; // edit 수정 불가 읽기 전용
			else return true;
		}
	};
	private STEP01 step01;
	private JTable table;
	
	//"<html><center><b>" + efscidpi[i].getSheetNm() + "</b><br><font color=#88929f>" + efscidpi[i].getAreaNm() + "</font>";
	private Object[] objColNms = new Object[] { "파일경로", "삭제"};
	
	public Step01FileTable(STEP01 step01, Object[][] arg0) {
		this.step01 = step01;
		dm.setDataVector(arg0, objColNms);
		initTable();
	} // end public FileTable()
	
	private void initTable() {
		table = new JTable(dm);
		table.getColumn("파일경로").setPreferredWidth(630);
		table.getColumn("삭제").setCellRenderer(new TableCell());
		table.getColumn("삭제").setCellEditor(new TableCell());
		
		table.getTableHeader().setReorderingAllowed(false); // 이동불가
//		table.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
	} // end private void initTable()
	
	public DefaultTableModel getDefaultTableModel() { return dm; }
	
	public JTable getTable() { return table; }
	
	@SuppressWarnings("serial")
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		JButton jb;

		public TableCell() {
			jb = new JButton("삭제");
			jb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (table.getSelectedRow() == -1) return;
					step01.removeFilePath(table.getValueAt(table.getSelectedRow(), 0).toString());
				}
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

