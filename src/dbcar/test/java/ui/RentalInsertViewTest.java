package dbcar.test.java.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import dbcar.main.java.com.dbshindong.dbcar.ui.view.RentalInsertView;

public class RentalInsertViewTest {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Rental 입력");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(600, 500);
			frame.add(new RentalInsertView());
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
