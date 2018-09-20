package stepE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import main.Scheduler;

import come.NorPanel;
import come.SouPanel;

import excl.ExcelManagerService;

/**
 * MAIN > STEP01 > 
 *        SPSS 이면 > STEP02 
 *        EXCL 이면 > STEP03 > STEP04
 *        
 * STEP03
 * excel sheet 별 영역 명칭을 정하고 넘어간다
 * */
@SuppressWarnings("serial")
public class STEP03 extends JPanel {
	private NorPanel norPanel;
	private SouPanel souPanel;

	private JPanel cenPanel;
	private JTable table;

	private File[] fileList;
	private Object[][] excelFilesheetAreaData = null;
	
	FileSheetAreaInfo fileSheetAreaInfo[] = null;
	
	STEP03 step03;
	
	public Object[][] getExcelFilesheetAreaData() {
		return excelFilesheetAreaData;
	}

	public void setExcelFilesheetAreaData(Object[][] excelFilesheetAreaData) {
		this.excelFilesheetAreaData = excelFilesheetAreaData;
	}

	public STEP03(Scheduler scheduler, String sPrev, String sNext, File[] fileList) {
		this.fileList = fileList;
		// NORTH
		norPanel = new NorPanel("STEP03", "EXCEL");
		this.add(norPanel);

		// SOUTH
		souPanel = new SouPanel(scheduler, sPrev, "step03",  sNext);
		this.add(souPanel);

		// CENTER
		cenPanel = new JPanel();
		excelFilesheetAreaData = fileList2objs();
		this.table = (new Step03Table(excelFilesheetAreaData)).getTable();
		centerPanelInit();
		this.add(cenPanel);

		BorderLayout borderLayout = new BorderLayout();
		borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
		borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);
		borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
		setLayout(borderLayout);
		
		step03 = this;

	}

	// fileList[] 를 JTable 에 인자로 넣을 Object[][] 형태로 반환한다
	private Object[][] fileList2objs() {
		
//		FileSheetAreaInfo fileSheetAreaInfo[] = null;
		
		Object[][] obj = null;
		if (fileList != null && fileList.length != 0) {
			
			int sheetTCnt = 0;
			for (int i = 0; i < fileList.length; i++) {
				// 각 sheet에 sheet name list 를 받아온다
				List<String> sheetsNmList = ExcelManagerService.getSheetsNm(fileList[i]);
				sheetTCnt += sheetsNmList.size();
			}
			
			
			fileSheetAreaInfo = new FileSheetAreaInfo[sheetTCnt];
			
			obj = new Object[sheetTCnt][4];
			int sheetNum = 0;
			for (int i = 0 ; i < fileList.length; i++) {
				try {
					String fileCanonicalPath = fileList[i].getCanonicalPath();
					String fileName = fileList[i].getName();
					List<String> sheetsNmList = ExcelManagerService.getSheetsNm(fileList[i]);
					for (int j = 0; j < sheetsNmList.size() ; j++) {
						obj[sheetNum][0] = fileCanonicalPath;
						obj[sheetNum][1] = fileName;
						obj[sheetNum][2] = sheetsNmList.get(j);
						obj[sheetNum][3] = sheetsNmList.get(j);
						
						fileSheetAreaInfo[sheetNum] = new FileSheetAreaInfo(fileList[i], sheetsNmList.get(j), sheetsNmList.get(j));
						
						sheetNum++;
					}
				} // end try
				catch (java.io.IOException e) {
				}
				
				
				
			} // end for: through each dropped file
		} else {
			System.out.println("fileList2objs : fileList == null or fileList.length == 0 : return null");
		}
		return obj;
	}

	// centerPanel을 초기화 한다
	private void centerPanelInit() {
		JScrollPane scroll = new JScrollPane(table);
		cenPanel.setBorder(new TitledBorder(new LineBorder(Color.black), ""));
		cenPanel.add(scroll);
		
		BorderLayout layout = new BorderLayout();
		layout.addLayoutComponent(scroll, BorderLayout.CENTER);
//		layout.addLayoutComponent(outPathPanel, BorderLayout.SOUTH);
		cenPanel.setLayout(layout);

	} // end private void initSTEP01(JScrollPane scroll)
	
	// fileList 를 반환한다
	public File[] getFileList() {
		return fileList;
	}
	
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
	
	// step03 의 화면에서 정리된 File / Sheet 별 영역 정보를 반환한다.
	public FileSheetAreaInfo[] getFileSheetAreaInfo() {
		if(fileSheetAreaInfo == null || excelFilesheetAreaData == null
		|| fileSheetAreaInfo.length == 0 || excelFilesheetAreaData.length == 0) return null;
		
		for(int i=0 ; i<table.getRowCount() ; i++) {
			fileSheetAreaInfo[i].setAreaNm(table.getValueAt(i,3).toString());
		}
		
		return fileSheetAreaInfo;
	}
	
	// 다음 step 으로 넘어가기 전 validation
	public Boolean validationMoveNextStep() {
		return true;
	}
	
} // end public class STEP03 extends JPanel

class Step03Table {
	
	@SuppressWarnings("serial")
	private DefaultTableModel dm = new DefaultTableModel() {
		public boolean isCellEditable(int r, int c) {
			if(c != 3 ) return false; // edit 수정 불가 읽기 전용
			else return true;
		}
	};
	
	private JTable table;
	
	private Object[] objColNms = new Object[] { "파일경로", "파일명", "sheet", "영역" };
	
	public Step03Table(Object[][] arg0) {
		dm.setDataVector(arg0, objColNms);
		table = new JTable(dm);
		table.getColumn("파일경로").setPreferredWidth(500);
		table.getColumn("파일명").setPreferredWidth(300);
		
		table.getTableHeader().setReorderingAllowed(false); // 이동불가
//		table.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
	} // end public FileTable()
	
	public JTable getTable() { return table; }
	
} // end public class Step03Table extends JTable

class FileSheetAreaInfo {
	private File   file = null; // 파일정보
	private String sheetNm = ""; // sheet 명
	private String areaNm = ""; // 영역명
	
	public FileSheetAreaInfo() {
	}
	
	public FileSheetAreaInfo(File file, String sheetNm, String areaNm) {
		this.file = file;
		this.sheetNm = sheetNm;
		this.areaNm = areaNm;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getSheetNm() {
		return sheetNm;
	}

	public void setSheetNm(String sheetNm) {
		this.sheetNm = sheetNm;
	}

	public String getAreaNm() {
		return areaNm;
	}

	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}
	
	
}