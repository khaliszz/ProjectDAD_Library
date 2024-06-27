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

public class History extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JPanel contentPane;
    private JTextField textFieldSearch;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    History frame = new History();
                    frame.setVisible(true);
                    //frame.startPolling();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public History() {
        initialize();
        loadReturnedBooksHistory();
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

        // Panel for header
        Panel panel = new Panel();
        panel.setBackground(new Color(128, 128, 128));
        panel.setBounds(0, 0, 817, 65);
        contentPane.add(panel);
        panel.setLayout(null);

        // Label for application title
        JLabel lblNewLabel = new JLabel("UTeM Library - Staff");
        lblNewLabel.setFont(new Font("League Spartan", Font.PLAIN, 18));
        lblNewLabel.setBounds(118, 11, 203, 34);
        panel.add(lblNewLabel);

        // Image icon for logo
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(History.class.getResource("/Images/Logo-Universiti-Teknikal-Malaysia-Melaka-UTeM-Jawi-new-300x300 (1).png")));
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

        // Scroll pane for displaying table
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(78, 121, 647, 266);
        contentPane.add(scrollPane);

        // Table to display returned book history
        table = new JTable();
        scrollPane.setViewportView(table);

        // Define table model with column names
        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Title", "ISBN", "Date Borrowed", "Member ID"}
        );
        table.setModel(model);

        // Set preferred widths for each column
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);

        // Panel for footer
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.GRAY);
        panel_1.setBounds(0, 459, 817, 35);
        contentPane.add(panel_1);

        // Label for section title
        JLabel lblReturnedBookHistory = new JLabel("RETURNED BOOK HISTORY");
        lblReturnedBookHistory.setFont(new Font("League Spartan", Font.PLAIN, 18));
        lblReturnedBookHistory.setBounds(78, 76, 250, 34);
        contentPane.add(lblReturnedBookHistory);

        // Text field for member ID search
        textFieldSearch = new JTextField();
        textFieldSearch.setBounds(462, 86, 172, 20);
        contentPane.add(textFieldSearch);
        textFieldSearch.setColumns(10);

        // Label for search field
        JLabel lblNewLabel_2 = new JLabel("Member ID:");
        lblNewLabel_2.setBounds(393, 89, 75, 14);
        contentPane.add(lblNewLabel_2);

        // Button for searching history based on member ID
        JButton btnSearchHistory = new JButton("Search");
        btnSearchHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String memberID = textFieldSearch.getText();
                if (memberID.isEmpty()) {
                    JOptionPane.showMessageDialog(btnSearchHistory, "Please enter a member ID to search.");
                    return;
                }

                try {
                    String encodedMemberID = URLEncoder.encode(memberID, "UTF-8");
                    String urlString = "http://localhost/projectDAD/LibraryServer.php?action=getReturnedBooksHistory"
                            + "&memberID=" + encodedMemberID;

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
                        System.out.println("Raw response: " + response.toString());

                        // Parse JSON
                        JSONObject jsonResponse = new JSONObject(response.toString());

                        if (jsonResponse.has("message")) {
                            JOptionPane.showMessageDialog(btnSearchHistory, jsonResponse.getString("message"));
                        } else if (jsonResponse.has("error")) {
                            JOptionPane.showMessageDialog(btnSearchHistory, "Error: " + jsonResponse.getString("error"));
                        } else {
                            JSONArray jsonArray = jsonResponse.getJSONArray("results");
                            model.setRowCount(0); // Clear existing rows

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("Title");
                                String isbn = jsonObject.getString("ISBN");
                                String dateBorrowed = jsonObject.getString("dateBorrowed");
                                int retrievedMemberID = jsonObject.getInt("memberID");

                                model.addRow(new Object[]{id, title, isbn, dateBorrowed, retrievedMemberID});
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
        btnSearchHistory.setBounds(644, 85, 75, 23);
        contentPane.add(btnSearchHistory);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		loadReturnedBooksHistory();
        	}
        });
        btnRefresh.setBounds(647, 397, 91, 28);
        contentPane.add(btnRefresh);

        // Load initial returned books history
        //loadReturnedBooksHistory();
    }


    /**
     * Method to load returned books history from server.
     */
    private void loadReturnedBooksHistory() {
        String urlString = "http://localhost/projectDAD/LibraryServer.php?action=getAllReturnedBooksHistory";
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
            if (response.toString().startsWith("[")) {
                JSONArray jsonArray = new JSONArray(response.toString());
                model.setRowCount(0); // Clear existing rows

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String title = jsonObject.getString("Title");
                    String isbn = jsonObject.getString("ISBN");
                    String dateBorrowed = jsonObject.getString("dateBorrowed");
                    int retrievedMemberID = jsonObject.getInt("memberID");

                    model.addRow(new Object[]{id, title, isbn, dateBorrowed, retrievedMemberID});
                }
            } else {
                JSONObject jsonObject = new JSONObject(response.toString());
                if (jsonObject.has("error")) {
                    JOptionPane.showMessageDialog(null, "Error: " + jsonObject.getString("error"));
                } else {
                    JOptionPane.showMessageDialog(null, "Unexpected response format.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 