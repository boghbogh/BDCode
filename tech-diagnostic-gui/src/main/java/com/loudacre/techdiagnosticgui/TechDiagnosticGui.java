package com.loudacre.techdiagnosticgui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class TechDiagnosticGui {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainPanel panel = new MainPanel();

				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				frame.add(panel, BorderLayout.CENTER);
				frame.pack();

				frame.setVisible(true);
			}
		});
	}

}
