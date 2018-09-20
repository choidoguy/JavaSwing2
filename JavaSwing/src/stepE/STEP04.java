package stepE;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.Scheduler;

import come.NorPanel;
import come.SouPanel;

/**
 * MAIN > STEP01 > SPSS 이면 > STEP02 EXCL 이면 > STEP03 > STEP04
 * 
 * STEP04 excel sheet별 데이터 위치를 지정받는다
 * */
@SuppressWarnings("serial")
public class STEP04 extends JPanel {
	private NorPanel norPanel;
	private SouPanel souPanel;

	private JPanel cenPanel;
	private JTable table;

	private File[] fileList;

	ExcelFileSheetColumnInitDataPosInfo efscidpi[] = null;

	STEP04 step04;

	public STEP04(Scheduler scheduler, String sPrev, String sNext, File[] fileList, FileSheetAreaInfo[] fileSheetAreaInfo) {
		this.fileList = fileList;

		// this.excelFilesheetAreaData = areaData;

		// NORTH
		norPanel = new NorPanel("STEP04", "EXCEL 파일 Sheet 별 데이터 위치지정");
		this.add(norPanel);

		// SOUTH
		souPanel = new SouPanel(scheduler, sPrev, "step04", sNext);
		this.add(souPanel);

		// CENTER
		cenPanel = new JPanel();
		// Object[][] objlist = fileList2objs();
		// this.table = (new
		// Step04Table(this.excelFilesheetAreaData)).getTable();
		excelFileSheetColumnInitDataSet(fileSheetAreaInfo);
		centerPanelInit();
		this.add(cenPanel);

		BorderLayout borderLayout = new BorderLayout();
		borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
		borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);
		borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
		setLayout(borderLayout);

