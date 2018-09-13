package come;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class NorPanel extends JPanel {
	
	public NorPanel(String sTitle, String sSubTitle) {
		JLabel lTitle = new JLabel(sTitle);
		lTitle.setBorder(BorderFactory.createEmptyBorder(0 , 10 , 0 , 10)); //»óÁÂÇÏ¿ì
		add(lTitle);
		
		JLabel lSubTitle = new JLabel(sSubTitle);
		lSubTitle.setBorder(BorderFactory.createEmptyBorder(0 , 10 , 0 , 10));
		add(lSubTitle);
		
		setBorder(new TitledBorder(new LineBorder(Color.black),""));
		
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.addLayoutComponent(lTitle, BorderLayout.WEST);
		borderLayout.addLayoutComponent(lSubTitle, BorderLayout.CENTER);
		
		setLayout(borderLayout);
	} // public NorPanel(String sTitle, String sSubTitle)
	
} // end public class NorPanel extends JPanel
