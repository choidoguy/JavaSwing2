package step;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.Scheduler;

@SuppressWarnings("serial")
public class JPanel01 extends JPanel { // 1번째 패널
 
    private JButton jButton1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    private Scheduler win;
 
    public JPanel01(Scheduler scheduler) {
        this.win = scheduler;
        setLayout(null);
 
        jButton1 = new JButton("버튼");
        jButton1.setSize(70, 20);
        jButton1.setLocation(10, 10);
        add(jButton1);
 
        jTextArea1 = new JTextArea();
 
        jScrollPane1 = new JScrollPane(jTextArea1);
        jScrollPane1.setSize(200, 150);
        jScrollPane1.setLocation(10, 40);
        add(jScrollPane1);
 
        jButton1.addActionListener(new ActionListener() {
        	@Override
    		public void actionPerformed(ActionEvent e) {
        		 win.change("panel02");
    		}
        });
    } // end public JPanel01(Scheduler scheduler)
}
