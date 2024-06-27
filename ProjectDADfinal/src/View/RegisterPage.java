package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONObject;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.TextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class RegisterPage {

	private JFrame frame;
	private JPasswordField txtPassword;
	private JPasswordField txtReconfirm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPage window = new RegisterPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterPage() {
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
		
		JLabel lblRegisterPage = new JLabel("Registration Request Page");
		lblRegisterPage.setForeground(new Color(0, 0, 0));
		lblRegisterPage.setFont(new Font("League Spartan", Font.PLAIN, 31));
		lblRegisterPage.setBounds(288, 10, 482, 146);
		frame.getContentPane().add(lblRegisterPage);
		
		TextField txtUsername = new TextField();
		txtUsername.setBounds(399, 184, 137, 27);
		frame.getContentPane().add(txtUsername);
		
		JButton btnRequest = new JButton("Request");
		btnRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = new String(txtPassword.getPassword());
				String confirmPassword = new String(txtReconfirm.getPassword());
				String memberStatus = "PENDING";

				// Check if password and confirm password match
				if (!password.equals(confirmPassword)) {
					JOptionPane.showMessageDialog(frame, "Passwords do not match!");
					return;
				}

				// Call the registerMember function
				registerMember(username, password, memberStatus);
			}
		});
		btnRequest.setForeground(new Color(0, 0, 0));
		btnRequest.setBackground(new Color(255, 255, 255));
		btnRequest.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRequest.setBounds(418, 345, 103, 27);
		frame.getContentPane().add(btnRequest);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsername.setBounds(308, 184, 89, 27);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setBounds(308, 245, 89, 27);
		frame.getContentPane().add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(399, 246, 137, 27);
		frame.getContentPane().add(txtPassword);
		
		txtReconfirm = new JPasswordField();
		txtReconfirm.setBounds(399, 295, 137, 27);
		frame.getContentPane().add(txtReconfirm);
		
		JLabel lblReconfirm = new JLabel("Reconfirm\r\n Password");
		lblReconfirm.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblReconfirm.setBounds(244, 277, 175, 60);
		frame.getContentPane().add(lblReconfirm);
	}
	
	// Method to register member
	private void registerMember(String username, String password, String memberStatus) {
	    try {
	        String url = "http://localhost/projectDAD/LibraryServer.php?action=register";

	        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);

	        String postData = "username=" + URLEncoder.encode(username, "UTF-8") +
	                          "&password=" + URLEncoder.encode(password, "UTF-8") +
	                          "&memberStatus=" + URLEncoder.encode(memberStatus, "UTF-8");

	        try (OutputStream os = conn.getOutputStream()) {
	            byte[] input = postData.getBytes("UTF-8");
	            os.write(input, 0, input.length);
	        }

	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();

	        JSONObject jsonResponse = new JSONObject(response.toString());
	        if (jsonResponse.getString("status").equals("success")) {
	            JOptionPane.showMessageDialog(frame, "Request for registration has been sent!");
	            LoginPage window = new LoginPage();
	            window.setVisible(true);
	            frame.setVisible(false);
	        } else {
	            JOptionPane.showMessageDialog(frame, "Error: " + jsonResponse.getString("message"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
	    }
	}

	
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
        frame.setVisible(b);
	}
}
