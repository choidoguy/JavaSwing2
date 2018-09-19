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

import come.NorPanel;
import come.SouPanel;
import file.FileDrop;
import file.Step02Table;
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
		this.table = (new Step02Table(this, objlist)).getTable();
		centerPanelInit();
		this.add(cenPanel);

		BorderLayout borderLayout = new BorderLayout();
		borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
		borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);
		borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
		setLayout(borderLayout);

	} // end public STEP01(

	// fileList[] 를 JTable 에 인자로 넣을 Object[][] 형태로 반환한다
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

	// centerPanel을 reload 한다
	private void centerPanelReload(JTable table) {
		this.table = table;

		cenPanel.removeAll();

		centerPanelInit();

		cenPanel.revalidate();
		cenPanel.repaint();
	} // end public void repaint(JTable table)

	// centerPanel을 초기화 한다
	private void centerPanelInit() {
		JScrollPane scroll = new JScrollPane(table);
		cenPanel.setBorder(new TitledBorder(new LineBorder(Color.black), ""));
		cenPanel.add(scroll);
		
		
			JPanel outPathPanel = new JPanel();
			JLabel label = new JLabel("출력경로 : ");
			outPathPanel.add(label);
			oututPathTF = new JTextField();
			oututPathTF.setEnabled(false);
			outPathPanel.add(oututPathTF);
			JButton btn = new JButton("선택");
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

//		new FileDrop(System.out, scroll, /* dragBorder, */ new FileDrop.Listener() {
//			public void filesDropped(java.io.File[] files) {
//				setFileList(files);
//			} // end filesDropped
//		}); // end FileDrop.Listener

	} // end private void initSTEP01(JScrollPane scroll)

//	// fileList 추가
//	public void setFileList(File[] addFiles) {
//		if (fileList == null || fileList.length == 0) {
//			fileList = addFiles;
//			Object[][] obj = fileList2objs();
//			centerPanelReload((new Step02Table(this, obj)).getTable());
//		} else {
//			boolean findFile = false;
//			// 파일 중복 체크
//			for (int i = 0; i < addFiles.length; i++) {
//				try {
//					Object newObj = addFiles[i].getCanonicalPath();
//					for (int j = 0; j < fileList.length; j++) {
//						Object oldObj = fileList[j].getCanonicalPath();
//						if (newObj.equals(oldObj)) {
//							JOptionPane.showMessageDialog(null, newObj.toString() + "은\r\n이미 선택한 파일입니다", "Message",
//									JOptionPane.ERROR_MESSAGE);
//							findFile = true;
//							return;
//						}
//					}
//				} catch (java.io.IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			if (findFile)
//				return;
//
//			// file List 갱신
//			File[] cloneFiles = new File[fileList.length + addFiles.length];
//			for (int i = 0; i < fileList.length; i++) {
//				cloneFiles[i] = fileList[i];
//			}
//			for (int i = fileList.length; i < (fileList.length + addFiles.length); i++) {
//				cloneFiles[i] = addFiles[i - fileList.length];
//			}
//
//			fileList = cloneFiles;
//
//			// table 갱신
//			Object[][] obj = fileList2objs();
//			centerPanelReload((new Step02Table(this, obj)).getTable());
//		}
//	} // end public void setFileList(File[] fileList)

//	// FileTable.java 에서 삭제버튼을 누르게 되면
//	// 삭제 할 file의 전체 경로를 반환 받아 삭제 처리 후 화면을 갱신한다
//	public void removeFilePath(String selectFilePath) {
//		File[] newfiles = new File[fileList.length - 1];
//		int cnt = 0;
//		for (int i = 0; i < fileList.length; i++) {
//			try {
//				Object oldObj = fileList[i].getCanonicalPath();
//				if (selectFilePath.equals(oldObj)) {
//					System.out.println("removeFilePath : remove file path " + selectFilePath + " find");
//				} else {
//					newfiles[cnt++] = fileList[i];
//				}
//			} catch (java.io.IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		fileList = newfiles;
//
//		Object[][] obj = fileList2objs();
//
//		this.table = (new Step02Table(this, obj)).getTable();
//		centerPanelReload(this.table);
//	} // end public void removeFilePath(String selectFilePath)
	
//	// 다음 step 으로 넘어가기 전 validation
//	public Boolean validationMoveNextStep() {
//		if (fileList == null || fileList.length == 0) {
//			JOptionPane.showMessageDialog(null, "파일이 지정되지 않았습니다", "Message",
//					JOptionPane.ERROR_MESSAGE);
//			return false;
//		}
//		
//		String tCase = "";
//		for(int i=0 ; i<fileList.length ; i++) {
//			String path = "";
//			String ext = "";
//			String cCase = "";
//			try {
//				path = fileList[i].getCanonicalPath();
//				ext = path.substring(path.lastIndexOf(".") + 1).toUpperCase();
//				if("SAV".equals(ext)) {
//					cCase = "SPSS";
//				}
//				else if("XLS".equals(ext) || "XLSX".equals(ext)) {
//					cCase = "EXCEL";
//				}
//				else {
//					JOptionPane.showMessageDialog(null, "지원하지 않는 형식입니다\r\n"+path, "Message",
//							JOptionPane.ERROR_MESSAGE);
//					return false;
//				}
//				
//				if("".equals(tCase)) tCase = cCase;
//				else if(!tCase.equals(cCase)) {
//					JOptionPane.showMessageDialog(null, "형식이 다른 파일이 포함되어있습니다\r\n"+path, "Message",
//							JOptionPane.ERROR_MESSAGE);
//					return false;
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} // end for(
//		
//		return true;
//	}
	
	// fileList 를 반환한다
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
					JOptionPane.showMessageDialog(null, "형식이 다른 파일이 포함되어있습니다\r\n"+path, "Message",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end for(
		return tCase;
	}
	
	// 생성버튼 클릭
	public void create() {
		String oututPath = oututPathTF.getText();
		if(null == oututPath || "".equals(oututPath)) {
			JOptionPane.showMessageDialog(null, "출력경로를 지정해 주세요", "Message",
					JOptionPane.ERROR_MESSAGE);
		} else {
			System.out.println(oututPathTF.getText() + "로 생성");
		}
	} // end public void create(

} // end public class STEP01 extends JPanel
