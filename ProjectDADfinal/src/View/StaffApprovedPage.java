package View;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.JScrollPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StaffApprovedPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JFrame frame;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffApprovedPage frame = new StaffApprovedPage();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public StaffApprovedPage() {
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 833, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel panel = new Panel();
		panel.setBackground(new Color(128, 128, 128));
		panel.setForeground(new Color(128, 128, 128));
		panel.setBounds(0, 0, 817, 65);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("UTeM Library");
		lblNewLabel.setFont(new Font("League Spartan", Font.PLAIN, 18));
		lblNewLabel.setBounds(118, 11, 203, 34);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(StaffApprovedPage.class.getResource("/Images/Logo-Universiti-Teknikal-Malaysia-Melaka-UTeM-Jawi-new-300x300 (1).png")));
		lblNewLabel_1.setBounds(20, 11, 88, 44);
		panel.add(lblNewLabel_1);
		
		Button buttonBack = new Button("BACK");
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaffHomePage window = new StaffHomePage();
				window.setVisible(true);
				dispose();
			}
		});
		buttonBack.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonBack.setBounds(625, 29, 100, 26);
		panel.add(buttonBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(77, 145, 647, 266);
		contentPane.add(scrollPane);
		
		table = new JTable();
        scrollPane.setViewportView(table);

        // Define table model with column names
        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Member ID", "Username", "Password"}
        );

        table.setModel(model);
        
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(149);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(0, 459, 817, 35);
		contentPane.add(panel_1);
		
		JLabel lblPendingMember = new JLabel("PENDING REQUEST FROM MEMBER");
		lblPendingMember.setFont(new Font("League Spartan", Font.PLAIN, 18));
		lblPendingMember.setBounds(227, 94, 358, 34);
		contentPane.add(lblPendingMember);
		
		//load the pending members
		loadPendingMember();

		Button button = new Button("Approve");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int[] selectedRows = table.getSelectedRows();

		        // Check if any row is selected
		        if (selectedRows.length == 0) {
		            JOptionPane.showMessageDialog(frame, "Please select at least one member to approve or reject.", "No Selection", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
		        
		        // Iterate over selected rows and prepare data to send to PHP script
		        for (int i : selectedRows) {
		            int memberID = (int) table.getValueAt(i, 0);
		            String username = (String) table.getValueAt(i, 1);
		            String password = (String) table.getValueAt(i, 2);
		          
		            // Example: Call method to send data to PHP script
		           giveApproval(memberID, username, password);
		        }
		    }
			
		});
		button.setFont(new Font("League Spartan", Font.BOLD, 14));
		button.setBounds(485, 417, 100, 26);
		contentPane.add(button);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 128));
		panel_2.setBounds(0, 62, 817, 21);
		contentPane.add(panel_2);
				
		Button buttonRefresh = new Button("Refresh");
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadPendingMember();
			}
		});
		buttonRefresh.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonRefresh.setBounds(624, 102, 100, 26);
		contentPane.add(buttonRefresh);
		
		Button buttonReject = new Button("Reject");
		buttonReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = table.getSelectedRows();

		        // Check if any row is selected
		        if (selectedRows.length == 0) {
		            JOptionPane.showMessageDialog(frame, "Please select at least one member to reject.", "No Selection", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        // Iterate over selected rows and prepare data to send to PHP script
		        for (int i : selectedRows) {
		            int memberID = (int) table.getValueAt(i, 0);
		            String username = (String) table.getValueAt(i, 1);
		            String password = (String) table.getValueAt(i, 2);
		            
		            // Example: Call method to send data to PHP script
		           rejectRequest(memberID, username, password);
		        }
			}
		});
		buttonReject.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonReject.setBounds(620, 417, 100, 26);
		contentPane.add(buttonReject);
		
		
	}
	
	private void loadPendingMember() {
	    String urlString = "http://localhost/projectDAD/LibraryServer.php?action=retrievePendingMember";
	    try {
	        URL url = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");

	        // Read response
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuilder response = new StringBuilder();
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        
            model.setRowCount(0); // Clear existing rows

	        // Debug: Print out the response received from server
	        System.out.println("Response from server: " + response.toString());

	        // Parse JSON response
	        if (response.toString().startsWith("[")) {
	            // JSONArray case: Multiple results
	            JSONArray jsonArray = new JSONArray(response.toString());
	            model.setRowCount(0); // Clear existing rows
	            for (int i = 0; i < jsonArray.length(); i++) {
	                JSONObject jsonObject = jsonArray.getJSONObject(i);
	                int memberID = jsonObject.getInt("memberID");
	                String username = jsonObject.getString("username");
	                String password = jsonObject.getString("password");

	                model.addRow(new Object[]{memberID, username, password});
	            }
	        } else if (response.toString().startsWith("{")) {
	            // JSONObject case: Single result or message
	            JSONObject jsonResponse = new JSONObject(response.toString());
	            if (jsonResponse.has("message")) {
	                JOptionPane.showMessageDialog(null, jsonResponse.getString("message"));
	            } else if (jsonResponse.has("error")) {
	                JOptionPane.showMessageDialog(null, "Error: " + jsonResponse.getString("error"));
	            } else if (jsonResponse.has("results")) {
	                JSONArray jsonArray = jsonResponse.getJSONArray("results");
	                model.setRowCount(0); // Clear existing rows
	                for (int i = 0; i < jsonArray.length(); i++) {
	                    JSONObject jsonObject = jsonArray.getJSONObject(i);
	                    int memberID = jsonObject.getInt("memberID");
	                    String username = jsonObject.getString("username");
	                    String password = jsonObject.getString("password");

	                    model.addRow(new Object[]{memberID, username, password});
	                }
	            }
	        } else {
	            // Unexpected response structure
	            System.out.println("Unexpected response structure: " + response.toString());
	            JOptionPane.showMessageDialog(null, "Unexpected response structure", "Error", JOptionPane.ERROR_MESSAGE);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error fetching pending members: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void giveApproval(int memberID, String username, String password) {
	    try {
	        String urlString = "http://localhost/projectDAD/LibraryServer.php?action=approvedMember";

	        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	        String postData = "memberID=" + URLEncoder.encode(String.valueOf(memberID), "UTF-8") +
	                          "&username=" + URLEncoder.encode(username, "UTF-8") +
	                          "&password=" + URLEncoder.encode(password, "UTF-8");

	        try (OutputStream os = conn.getOutputStream()) {
	            byte[] input = postData.getBytes("UTF-8");
	            os.write(input, 0, input.length);
	        }

	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuilder response = new StringBuilder();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();

	        System.out.println("Response from server: " + response.toString());

	        if (response.toString().contains("success")) {
	            JOptionPane.showMessageDialog(frame, "Member approved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(frame, "Error approving member. Please try again.", "Error", JOptionPane.WARNING_MESSAGE);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frame, "Error approving member: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void rejectRequest(int memberID, String username, String password) {
	    try {
	        String urlString = "http://localhost/projectDAD/LibraryServer.php?action=rejectMember";

	        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	        String postData = "memberID=" + URLEncoder.encode(String.valueOf(memberID), "UTF-8") +
	                          "&username=" + URLEncoder.encode(username, "UTF-8") +
	                          "&password=" + URLEncoder.encode(password, "UTF-8");

	        try (OutputStream os = conn.getOutputStream()) {
	            byte[] input = postData.getBytes("UTF-8");
	            os.write(input, 0, input.length);
	        }

	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuilder response = new StringBuilder();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();

	        System.out.println("Response from server: " + response.toString());

	        if (response.toString().contains("success")) {
	            JOptionPane.showMessageDialog(frame, "Member rejected successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(frame, "Error rejecting member. Please try again.", "Error", JOptionPane.WARNING_MESSAGE);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frame, "Error rejecting member: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
