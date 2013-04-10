package sepm.ss13.e0828.start;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sepm.ss13.e0828.gui.MainWindow;
import sepm.ss13.e0828.service.TherapyService;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame mainWindow = new MainWindow(TherapyService.getInstance());
		mainWindow.setVisible(true);

	}

}
