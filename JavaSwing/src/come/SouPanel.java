package come;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.Scheduler;

@SuppressWarnings("serial")
public class SouPanel extends JPanel {
	public SouPanel(Scheduler scheduler, String sPrev, String sCurr, String sNext) {
		BorderLayout borderLayout = new BorderLayout();
		
		JLabel label = new JLabel("SouPanel");
		this.add(label);
		setBorder(new TitledBorder(new LineBorder(Color.black),""));
		borderLayout.addLayoutComponent(label, BorderLayout.CENTER);
		
		JButton btnPrev = new JButton("PREV");
		if(sPrev != null && !"".equals(sPrev)) {
			btnPrev.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					scheduler.callScheduler(sCurr, sPrev);
				}
			});
		} else {
			btnPrev.setEnabled(false);
			//btnPrev.setVisible(false);
		}
		this.add(btnPrev);
		borderLayout.addLayoutComponent(btnPrev, BorderLayout.WEST);
		
		JButton btnNext = new JButton("NEXT");
		if(sNext != null && !"".equals(sNext)) {
			btnNext.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					scheduler.callScheduler(sCurr, sNext);
				}
			});
		} else {
			btnNext.setEnabled(false);
			//btnNext.setVisible(false);
		}
		this.add(btnNext);
		borderLayout.addLayoutComponent(btnNext, BorderLayout.EAST);
		
		setLayout(borderLayout);
	}
}
