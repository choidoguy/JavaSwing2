package fram;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SouPanel extends JPanel {
	public SouPanel() {
		JLabel label = new JLabel("SouPanel");
		this.add(label);
		setBorder(new TitledBorder(new LineBorder(Color.black),""));
		
		JButton btnPrev = new JButton("PREV");
		this.add(btnPrev);
		
		JButton btnNext = new JButton("NEXT");
		this.add(btnNext);
		
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.addLayoutComponent(btnPrev, BorderLayout.WEST);
		borderLayout.addLayoutComponent(label, BorderLayout.CENTER);
		borderLayout.addLayoutComponent(btnNext, BorderLayout.EAST);
		
		setLayout(borderLayout);
	}
}
