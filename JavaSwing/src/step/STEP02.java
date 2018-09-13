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
public class STEP02 extends JPanel {
 
    public STEP02(Scheduler scheduler, String sPrev, String sNext) {
    	NorPanel norPanel = new NorPanel("STEP02","SPSS");
    	WesPanel wesPanel = new WesPanel();
    	CenPanel cenPanel = new CenPanel();
    	EasPanel easPanel = new EasPanel();
    	SouPanel souPanel = new SouPanel(scheduler, sPrev, sNext);
    	
		add(norPanel);  
		add(wesPanel);   
		add(cenPanel); 
		add(easPanel);   
		add(souPanel);  
		
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
		borderLayout.addLayoutComponent(wesPanel, BorderLayout.WEST);
		borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
		borderLayout.addLayoutComponent(easPanel, BorderLayout.EAST);
		borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);
		
		setLayout(borderLayout);
    } // end public STEP02(Scheduler win)
    
} //end public class STEP02 extends JPanel