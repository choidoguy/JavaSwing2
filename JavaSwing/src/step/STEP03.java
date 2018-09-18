package step;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import come.CenPanel;
import come.NorPanel;
import come.SouPanel;
import main.Scheduler;

@SuppressWarnings("serial")
public class STEP03 extends JPanel {
 
    public STEP03(Scheduler scheduler, String sPrev, String sNext) {
    	BorderLayout borderLayout = new BorderLayout();
    	
    	NorPanel norPanel = new NorPanel("STEP03","EXCEL");
    	add(norPanel);  
    	borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
    	
//    	WesPanel wesPanel = new WesPanel();
//    	add(wesPanel);   
//    	borderLayout.addLayoutComponent(wesPanel, BorderLayout.WEST);
//    	
//    	EasPanel easPanel = new EasPanel();
//    	add(easPanel);   
//    	borderLayout.addLayoutComponent(easPanel, BorderLayout.EAST);
    	
    	SouPanel souPanel = new SouPanel(scheduler, sPrev, "step03",  sNext);
    	add(souPanel);  
    	borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);
    	
    	CenPanel cenPanel = new CenPanel();
    	add(cenPanel); 
    	borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
		
		setLayout(borderLayout);
    } // end public STEP02(
    
 // 	다음 step 으로 넘어가기 전 validation
 	public Boolean validationMoveNextStep() {
 		return true;
 	}
    
} //end public class STEP03 extends JPanel