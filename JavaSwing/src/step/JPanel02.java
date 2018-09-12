package step;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.Scheduler;

@SuppressWarnings("serial")
public class JPanel02 extends JPanel { // 2번째 패널
    private JTextField textField;
    private JPasswordField passwordField;
    private Scheduler win;
 
    public JPanel02(Scheduler win) {
        setLayout(null);
        this.win = win;
        JLabel lblLbl = new JLabel("아이디:");
        lblLbl.setBounds(31, 40, 67, 15);
        add(lblLbl);
 
        textField = new JTextField();
        textField.setBounds(123, 40, 116, 21);
        add(textField);
        textField.setColumns(10);
 
        JLabel lblLbl_1 = new JLabel("암호:");
        lblLbl_1.setBounds(31, 84, 67, 15);
        add(lblLbl_1);
 
        passwordField = new JPasswordField();
        passwordField.setBounds(123, 84, 116, 21);
        add(passwordField);
 
        JButton btn = new JButton("버튼");
        btn.setSize(70, 20);
        btn.setLocation(10, 10);
        add(btn);
        btn.addActionListener(new MyActionListener());
    }
 
    class MyActionListener implements ActionListener { // 버튼 키 눌리면 패널 1번 호출
        @Override
        public void actionPerformed(ActionEvent e) {
            win.change("panel01");
        }
    }
}