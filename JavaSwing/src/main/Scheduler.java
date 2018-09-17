package main;
import javax.swing.JFrame;

import step.STEP01;
import step.STEP02;

@SuppressWarnings("serial")
public class Scheduler extends JFrame {
 
    public STEP01 step01 = null;
    public STEP02 step02 = null;
    
    public Scheduler() {
    	setTitle("frame change");
    	
    	step01 = new STEP01(this, "", "step02");
    	step02 = new STEP02(this, "step01", "");
 
        add(step01);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    } // end public Scheduler()
 
    public void callScheduler(String currPanelName, String nextPanelName) {
    	
    	boolean validationCheck = true;
    	switch(currPanelName) {
    	case "step01": validationCheck = step01.validationMoveNextStep(); break;
    	case "step02": validationCheck = step02.validationMoveNextStep(); break;
    	default : 
    		validationCheck = false;
    	break;
    	}
    	
    	if(validationCheck) {
    		change(nextPanelName);
    	}
    } // end public void change(
    
    public void change(String nextPanelName) {
    	
    	getContentPane().removeAll();
    	
    	switch(nextPanelName) {
    	case "step01": getContentPane().add(step01); break;
    	case "step02": getContentPane().add(step02); break;
    	default : 
    		getContentPane().add(step01);
    		break;
    	}
    	
        revalidate();
        repaint();
    } // end public void change(
    
    public static void main(String[] args) {
    	Scheduler win = new Scheduler();
        
    	win.setLocationRelativeTo(null);
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice [] gd = ge.getScreenDevices();
//        
//        if(gd.length > 0) {
//            int width  = gd[0].getDefaultConfiguration().getBounds().width;
//            int height = gd[0].getDefaultConfiguration().getBounds().height;
//            
//            win.setSize(width / 2, height / 2);
//            
//            win.setLocation(
//                ((width  / 2) - (win.getSize().width  / 2)) + gd[0].getDefaultConfiguration().getBounds().x,
//                ((height / 2) - (win.getSize().height / 2)) + gd[0].getDefaultConfiguration().getBounds().y);
//            win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            win.setVisible(true);
//        }
//        else {
//            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(win);
//        }
        
    } // end public static void main(String[] args)
 
}