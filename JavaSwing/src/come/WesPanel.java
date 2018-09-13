package come;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class WesPanel extends JPanel {
	public WesPanel() {
		JLabel label = new JLabel("WesPanel");
		this.add(label);
		setBorder(new TitledBorder(new LineBorder(Color.black),""));
	}
}
