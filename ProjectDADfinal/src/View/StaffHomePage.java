package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StaffHomePage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffHomePage window = new StaffHomePage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param memberId 
	 */
	public StaffHomePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		
		JLabel lblStaffPage = new JLabel("Staff Library Page");
		lblStaffPage.setForeground(new Color(0, 0, 0));
		lblStaffPage.setFont(new Font("League Spartan", Font.PLAIN, 31));
		lblStaffPage.setBounds(348, 10, 272, 94);
		frame.getContentPane().add(lblStaffPage);
		
		JButton btnReturn = new JButton("VIEW RETURNED BOOK");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				History frame1 = new History();
				frame1.setVisible(true);
				frame.dispose();
			}
		});
		btnReturn.setFont(new Font("Dialog", Font.BOLD, 20));
		btnReturn.setBounds(269, 258, 410, 68);
		frame.getContentPane().add(btnReturn);
		
		JButton btnBorrow = new JButton("VIEW BORROWED BOOK");
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaffLibrary borrowframe = new StaffLibrary();
				borrowframe.setVisible(true);
				frame.dispose();
			}
		});
		btnBorrow.setFont(new Font("Dialog", Font.BOLD, 20));
		btnBorrow.setBounds(269, 168, 410, 68);
		frame.getContentPane().add(btnBorrow);
		
		JLabel lblChooseAnyOperation = new JLabel("Choose any operation");
		lblChooseAnyOperation.setForeground(Color.BLACK);
		lblChooseAnyOperation.setFont(new Font("Dialog", Font.PLAIN, 17));
		lblChooseAnyOperation.setBounds(393, 103, 178, 53);
		frame.getContentPane().add(lblChooseAnyOperation);
		
		JButton btnPendingRequestFrom = new JButton("PENDING REQUEST FROM MEMBER");
		btnPendingRequestFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaffApprovedPage staffapprove = new StaffApprovedPage();
				staffapprove.setVisible(true);
				frame.dispose();
			}
		});
		btnPendingRequestFrom.setFont(new Font("Dialog", Font.BOLD, 20));
		btnPendingRequestFrom.setBounds(269, 353, 410, 68);
		frame.getContentPane().add(btnPendingRequestFrom);
		
		JButton btnBack = new JButton("LOG OUT");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();;
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
