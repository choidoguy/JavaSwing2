package file;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import step.STEP02;

public class Step02Table {
	
	@SuppressWarnings("serial")
	private DefaultTableModel dm = new DefaultTableModel() {
		public boolean isCellEditable(int r, int c) {
			if(c != 1 )return false; // edit ���� �Ұ� �б� ����
			else return true;
		}
	};
	
	private STEP02 step02;
	private JTable table;
	
	private Object[] objColNms = new Object[] { "���ϰ��", "����"  };
	
	public Step02Table(STEP02 step02, Object[][] arg0) {
		this.step02 = step02;
		dm.setDataVector(arg0, objColNms);
		initTable();
	} // end public FileTable()
	
	private void initTable() {
		table = new JTable(dm);
		table.getColumn("���ϰ��").setPreferredWidth(300);
//		table.getColumn("Del").setCellRenderer(new TableCell());
//		table.getColumn("Del").setCellEditor(new TableCell());
	} // end private void initTable()
	
	public DefaultTableModel getDefaultTableModel() { return dm; }
	
	public JTable getTable() { return table; }
	
} // end public class FileTable extends JTable

