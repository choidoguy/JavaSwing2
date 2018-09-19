package step;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import come.NorPanel;
import come.SouPanel;
import file.FileDrop;
import file.Step02FileTable;
import main.Scheduler;

@SuppressWarnings("serial")
public class STEP02 extends JPanel {
	private NorPanel norPanel;
	private SouPanel souPanel;

	private JPanel cenPanel;
	private JTable table;

	private File[] fileList;
	
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
		Object[][] obj = fileList2objs();
		this.table = (new Step02FileTable(this, obj)).getTable();
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

	// centerPanel�� reload �Ѵ�
	private void centerPanelReload(JTable table) {
		this.table = table;

		cenPanel.removeAll();

		centerPanelInit();

		cenPanel.revalidate();
		cenPanel.repaint();
	} // end public void repaint(JTable table)

	// centerPanel�� �ʱ�ȭ �Ѵ�
	@SuppressWarnings("static-access")
	private void centerPanelInit() {
		JScrollPane scroll = new JScrollPane(table);
		cenPanel.setBorder(new TitledBorder(new LineBorder(Color.black), ""));
		cenPanel.add(scroll);
		
		JLabel lMessage = new JLabel("�۾��Ͻ� ������ �߰��� �ּ���!");
		lMessage.setHorizontalAlignment(lMessage.CENTER);
		cenPanel.add(lMessage);

		BorderLayout layout = new BorderLayout();
		layout.addLayoutComponent(scroll, BorderLayout.CENTER);
		layout.addLayoutComponent(lMessage, BorderLayout.SOUTH);
		cenPanel.setLayout(layout);

		new FileDrop(System.out, scroll, /* dragBorder, */ new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				setFileList(files);
			} // end filesDropped
		}); // end FileDrop.Listener

	} // end private void initSTEP01(JScrollPane scroll)

	// fileList �߰�
	public void setFileList(File[] addFiles) {
		if (fileList == null || fileList.length == 0) {
			fileList = addFiles;
			Object[][] obj = fileList2objs();
			centerPanelReload((new Step02FileTable(this, obj)).getTable());
		} else {
			boolean findFile = false;
			// ���� �ߺ� üũ
			for (int i = 0; i < addFiles.length; i++) {
				try {
					Object newObj = addFiles[i].getCanonicalPath();
					for (int j = 0; j < fileList.length; j++) {
						Object oldObj = fileList[j].getCanonicalPath();
						if (newObj.equals(oldObj)) {
							JOptionPane.showMessageDialog(null, newObj.toString() + "��\r\n�̹� ������ �����Դϴ�", "Message",
									JOptionPane.ERROR_MESSAGE);
							findFile = true;
							return;
						}
					}
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			}

			if (findFile)
				return;

			// file List ����
			File[] cloneFiles = new File[fileList.length + addFiles.length];
			for (int i = 0; i < fileList.length; i++) {
				cloneFiles[i] = fileList[i];
			}
			for (int i = fileList.length; i < (fileList.length + addFiles.length); i++) {
				cloneFiles[i] = addFiles[i - fileList.length];
			}

			fileList = cloneFiles;

			// table ����
			Object[][] obj = fileList2objs();
			centerPanelReload((new Step02FileTable(this, obj)).getTable());
		}
	} // end public void setFileList(File[] fileList)

	// FileTable.java ���� ������ư�� ������ �Ǹ�
	// ���� �� file�� ��ü ��θ� ��ȯ �޾� ���� ó�� �� ȭ���� �����Ѵ�
	public void removeFilePath(String selectFilePath) {
		File[] newfiles = new File[fileList.length - 1];
		int cnt = 0;
		for (int i = 0; i < fileList.length; i++) {
			try {
				Object oldObj = fileList[i].getCanonicalPath();
				if (selectFilePath.equals(oldObj)) {
					System.out.println("removeFilePath : remove file path " + selectFilePath + " find");
				} else {
					newfiles[cnt++] = fileList[i];
				}
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}

		fileList = newfiles;

		Object[][] obj = fileList2objs();

		this.table = (new Step02FileTable(this, obj)).getTable();
		centerPanelReload(this.table);
	} // end public void removeFilePath(String selectFilePath)
	
	// ���� step ���� �Ѿ�� �� validation
	public Boolean validationMoveNextStep() {
		if (fileList == null || fileList.length == 0) {
			JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�", "Message",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		String tCase = "";
		for(int i=0 ; i<fileList.length ; i++) {
			String path = "";
			String ext = "";
			String cCase = "";
			try {
				path = fileList[i].getCanonicalPath();
				ext = path.substring(path.lastIndexOf(".") + 1).toUpperCase();
				if("SAV".equals(ext)) {
					cCase = "SPSS";
				}
				else if("XLS".equals(ext) || "XLSX".equals(ext)) {
					cCase = "EXCEL";
				}
				else {
					JOptionPane.showMessageDialog(null, "�������� �ʴ� �����Դϴ�\r\n"+path, "Message",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
				if("".equals(tCase)) tCase = cCase;
				else if(!tCase.equals(cCase)) {
					JOptionPane.showMessageDialog(null, "������ �ٸ� ������ ���ԵǾ��ֽ��ϴ�\r\n"+path, "Message",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end for(
		
		return true;
	}
	
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

} // end public class STEP01 extends JPanel