		step04 = this;

	} // end public STEP01(

	// file list 각 sheet 별 영역 정보를 기반으로 초기데이터를 셋팅한다
	private void excelFileSheetColumnInitDataSet(FileSheetAreaInfo[] fileSheetAreaInfo) {
		efscidpi = new ExcelFileSheetColumnInitDataPosInfo[fileSheetAreaInfo.length];
		for(int i=0 ; i<fileSheetAreaInfo.length ; i++) {
			ExcelFileSheetColumnInitDataPosInfo info = new ExcelFileSheetColumnInitDataPosInfo(
					fileSheetAreaInfo[i].getFile(),
					fileSheetAreaInfo[i].getFile().getName(), 
					fileSheetAreaInfo[i].getSheetNm(),
					fileSheetAreaInfo[i].getAreaNm());
			
			efscidpi[i] = info;
		}
	}

	// centerPanel을 초기화 한다
	private void centerPanelInit() {

		String[] items = new String[fileList.length];
		for (int i = 0; i < fileList.length; i++) {
			items[i] = fileList[i].getName();
		}

		/*-------------------------------------------------------
		 * Add the list
		 * ------------------------------------------------------ */
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		// for (String item : items) listModel.addElement(item);
		for (int i = 0; i < fileList.length; i++)
			listModel.addElement(fileList[i].getName());

		final JList<String> list = new JList<String>(listModel);
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JList list = (JList) e.getSource();
				list.getCellBounds(0, list.getLastVisibleIndex());
				if (e.getClickCount() > 1) {
					Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex());
					if (r != null && r.contains(e.getPoint())) {
						System.out.println(e.getClickCount() + "Click");
						int index = list.locationToIndex(e.getPoint());
						System.out.println("locationToIndex : " + index);
						//list.getSelectedValue();
					}
				}
			}
		}); // end list.addMouseListener(
		
		list.setSelectedIndex(0);// 첫번째것 선택

		String sHtmlLabelTitle = "<html><center><br>파일리스트";
		JLabel lList = new JLabel(sHtmlLabelTitle);
		lList.setHorizontalAlignment(lList.CENTER);

		JPanel listPanel = new JPanel(new BorderLayout(5, 5));
		listPanel.add(lList, BorderLayout.PAGE_START);
		listPanel.add(new JScrollPane(list), BorderLayout.CENTER);

		/*----------------------------------------------------
		 * Add the tab
		 * ---------------------------------------------------- */
		JTabbedPane tab = new JTabbedPane();
		
		String selectFileNm = list.getSelectedValue();
		for(int i=0 ; i<efscidpi.length ; i++) {
			if(selectFileNm.equals(efscidpi[i].getFileNm())) {
				//tab.addTab(efscidpi[i].getSheetNm() + "\r\n" + efscidpi[i].getAreaNm() , new JScrollPane(new JTable(new DefaultTableModel(0, 1))));
				String sHtmlTabTitle = "<html><center><b>" + efscidpi[i].getSheetNm() + "</b><br><font color=#88929f>" + efscidpi[i].getAreaNm() + "</font>";
				
					Font font = new Font("Consolas", Font.BOLD, 13);
					
					JLabel jlHead = new JLabel(" HEAD : "); jlHead.setFont(font);
					
						JPanel panelHeadSub = new JPanel();
						panelHeadSub.setLayout(new BoxLayout(panelHeadSub, BoxLayout.X_AXIS));
						panelHeadSub.add(new JButton("시작"));
						panelHeadSub.add(new JTextField(""));
						panelHeadSub.add(new JButton("종료"));
						panelHeadSub.add(new JTextField(""));
					
					JPanel panelHead = new JPanel(new BorderLayout(5, 5));
					panelHead.add(jlHead, BorderLayout.WEST);
					panelHead.add(panelHeadSub, BorderLayout.CENTER);
					
					
					JPanel panelItem = new JPanel(new BorderLayout(5, 5));
					
						JPanel panelItemSub = new JPanel();
						panelItemSub.setLayout(new BoxLayout(panelItemSub, BoxLayout.X_AXIS));
						panelItemSub.add(new JButton("시작"));
						panelItemSub.add(new JTextField(""));
						panelItemSub.add(new JButton("종료"));
						panelItemSub.add(new JTextField(""));
					
					JLabel jlItem = new JLabel(" ITEM : "); jlItem.setFont(font);
					panelItem.add(jlItem, BorderLayout.WEST);
					panelItem.add(panelItemSub, BorderLayout.CENTER);
					
					JPanel panelData = new JPanel(new BorderLayout(5, 5));
					
						JPanel panelDataSub = new JPanel();
						panelDataSub.setLayout(new BoxLayout(panelDataSub, BoxLayout.X_AXIS));
						panelDataSub.add(new JButton("시작"));
						panelDataSub.add(new JTextField(""));
						panelDataSub.add(new JButton("종료"));
						panelDataSub.add(new JTextField(""));
					
					JLabel jlData = new JLabel(" DATA : "); jlData.setFont(font);
					panelData.add(jlData, BorderLayout.WEST);
					panelData.add(panelDataSub, BorderLayout.CENTER);
				
				//JPanel inTabPaneHead = new JPanel(new BorderLayout(5, 5));
				JPanel inTabPaneHead = new JPanel();
				inTabPaneHead.setLayout(new BoxLayout(inTabPaneHead, BoxLayout.Y_AXIS));
				inTabPaneHead.add(panelHead);
				inTabPaneHead.add(panelItem);
				inTabPaneHead.add(panelData);
				
				JPanel inTabPane = new JPanel(new BorderLayout(5, 5));
				inTabPane.add(new JScrollPane(new JTable(new DefaultTableModel(0, 1))), BorderLayout.CENTER);
				inTabPane.add(inTabPaneHead, BorderLayout.NORTH);
				//inTabPane.add(inTabPaneHead, BorderLayout.CENTER);
				
				tab.addTab(sHtmlTabTitle , inTabPane);
			}
		} // end for(
		
		/*----------------------------------------------------
		 * 마무리
		 * ---------------------------------------------------- */
		cenPanel.setLayout(new BorderLayout(5, 5));
		cenPanel.add(listPanel, BorderLayout.WEST);
		cenPanel.add(tab, BorderLayout.CENTER);

	} // end private void initSTEP01(JScrollPane scroll)

	// fileList 를 반환한다
	public String getFileCase() {
		String tCase = "";
		for (int i = 0; i < fileList.length; i++) {
			String path = "";
			String ext = "";
			String cCase = "";
			try {
				path = fileList[i].getCanonicalPath();
				ext = path.substring(path.lastIndexOf(".") + 1).toUpperCase();
				if ("sav".equals(ext) || "SAV".equals(ext)) {
					cCase = "SPSS";
				} else if ("xls".equals(ext) || "xlsx".equals(ext)) {
					cCase = "EXCEL";
				}

				if ("".equals(tCase))
					tCase = cCase;
				else if (!tCase.equals(cCase)) {
					JOptionPane.showMessageDialog(null, "형식이 다른 파일이 포함되어있습니다\r\n" + path, "Message", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end for(
		return tCase;
	}

	// 다음 step 으로 넘어가기 전 validation
	public Boolean validationMoveNextStep() {
		return true;
	}

} // end public class STEP04 extends JPanel

// class Step04Table {
//
// @SuppressWarnings("serial")
// private DefaultTableModel dm = new DefaultTableModel() {
// public boolean isCellEditable(int r, int c) {
// if(c != 2 ) return false; // edit 수정 불가 읽기 전용
// else return true;
// }
// };
//
// private JTable table;
//
// private Object[] objColNms = new Object[] { "파일경로", "sheet", "영역" };
//
// public Step04Table(Object[][] arg0) {
// dm.setDataVector(arg0, objColNms);
// table = new JTable(dm);
// table.getColumn("파일경로").setPreferredWidth(500);
//
// table.getTableHeader().setReorderingAllowed(false); // 이동불가
// // table.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
// } // end public FileTable()
//
// public JTable getTable() { return table; }
//
// }

class ExcelFileSheetColumnInitDataPosInfo {

	private File file; // 파일정보
	private String fileNm;
	private String sheetNm; // sheet 명
	private String areaNm; // 영역명

	private String headerS;
	private String headerE;

	private String itemS;
	private String itemE;

	private String dataS;
	private String dataE;

	public ExcelFileSheetColumnInitDataPosInfo() {
	} // end public FileTable()
	
	public ExcelFileSheetColumnInitDataPosInfo(File file, String fileNm, String sheetNm, String areaNm) {
		this.file = file;
		this.fileNm = fileNm;
		this.sheetNm = sheetNm; // sheet 명
		this.areaNm = areaNm; // 영역명

		this.headerS = "";
		this.headerE = "";

		this.itemS = "";
		this.itemE = "";

		this.dataS = "";
		this.dataE = "";
	} // end public FileTable()

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
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

	public String getHeaderS() {
		return headerS;
	}

	public void setHeaderS(String headerS) {
		this.headerS = headerS;
	}

	public String getHeaderE() {
		return headerE;
	}

	public void setHeaderE(String headerE) {
		this.headerE = headerE;
	}

	public String getItemS() {
		return itemS;
	}

	public void setItemS(String itemS) {
		this.itemS = itemS;
	}

	public String getItemE() {
		return itemE;
	}

	public void setItemE(String itemE) {
		this.itemE = itemE;
	}

	public String getDataS() {
		return dataS;
	}

	public void setDataS(String dataS) {
		this.dataS = dataS;
	}

	public String getDataE() {
		return dataE;
	}

	public void setDataE(String dataE) {
		this.dataE = dataE;
	}
	
	
}
