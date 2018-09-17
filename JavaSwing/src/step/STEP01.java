package step;

import java.awt.BorderLayout;
import java.awt.Color;

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
	private Scheduler scheduler;
	private STEP01 step01;
	private String sPrev;
	private String sNext;
	
	JPanel cenPanel;
	
	JTable table;
	
    public STEP01(Scheduler scheduler, String sPrev, String sNext) {
    	this.scheduler = scheduler;
    	this.sPrev = sPrev;
    	this.sNext = sNext;
    	
    	BorderLayout borderLayout = new BorderLayout();
    	
    	NorPanel norPanel = new NorPanel("STEP01","다중파일선택");
    	add(norPanel);
    	borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
    	
    	SouPanel souPanel = new SouPanel(scheduler, sPrev, sNext);
    	add(souPanel);  
    	borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);
    	
    	cenPanel = new JPanel();
    	
    	table = (new FileTable()).getTable();
    	JScrollPane scroll = new JScrollPane(table);
    	initSTEP01(scroll);
    	
    	BorderLayout layout = new BorderLayout();
		layout.addLayoutComponent(scroll, BorderLayout.CENTER);
		cenPanel.setLayout(layout);
    	
    	add(cenPanel); 
    	borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
    	setLayout(borderLayout);
    	
    	this.step01 = this;
    } // end public STEP01(Scheduler scheduler)
    
    private void initSTEP01(JScrollPane scroll) {
    	
    	cenPanel.setBorder(new TitledBorder(new LineBorder(Color.black),""));
    	cenPanel.add(scroll);
		new FileDrop(System.out, scroll, /* dragBorder, */ new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				
				Object[][] obj = new Object[files.length][2];
				for (int i = 0; i < files.length; i++) {
					try {
						obj[i][0] = files[i].getCanonicalPath();
						obj[i][1] = "Del";
					} // end try
					catch (java.io.IOException e) {
					}
				} // end for: through each dropped file
				
				cenPanel.removeAll();
		    	cenPanel.add(new JScrollPane((new FileTable(step01, obj)).getTable()));
				
				cenPanel.revalidate();
				cenPanel.repaint();
				
			} // end filesDropped
		}); // end FileDrop.Listener
    }
    
    public void repaint(JTable table) {
    	cenPanel.removeAll();
    	
    	JScrollPane scroll = new JScrollPane(table);
    	
    	initSTEP01(scroll);
    	
    	cenPanel.revalidate();
    	cenPanel.repaint();
    }
    
} // end public class STEP01 extends JPanel
