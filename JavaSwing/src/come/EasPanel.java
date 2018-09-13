package come;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class EasPanel extends JPanel {
	public EasPanel() {
		JLabel label = new JLabel("EasPanel");
		this.add(label);
		setBorder(new TitledBorder(new LineBorder(Color.black),""));
	}
}
