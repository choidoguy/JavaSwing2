package main;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import step.STEP01;
import stepE.STEP03;
import stepE.STEP04;
import stepS.STEP02;

@SuppressWarnings("serial")
public class Scheduler extends JFrame {
 
	public STEP01 step01 = null;
    public STEP02 step02 = null;
    public STEP03 step03 = null;
    public STEP04 step04 = null;
    
	public Scheduler() {
    	setTitle("자료변환도구 v2");
    	
    	step01 = new STEP01(this, "", "step02");
        add(step01);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    } // end public Scheduler()
    
    
    // 현 step 에서 다음 stpe 으로 가기 위한 맞춤 검사
    public void callScheduler(String currPanelNm, String nextPanelNm) {
    	
    	boolean validationCheck = true;
    	switch(currPanelNm) {
    	case "step01": 
    		if(step01.validationMoveNextStep()) {
    			String tCase = step01.getFileCase();
    			if("SPSS".equals(tCase)) {
    				getContentPane().removeAll();
    				step02 = new STEP02(this, "step01", "Create", step01.getFileList());
    				getContentPane().add(step02);
    				revalidate();
    		        repaint();
    			}
    			else {
    				getContentPane().removeAll();
    				step03 = new STEP03(this, "step01", "step04", step01.getFileList());
    				getContentPane().add(step03);
    				revalidate();
    		        repaint();
    			}
    		} // end if(step01.validationMoveNextStep()) {
    		return;
    	case "step02": 
    		//validationCheck = step02.validationMoveNextStep();
    		if("Create".equals(nextPanelNm)) {
    			step02.create();
    			return;
    		}
    		break;
    	case "step03": 
    		validationCheck = step03.validationMoveNextStep(); 
    		break;
    	case "step04": 
    		validationCheck = step04.validationMoveNextStep(); 
    		break;
    	default : 
    		System.out.println("callScheduler default case");
    		validationCheck = false;
    	break;
    	}
    	
    	if(validationCheck) {
    		change(nextPanelNm);
    	}
    } // end public void change(
    
    public void change(String nextPanelNm) {
    	
    	getContentPane().removeAll();
    	
    	switch(nextPanelNm) {
    	case "step01": getContentPane().add(step01); break;
    	case "step02": getContentPane().add(step02); break;
    	case "step03": getContentPane().add(step03); break;
    	case "step04": 
    		step04 = new STEP04(this, "step03", "", step03.getFileList(), step03.getFileSheetAreaInfo());
    		getContentPane().add(step04); 
    		break;
    	default : 
    		getContentPane().add(step01);
    		break;
    	}
    	
        revalidate();
        repaint();
    } // end public void change(
    
    public static void main(String[] args) {
    	Scheduler win = new Scheduler();
        
    	//win.setLocationRelativeTo(null);
    	
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice [] gd = ge.getScreenDevices();
        
        if(gd.length > 0) {
            int width  = gd[0].getDefaultConfiguration().getBounds().width;
            int height = gd[0].getDefaultConfiguration().getBounds().height;
            
            win.setSize(width / 2, height / 2);
            
            win.setLocation(
                ((width  / 2) - (win.getSize().width  / 2)) + gd[0].getDefaultConfiguration().getBounds().x,
                ((height / 2) - (win.getSize().height / 2)) + gd[0].getDefaultConfiguration().getBounds().y);
            win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            win.setVisible(true);
        }
        else {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(win);
        }
        
    } // end public static void main(String[] args)
 
}