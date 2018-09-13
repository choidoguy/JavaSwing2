package step;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import fram.CenPanel;
import fram.EasPanel;
import fram.NorPanel;
import fram.SouPanel;
import fram.WesPanel;
import main.Scheduler;

@SuppressWarnings("serial")
public class STEP01 extends JPanel {
    
    public STEP01(Scheduler scheduler, String sPrev, String sNext) {
    	BorderLayout borderLayout = new BorderLayout();
    	setLayout(borderLayout);
    	
    	NorPanel norPanel = new NorPanel("STEP01","다중파일선택");
    	add(norPanel);
    	borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
    	
//    	WesPanel wesPanel = new WesPanel();
//    	add(wesPanel);   
//    	borderLayout.addLayoutComponent(wesPanel, BorderLayout.WEST);
//    	
//    	EasPanel easPanel = new EasPanel();
//    	add(easPanel);   
//    	borderLayout.addLayoutComponent(easPanel, BorderLayout.EAST);
    	
    	SouPanel souPanel = new SouPanel(scheduler, sPrev, sNext);
    	add(souPanel);  
    	borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);
    	
    	CenPanel cenPanel = new CenPanel();
    	add(cenPanel); 
    	borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
		
    } // end public STEP01(Scheduler scheduler)
    
} // end public class STEP01 extends JPanel
