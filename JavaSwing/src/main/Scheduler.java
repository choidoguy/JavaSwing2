package main;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import step.STEP01;
import step.STEP02;
import step.STEP03;

@SuppressWarnings("serial")
public class Scheduler extends JFrame {
 
    @SuppressWarnings("rawtypes")
	public STEP01 step01 = null;
    public STEP02 step02 = null;
    public STEP03 step03 = null;
    
    @SuppressWarnings("rawtypes")
	public Scheduler() {
    	setTitle("frame change");
    	
    	step01 = new STEP01(this, "", "step02");
//    	// spss 파일의 확장자(.sav, .SAV) 형식인 경우 
//    	step02 = new STEP02(this, "step01", "");
//    	// EXCEL 파일의 확장자(.xls, .xlsx) 형식인 경우 
//    	step03 = new STEP03(this, "step01", "");
 
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
    				step03 = new STEP03(this, "step01", "", step01.getFileList());
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
    	case "step03": validationCheck = step03.validationMoveNextStep(); break;
    	default : 
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