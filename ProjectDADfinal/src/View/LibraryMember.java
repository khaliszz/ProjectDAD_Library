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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class LibraryMember extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JFrame frame;
	private JTextField txtBookName;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
	}

	/**
	 * Create the frame.
	 */
	public LibraryMember(int memberId) {
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
		lblNewLabel_1.setIcon(new ImageIcon(LibraryMember.class.getResource("/Images/Logo-Universiti-Teknikal-Malaysia-Melaka-UTeM-Jawi-new-300x300 (1).png")));
		lblNewLabel_1.setBounds(20, 11, 88, 44);
		panel.add(lblNewLabel_1);
		
		Button buttonBack = new Button("BACK");
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeMember window = new HomeMember(memberId);
				window.setVisible(true);
				dispose();
			}
		});
		buttonBack.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonBack.setBounds(707, 19, 100, 26);
		panel.add(buttonBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(77, 145, 647, 266);
		contentPane.add(scrollPane);
		
		table = new JTable();
        scrollPane.setViewportView(table);

        // Define table model with column names
        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Title", "ISBN", "Date Published", "Category"}
        );

        table.setModel(model);
        
		table.getColumnModel().getColumn(0).setPreferredWidth(235);
		table.getColumnModel().getColumn(1).setPreferredWidth(149);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(3).setPreferredWidth(133);
		
		//display the books available
		displayBooks();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(0, 459, 817, 35);
		contentPane.add(panel_1);
		
		JLabel lblSelectYourBook = new JLabel("SELECT YOUR BOOK TO RENT");
		lblSelectYourBook.setFont(new Font("League Spartan", Font.PLAIN, 18));
		lblSelectYourBook.setBounds(285, 94, 300, 34);
		contentPane.add(lblSelectYourBook);
		
		Button button = new Button("Proceed");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int[] selectedRows = table.getSelectedRows();

		        // Check if any row is selected
		        if (selectedRows.length == 0) {
		            JOptionPane.showMessageDialog(frame, "Please select at least one book to borrow.", "No Selection", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
		        
		        // Get current date as string in yyyy-MM-dd format
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        String currentDate = sdf.format(Calendar.getInstance().getTime());
		        String status = "ONGOING";

		        // Iterate over selected rows and prepare data to send to PHP script
		        for (int i : selectedRows) {
		            String title = (String) table.getValueAt(i, 0);
		            String isbn = (String) table.getValueAt(i, 1);

		            // Example: Print selected book details (replace with actual logic to send to PHP script)
		            System.out.println("Selected Book - Title: " + title + ", ISBN: " + isbn + ", Date Borrowed: " + currentDate);

		            // Example: Call method to send data to PHP script
		           sendBorrowRequest(title, isbn, currentDate, status, memberId);
		        }
		        
		        
		    }
			
		});
		button.setFont(new Font("League Spartan", Font.BOLD, 14));
		button.setBounds(624, 417, 100, 26);
		contentPane.add(button);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 128));
		panel_2.setBounds(0, 62, 817, 21);
		contentPane.add(panel_2);
		
		JLabel lblNewLabel_2 = new JLabel("Enter Book Name:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(44, 422, 117, 26);
		contentPane.add(lblNewLabel_2);
		
		txtBookName = new JTextField();
		txtBookName.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtBookName.setBounds(154, 422, 176, 26);
		contentPane.add(txtBookName);
		txtBookName.setColumns(10);
		
		Button btnSearch = new Button("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchTitle = txtBookName.getText();
                if (searchTitle.isEmpty()) {
                    JOptionPane.showMessageDialog(btnSearch, "Please enter a title to search.");
                    return;
                }

                try {
                    String encodedTitle = URLEncoder.encode(searchTitle, "UTF-8");
                    String urlString = "http://localhost/projectDAD/LibraryServer.php?action=searchBookByTitle"
                            + "&title=" + encodedTitle;

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
                        
                        // Print raw response
                        //System.out.println("Raw response: " + response.toString());

                        // Parse JSON
                        JSONObject jsonResponse = new JSONObject(response.toString());

                        if (jsonResponse.has("message")) {
                            JOptionPane.showMessageDialog(btnSearch, jsonResponse.getString("message"));
                        } else if (jsonResponse.has("error")) {
                            JOptionPane.showMessageDialog(btnSearch, "Error: " + jsonResponse.getString("error"));
                        } else {
                            JSONArray jsonArray = jsonResponse.getJSONArray("results");
                            model.setRowCount(0); // Clear existing rows

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String title = jsonObject.getString("title");
                		        String isbn = jsonObject.getString("isbn");
                		        int publishedYear = jsonObject.getInt("publishedYear");
                		        String category = jsonObject.getString("category");

                		        model.addRow(new Object[]{title, isbn, publishedYear, category});
                            }
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
			}
		});
		btnSearch.setFont(new Font("Dialog", Font.BOLD, 12));
		btnSearch.setBounds(336, 422, 85, 26);
		contentPane.add(btnSearch);
		
		Button btnRefresh = new Button("REFRESH");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayBooks();
			}
		});
		btnRefresh.setFont(new Font("Dialog", Font.BOLD, 12));
		btnRefresh.setBounds(77, 111, 117, 26);
		contentPane.add(btnRefresh);
		
	}
		private void displayBooks() {
			
		String urlString = "http://localhost/projectDAD/LibraryServer.php?action=retrieve";
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


		    // Parse JSON
		    JSONArray jsonArray = new JSONArray(response.toString());
		    
		    for (int i = 0; i < jsonArray.length(); i++) {
		        JSONObject jsonObject = jsonArray.getJSONObject(i);
		        String title = jsonObject.getString("title");
		        String isbn = jsonObject.getString("isbn");
		        int publishedYear = jsonObject.getInt("publishedYear");
		        String category = jsonObject.getString("category");

		        model.addRow(new Object[]{title, isbn, publishedYear, category});
		    }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
		private void sendBorrowRequest(String title, String isbn, String dateBorrowed, String status, int memberID) {
		    try {
		        String urlString = "http://localhost/projectDAD/LibraryServer.php?action=borrow";

		        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
		        conn.setRequestMethod("POST");
		        conn.setDoOutput(true);
		        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		        String postData = "title=" + URLEncoder.encode(title, "UTF-8") +
		                          "&isbn=" + URLEncoder.encode(isbn, "UTF-8") +
		                          "&dateBorrowed=" + URLEncoder.encode(dateBorrowed, "UTF-8") +
		                          "&status=" + URLEncoder.encode(status, "UTF-8") +
		                          "&memberID=" + URLEncoder.encode(String.valueOf(memberID), "UTF-8");

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
		            JOptionPane.showMessageDialog(frame, "Books borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		        } else if (response.toString().contains("more")) {
		            JOptionPane.showMessageDialog(frame, "You have already borrowed 3 books. Please return them before borrowing another book.", "Error", JOptionPane.WARNING_MESSAGE);
		        } else {
		            JOptionPane.showMessageDialog(frame, "Error borrowing books. Please try again.", "Error", JOptionPane.WARNING_MESSAGE);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(frame, "Error borrowing books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}

}
