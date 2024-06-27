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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginPage {

	private JFrame frame;
	private JPasswordField txtPassword;
	private int memberId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
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
	public LoginPage() {
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
		
		JLabel lblLoginPage = new JLabel("Login Page");
		lblLoginPage.setForeground(new Color(0, 0, 0));
		lblLoginPage.setFont(new Font("League Spartan", Font.PLAIN, 31));
		lblLoginPage.setBounds(402, 0, 204, 114);
		frame.getContentPane().add(lblLoginPage);
		
		TextField txtUsername = new TextField();
		txtUsername.setBounds(393, 120, 181, 27);
		frame.getContentPane().add(txtUsername);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txtPassword.getText().equals("")||txtUsername.getText().equals(""))
					JOptionPane.showMessageDialog(frame, "Username and password can't be empty!!!", "Error", JOptionPane.WARNING_MESSAGE);
				else			
					sendLoginRequest(txtUsername.getText(),txtPassword.getText());
			}
		});
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(0, 0, 128));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogin.setBounds(431, 266, 103, 27);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsername.setBounds(298, 120, 89, 27);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setBounds(298, 194, 89, 27);
		frame.getContentPane().add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(393, 195, 181, 27);
		frame.getContentPane().add(txtPassword);
		
		JLabel lblAreYouA = new JLabel("NEW USER?");
		lblAreYouA.setForeground(Color.BLACK);
		lblAreYouA.setFont(new Font("Dialog", Font.ITALIC, 25));
		lblAreYouA.setBounds(597, 313, 173, 74);
		frame.getContentPane().add(lblAreYouA);
		
		JButton btnRegisterNow = new JButton("REGISTER NOW");
		btnRegisterNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterPage window = new RegisterPage();
				window.setVisible(true);
				frame.dispose();
			}
		});
		btnRegisterNow.setForeground(Color.WHITE);
		btnRegisterNow.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRegisterNow.setBackground(new Color(0, 0, 128));
		btnRegisterNow.setBounds(597, 383, 152, 47);
		frame.getContentPane().add(btnRegisterNow);
	}
	
	private void sendLoginRequest(String username, String password) {
	    try {
	        String encodedUsername = URLEncoder.encode(username, "UTF-8");
	        String encodedPassword = URLEncoder.encode(password, "UTF-8");
	        String urlString = "http://localhost/projectDAD/LibraryServer.php"
	                         + "?action=login"
	                         + "&username=" + encodedUsername
	                         + "&password=" + encodedPassword;

	        URL url = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");

	        // Read response if needed
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuilder response = new StringBuilder();
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();

	        System.out.println("Response from server: " + response.toString());
	        String jsonResponse = response.toString();
	        JSONObject jsonObject = new JSONObject(jsonResponse);

	        if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
	            // Retrieve and store memberId
	            memberId = jsonObject.getInt("memberId");
	            JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
	            HomeMember window = new HomeMember(memberId);
	            window.setVisible(true);
	            frame.setVisible(false);
	        } else {
	            String errorMessage = jsonObject.has("message") ? jsonObject.getString("message") : "Error Credentials! Please try again.";
	            JOptionPane.showMessageDialog(frame, errorMessage, "Error", JOptionPane.WARNING_MESSAGE);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frame, "Error borrowing books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
        frame.setVisible(b);
	}
}
