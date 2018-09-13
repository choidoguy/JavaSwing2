package step;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import come.NorPanel;
import come.SouPanel;
import file.FileDrop;
import main.Scheduler;

@SuppressWarnings("serial")
public class STEP01 extends JPanel {
    
    public STEP01(Scheduler scheduler, String sPrev, String sNext) {
    	BorderLayout borderLayout = new BorderLayout();
    	
    	NorPanel norPanel = new NorPanel("STEP01","다중파일선택");
    	add(norPanel);
    	borderLayout.addLayoutComponent(norPanel, BorderLayout.NORTH);
    	
//    	WesPanel wesPanel = new WesPanel();
//    	add(wesPanel);   
//    	borderLayout.addLayoutComponent(wesPanel, BorderLayout.WEST);
//    	
//    	EasPanel easPanel = new EasPanel();
//    	add(easPanel);   
//    	borderLayout.addLayoutComponent(easPanel, BorderLayout.EAST);
    	
    	SouPanel souPanel = new SouPanel(scheduler, sPrev, sNext);
    	add(souPanel);  
    	borderLayout.addLayoutComponent(souPanel, BorderLayout.SOUTH);

    	JPanel cenPanel = new JPanel();
    	cenPanel.setBorder(new TitledBorder(new LineBorder(Color.black),""));
    	
    	
		JList list = new JList();
		cenPanel.add(list);
		
		new FileDrop(System.out, list, /* dragBorder, */ new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				Vector vec = new Vector();
				for (int i = 0; i < files.length; i++) {
					try {
						vec.addElement(files[i].getCanonicalPath() + "\n");
					} // end try
					catch (java.io.IOException e) {
					}
				} // end for: through each dropped file
				list.setListData(vec);
			} // end filesDropped
		}); // end FileDrop.Listener
		
		BorderLayout layout = new BorderLayout();
		layout.addLayoutComponent(list, BorderLayout.CENTER);
		
		cenPanel.setLayout(layout);
		
    	
    	add(cenPanel); 
    	borderLayout.addLayoutComponent(cenPanel, BorderLayout.CENTER);
    	setLayout(borderLayout);
		
    } // end public STEP01(Scheduler scheduler)
    
} // end public class STEP01 extends JPanel
