package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StaffLibrary extends JFrame{

	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private JPanel contentPane;
	private JTextField textFieldSearch;
	private JTextField textFieldID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffLibrary frame = new StaffLibrary();
					frame.setVisible(true);
					frame.startPolling();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StaffLibrary() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
		
		JLabel lblNewLabel = new JLabel("UTeM Library - Staff");
		lblNewLabel.setFont(new Font("League Spartan", Font.PLAIN, 18));
		lblNewLabel.setBounds(118, 11, 203, 34);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(StaffLibrary.class.getResource("/Images/Logo-Universiti-Teknikal-Malaysia-Melaka-UTeM-Jawi-new-300x300 (1).png")));
		lblNewLabel_1.setBounds(20, 11, 88, 44);
		panel.add(lblNewLabel_1);
		
		JButton btnBack_1 = new JButton("BACK");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaffHomePage window = new StaffHomePage();
				window.setVisible(true);
				dispose();

			}
		});
		btnBack_1.setFont(new Font("Dialog", Font.BOLD, 15));
		btnBack_1.setBounds(689, 11, 118, 35);
		panel.add(btnBack_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(78, 121, 647, 266);
		contentPane.add(scrollPane);
		
		table = new JTable();
        scrollPane.setViewportView(table);

        // Define table model with column names
        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Title", "ISBN", "Date Borrowed", "Borrower ID"}
        );

        table.setModel(model);
        
		table.getColumnModel().getColumn(0).setPreferredWidth(235);
		table.getColumnModel().getColumn(1).setPreferredWidth(149);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(3).setPreferredWidth(133);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(0, 459, 817, 35);
		contentPane.add(panel_1);
		
		JLabel lblBorrowedBook = new JLabel("BORROWED BOOK");
		lblBorrowedBook.setFont(new Font("League Spartan", Font.PLAIN, 18));
		lblBorrowedBook.setBounds(78, 76, 188, 34);
		contentPane.add(lblBorrowedBook);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(142, 422, 182, 20);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Title:");
		lblNewLabel_2.setBounds(78, 424, 33, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnSearchBook = new JButton("Search");
		btnSearchBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchTitle = textFieldSearch.getText();
                if (searchTitle.isEmpty()) {
                    JOptionPane.showMessageDialog(btnSearchBook, "Please enter a title to search.");
                    return;
                }

                try {
                    String encodedTitle = URLEncoder.encode(searchTitle, "UTF-8");
                    String urlString = "http://localhost/projectDAD/LibraryServer.php?action=searchBorrowedBook"
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
                            JOptionPane.showMessageDialog(btnSearchBook, jsonResponse.getString("message"));
                        } else if (jsonResponse.has("error")) {
                            JOptionPane.showMessageDialog(btnSearchBook, "Error: " + jsonResponse.getString("error"));
                        } else {
                            JSONArray jsonArray = jsonResponse.getJSONArray("results");
                            model.setRowCount(0); // Clear existing rows

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String isbn = jsonObject.getString("isbn");
                                String dateBorrowed = jsonObject.getString("dateBorrowed");
                                int memberID = jsonObject.getInt("memberID");

                                model.addRow(new Object[]{title, isbn, dateBorrowed, memberID});
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
		btnSearchBook.setBounds(334, 420, 75, 23);
		contentPane.add(btnSearchBook);
		
		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		textFieldID.setBounds(142, 398, 182, 20);
		contentPane.add(textFieldID);
		
		JLabel lblNewLabel_2_1 = new JLabel("Member ID:");
		lblNewLabel_2_1.setBounds(78, 400, 69, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JButton btnSearchID = new JButton("Search");
		btnSearchID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchID = textFieldID.getText();
                if (searchID.isEmpty()) {
                    JOptionPane.showMessageDialog(btnSearchBook, "Please enter ID to search.");
                    return;
                }

                try {
                    String encodedID = URLEncoder.encode(searchID, "UTF-8");
                    String urlString = "http://localhost/projectDAD/LibraryServer.php?action=searchBorrowedBookByID"
                            + "&memberID=" + encodedID;

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
                            JOptionPane.showMessageDialog(btnSearchBook, jsonResponse.getString("message"));
                        } else if (jsonResponse.has("error")) {
                            JOptionPane.showMessageDialog(btnSearchBook, "Error: " + jsonResponse.getString("error"));
                        } else {
                            JSONArray jsonArray = jsonResponse.getJSONArray("results");
                            model.setRowCount(0); // Clear existing rows

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String title = jsonObject.getString("Title");
                                String isbn = jsonObject.getString("ISBN");
                                String dateBorrowed = jsonObject.getString("dateBorrowed");
                                int memberID = jsonObject.getInt("memberID");

                                model.addRow(new Object[]{title, isbn, dateBorrowed, memberID});
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
		btnSearchID.setBounds(334, 396, 75, 23);
		contentPane.add(btnSearchID);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadBorrowedBooks();
			}
		});
		btnReset.setBounds(615, 397, 110, 23);
		contentPane.add(btnReset);
		
		
		loadBorrowedBooks();
        
	}
	
	private void loadBorrowedBooks() {
        String urlString = "http://localhost/projectDAD/LibraryServer.php?action=retrieveBorrowedBooks";
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

            // Parse JSON
            JSONArray jsonArray = new JSONArray(response.toString());
            
            model.setRowCount(0); // Clear existing rows

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("Title");
                String isbn = jsonObject.getString("ISBN");
                String dateBorrowed = jsonObject.getString("dateBorrowed");
                int memberID = jsonObject.getInt("memberID");

                model.addRow(new Object[]{title, isbn, dateBorrowed, memberID});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to start polling
    private void startPolling() {
        new Thread(() -> {
            while (true) {
                loadBorrowedBooks();
                try {
                    Thread.sleep(10000); // Wait for 10 seconds before next poll
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
