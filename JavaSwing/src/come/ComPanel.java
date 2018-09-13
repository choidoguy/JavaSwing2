package come;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ComPanel extends JPanel {
	public ComPanel() {
		JLabel label = new JLabel("ComPanel");
		this.add(label);
		setBorder(new TitledBorder(new LineBorder(Color.black),""));
	}
}
