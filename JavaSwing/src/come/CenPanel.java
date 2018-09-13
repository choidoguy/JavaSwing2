package come;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class CenPanel extends JPanel {
	public CenPanel() {
		JLabel label = new JLabel("CenPanel");
		this.add(label);
		setBorder(new TitledBorder(new LineBorder(Color.black),""));
	}
}
