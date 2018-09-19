package step;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import come.NorPanel;
import come.SouPanel;
import main.Scheduler;

@SuppressWarnings("serial")
public class STEP02 extends JPanel {
	private NorPanel norPanel;
	private SouPanel souPanel;

	private JPanel cenPanel;
	private JTable table;

	private File[] fileList;
	
	JTextField oututPathTF;
	
	public STEP02(Scheduler scheduler, String sPrev, String sNext, File[] fileList) {
		this.fileList = fileList;
		// NORTH
		norPanel = new NorPanel("STEP02", "SPSS");
		this.add(norPanel);

		// SOUTH
		souPanel = new SouPanel(scheduler, sPrev, "step02",  sNext);
		this.add(souPanel);

		// CENTER
		cenPanel = new JPanel();
		Object[][] objlist = fileList2objs();
		for(int i =0 ; i<objlist.length ; i++) {
			objlist[i][1] = "";
		}
		this.table = (new Step02Table(objlist)).getTable();
		centerPanelInit();
		this.add(cenPanel);

		BorderLayout borderLayout = new BorderLayout();
		borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
		borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);
		borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
		setLayout(borderLayout);

	} // end public STEP01(

	// fileList[] �� JTable �� ���ڷ� ���� Object[][] ���·� ��ȯ�Ѵ�
	private Object[][] fileList2objs() {
		Object[][] obj = null;
		if (fileList != null && fileList.length != 0) {
			obj = new Object[fileList.length][2];
			for (int i = 0; i < fileList.length; i++) {
				try {
					obj[i][0] = fileList[i].getCanonicalPath();
					obj[i][1] = "Del";
				} // end try
				catch (java.io.IOException e) {
				}
			} // end for: through each dropped file
		} else {
			System.out.println("fileList2objs : fileList == null or fileList.length == 0 : return null");
		}
		return obj;
	}

	// centerPanel�� �ʱ�ȭ �Ѵ�
	private void centerPanelInit() {
		JScrollPane scroll = new JScrollPane(table);
		cenPanel.setBorder(new TitledBorder(new LineBorder(Color.black), ""));
		cenPanel.add(scroll);
		
		
			JPanel outPathPanel = new JPanel();
			JLabel label = new JLabel("��°�� : ");
			outPathPanel.add(label);
			oututPathTF = new JTextField();
			oututPathTF.setEnabled(false);
			outPathPanel.add(oututPathTF);
			JButton btn = new JButton("����");
			btn.addActionListener(e -> {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showDialog(this, null);
				File dir = jfc.getSelectedFile();
				oututPathTF.setText(dir==null?"":dir.getPath());
			});
			outPathPanel.add(btn);
			
			BorderLayout outPathLayout = new BorderLayout();
			outPathLayout.addLayoutComponent(label, BorderLayout.WEST);
			outPathLayout.addLayoutComponent(oututPathTF, BorderLayout.CENTER);
			outPathLayout.addLayoutComponent(btn, BorderLayout.EAST);
			
			outPathPanel.setLayout(outPathLayout);
		
		
		cenPanel.add(outPathPanel);

		BorderLayout layout = new BorderLayout();
		layout.addLayoutComponent(scroll, BorderLayout.CENTER);
		layout.addLayoutComponent(outPathPanel, BorderLayout.SOUTH);
		cenPanel.setLayout(layout);

	} // end private void initSTEP01(JScrollPane scroll)

	
	// fileList �� ��ȯ�Ѵ�
	public String getFileCase() {
		String tCase = "";
		for(int i=0 ; i<fileList.length ; i++) {
			String path = "";
			String ext = "";
			String cCase = "";
			try {
				path = fileList[i].getCanonicalPath();
				ext = path.substring(path.lastIndexOf(".") + 1).toUpperCase();
				if("sav".equals(ext) || "SAV".equals(ext)) {
					cCase = "SPSS";
				}
				else if("xls".equals(ext) || "xlsx".equals(ext)) {
					cCase = "EXCEL";
				}
				
				if("".equals(tCase)) tCase = cCase;
				else if(!tCase.equals(cCase)) {
					JOptionPane.showMessageDialog(null, "������ �ٸ� ������ ���ԵǾ��ֽ��ϴ�\r\n"+path, "Message",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end for(
		return tCase;
	}
	
	// ������ư Ŭ��
	public void create() {
		String oututPath = oututPathTF.getText();
		if(null == oututPath || "".equals(oututPath)) {
			JOptionPane.showMessageDialog(null, "��°�θ� ������ �ּ���", "Message",
					JOptionPane.ERROR_MESSAGE);
		} else {
			System.out.println(oututPathTF.getText() + "�� ����");
		}
	} // end public void create(

} // end public class STEP01 extends JPanel

class Step02Table {
	
	@SuppressWarnings("serial")
	private DefaultTableModel dm = new DefaultTableModel() {
		public boolean isCellEditable(int r, int c) {
			if(c != 1 )return false; // edit ���� �Ұ� �б� ����
			else return true;
		}
	};
	
	private JTable table;
	
	private Object[] objColNms = new Object[] { "���ϰ��", "����"  };
	
	public Step02Table(Object[][] arg0) {
		dm.setDataVector(arg0, objColNms);
		table = new JTable(dm);
		table.getColumn("���ϰ��").setPreferredWidth(300);
	} // end public FileTable()
	
	public JTable getTable() { return table; }
	
} // end public class Step02Table extends JTable