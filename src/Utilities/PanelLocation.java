package Utilities;

import java.awt.Dimension;

import javax.swing.JFrame;

public class PanelLocation {

	public static void main(String[] args) {

		JFrame window = new JFrame();
		
		// SETTINGS
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("Week Eight Lab");		
		window.setSize(new Dimension(500, 600));
		
		// ADDING CLASSES
		PanelLocationObj panel = new PanelLocationObj();
		window.add(panel);
		
		// SETTINGS
		//window.pack();// causes this window to be sized to fit the preferred size and layout of its subcompononents (=GamePanel)
	    
		window.setLocationRelativeTo(null);// makes it so window appears in the middle of the screen
		window.setVisible(true);// makes the window viable

		// CALLING METHODS
		panel.StartThread();

		
	}

}
