package fram;

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
	public SouPanel(Scheduler scheduler, String sPrev, String sNext) {
		BorderLayout borderLayout = new BorderLayout();
		
		JLabel label = new JLabel("SouPanel");
		this.add(label);
		setBorder(new TitledBorder(new LineBorder(Color.black),""));
		borderLayout.addLayoutComponent(label, BorderLayout.CENTER);
		
		if(sPrev != null && !"".equals(sPrev)) {
			JButton btnPrev = new JButton("PREV");
			btnPrev.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					scheduler.change(sPrev);
				}
			});
			this.add(btnPrev);
			borderLayout.addLayoutComponent(btnPrev, BorderLayout.WEST);
		}
		
		if(sNext != null && !"".equals(sNext)) {
			JButton btnNext = new JButton("NEXT");
			btnNext.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					scheduler.change(sNext);
				}
			});
			this.add(btnNext);
			borderLayout.addLayoutComponent(btnNext, BorderLayout.EAST);
		}
		
		setLayout(borderLayout);
	}
}
