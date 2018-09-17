package step;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import come.NorPanel;
import come.SouPanel;
import file.FileDrop;
import file.FileTable;
import main.Scheduler;

@SuppressWarnings("serial")
public class STEP01 extends JPanel {
	private NorPanel norPanel;
	private SouPanel souPanel;
	
	private JPanel cenPanel;
	private JTable table;
	
	private File[] fileList;
	
	public File[] getFileList() {
		return fileList;
	}

	public void setFileList(File[] addFiles) {
		if(fileList == null || fileList.length == 0) {
			fileList = addFiles;
			
			Object[][] obj = new Object[fileList.length][2];
			for (int i = 0; i < fileList.length; i++) {
				try {
					obj[i][0] = fileList[i].getCanonicalPath();
					obj[i][1] = "Del";
				} // end try
				catch (java.io.IOException e) {
				}
			} // end for: through each dropped file
			
			repaint((new FileTable(this, obj)).getTable());
		}
		else {
			boolean findFile = false;
			// 파일 중복 체크
			for(int i=0 ; i < addFiles.length ; i++) {
				try {
					Object newObj = addFiles[i].getCanonicalPath();
					for(int j=0 ; j < fileList.length ; j++) {
						Object oldObj = fileList[j].getCanonicalPath();
						if(newObj.equals(oldObj)) {
							String msg = newObj.toString() + "은\r\n이미 선택한 파일입니다";
							JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.ERROR_MESSAGE);
							findFile = true;
							return;
						}
					}
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			}
			
			if(findFile) return;
			
			// file List 갱신 
			File[] cloneFiles = new File[fileList.length + addFiles.length];
			for(int i=0 ; i < fileList.length ; i++) {
				cloneFiles[i] = fileList[i];
			}
			for(int i=fileList.length ; i < (fileList.length + addFiles.length) ; i++) {
				cloneFiles[i] = addFiles[i-fileList.length];
			}
			
			fileList = cloneFiles;
			
			// table 갱신
			Object[][] obj = new Object[fileList.length][2];
			for (int i = 0 ; i < fileList.length ; i++) {
				try {
					obj[i][0] = fileList[i].getCanonicalPath();
					obj[i][1] = "Del";
				} // end try
				catch (java.io.IOException e) {
				}
			} // end for: add new file
			
			repaint((new FileTable(this, obj)).getTable());
		}
		
	} // end public void setFileList(File[] fileList)
	
	public void removeFilePath(String selectFilePath) {
		File[] newfiles = new File[fileList.length -1];
		int cnt = 0;
		for(int i=0 ; i < fileList.length ; i++) {
			try {
				Object oldObj = fileList[i].getCanonicalPath();
				if(selectFilePath.equals(oldObj)) {
					System.out.println("remove file path find : " + selectFilePath);
				} else {
					newfiles[cnt++] = fileList[i];
				}
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
		
		this.fileList = newfiles;
		
		Object[][] obj = new Object[fileList.length][2];
		for (int i = 0 ; i < fileList.length ; i++) {
			try {
				obj[i][0] = fileList[i].getCanonicalPath();
				obj[i][1] = "Del";
			} // end try
			catch (java.io.IOException e) {
			}
		} // end for: add new file
		
		this.table = (new FileTable(this, obj)).getTable();
		repaint(this.table);
	}
	
	public Boolean validationMoveNextStep() {
		if(fileList == null || fileList.length == 0) {
			return false;
		}
		return true;
	}
	
    public STEP01(Scheduler scheduler, String sPrev, String sNext) {
    	
    	//NORTH
    	norPanel = new NorPanel("STEP01","다중파일선택");
    	this.add(norPanel);
    	
    	//SOUTH
    	souPanel = new SouPanel(scheduler, sPrev, sNext);
    	this.add(souPanel);  
    	
    	//CENTER
    	cenPanel = new JPanel();
    	this.table = (new FileTable(this, null)).getTable();
    	STEP01CenterPanelInit(this.table);
		this.add(cenPanel); 
    	
    	BorderLayout borderLayout = new BorderLayout();
    	borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
    	borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);
    	borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
    	setLayout(borderLayout);
    	
    } // end public STEP01(Scheduler scheduler)
    
    public void repaint(JTable table) {
    	this.table = table;
    	
    	cenPanel.removeAll();
    	
    	STEP01CenterPanelInit(this.table);
    	
    	cenPanel.revalidate();
    	cenPanel.repaint();
    } // end public void repaint(JTable table)
    
    private void STEP01CenterPanelInit(JTable table) {
    	JScrollPane scroll = new JScrollPane(table);
    	
    	cenPanel.setBorder(new TitledBorder(new LineBorder(Color.black),""));
    	cenPanel.add(scroll);
    	
    	BorderLayout layout = new BorderLayout();
		layout.addLayoutComponent(scroll, BorderLayout.CENTER);
		cenPanel.setLayout(layout);
    	
		new FileDrop(System.out, scroll, /* dragBorder, */ new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				setFileList(files);
			} // end filesDropped
		}); // end FileDrop.Listener
		
    } // end private void initSTEP01(JScrollPane scroll)
    
} // end public class STEP01 extends JPanel
