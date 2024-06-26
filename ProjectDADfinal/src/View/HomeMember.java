package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeMember {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	}

	/**
	 * Create the application.
	 * @param memberId 
	 * @wbp.parser.entryPoint
	 */
	public HomeMember(int memberId) {
		initialize(memberId);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int memberId) {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(192, 192, 192));
		frame.setBounds(100, 100, 794, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 128));
		panel.setBounds(0, 0, 204, 467);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("UTeM");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("League Spartan", Font.PLAIN, 31));
		lblNewLabel.setBounds(50, 125, 117, 108);
		panel.add(lblNewLabel);
		
		JLabel lblLibrary = new JLabel("Library");
		lblLibrary.setForeground(Color.WHITE);
		lblLibrary.setFont(new Font("League Spartan", Font.PLAIN, 31));
		lblLibrary.setBounds(50, 214, 117, 48);
		panel.add(lblLibrary);
		
		JLabel lblLoginPage = new JLabel("Welcome To UTeM Library");
		lblLoginPage.setForeground(new Color(0, 0, 0));
		lblLoginPage.setFont(new Font("League Spartan", Font.PLAIN, 31));
		lblLoginPage.setBounds(284, 44, 391, 68);
		frame.getContentPane().add(lblLoginPage);
		
		JButton btnReturn = new JButton("RETURN A BOOK");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnBook frame1 = new ReturnBook(memberId);
				frame1.setVisible(true);
				frame.dispose();
			}
		});
		btnReturn.setFont(new Font("Dialog", Font.BOLD, 20));
		btnReturn.setBounds(294, 259, 360, 68);
		frame.getContentPane().add(btnReturn);
		
		JButton btnHistory = new JButton("HISTORY OF BORROWED BOOK");
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HistoryMember window = new HistoryMember(memberId);
				window.setVisible(true);
				frame.dispose();
			}
		});
		btnHistory.setFont(new Font("Dialog", Font.BOLD, 20));
		btnHistory.setBounds(294, 348, 360, 68);
		frame.getContentPane().add(btnHistory);
		
		JButton btnBorrow = new JButton("BORROW A BOOK");
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibraryMember borrowframe = new LibraryMember(memberId);
				borrowframe.setVisible(true);
				frame.dispose();
			}
		});
		btnBorrow.setFont(new Font("Dialog", Font.BOLD, 20));
		btnBorrow.setBounds(294, 168, 360, 68);
		frame.getContentPane().add(btnBorrow);
		
		JLabel lblChooseAnyOperation = new JLabel("Choose any operation");
		lblChooseAnyOperation.setForeground(Color.BLACK);
		lblChooseAnyOperation.setFont(new Font("Dialog", Font.PLAIN, 17));
		lblChooseAnyOperation.setBounds(393, 103, 178, 53);
		frame.getContentPane().add(lblChooseAnyOperation);
		
		JButton btnBack = new JButton("LOG OUT");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage window = new LoginPage();
				window.setVisible(true);
				frame.dispose();
			}
		});
		btnBack.setFont(new Font("Dialog", Font.BOLD, 15));
		btnBack.setBounds(652, 10, 118, 35);
		frame.getContentPane().add(btnBack);
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
        frame.setVisible(b);

	}
}
