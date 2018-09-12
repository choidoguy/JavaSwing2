package main;
import javax.swing.JFrame;

import step.JPanel01;
import step.JPanel02;

@SuppressWarnings("serial")
public class Scheduler extends JFrame {
 
    public JPanel01 jpanel01 = null;
    public JPanel02 jpanel02 = null;
    
    public Scheduler() {
    	setTitle("frame test");
        jpanel01 = new JPanel01(this);
        jpanel02 = new JPanel02(this);
 
        add(jpanel01);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
    }
 
    public void change(String panelName) { // 패널 1번과 2번 변경 후 재설정
    	
    	getContentPane().removeAll();
    	
    	switch(panelName) {
    	case "panel01":
    		getContentPane().add(jpanel01);
    	break;
    	case "panel02":
    		getContentPane().add(jpanel02);
    	break;
    	default : break;
    	}
    	
        revalidate();
        repaint();
    }
    
    public static void main(String[] args) {
    	new Scheduler();
    }
 
}