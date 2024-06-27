package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.ImageIcon;

public class HistoryMember extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    }

    /**
     * Create the frame.
     * @param memberId 
     */
    public HistoryMember(int memberId) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 833, 533);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel panel = new JPanel();
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
        lblNewLabel_1.setIcon(new ImageIcon(HistoryMember.class.getResource("/Images/Logo-Universiti-Teknikal-Malaysia-Melaka-UTeM-Jawi-new-300x300 (1).png")));
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
                new String[]{"Title", "ISBN", "Date Borrowed", "Status"}
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
        
        JLabel lblSelectYourBook = new JLabel("LISTS OF BORROWED BOOKS");
        lblSelectYourBook.setFont(new Font("League Spartan", Font.PLAIN, 18));
        lblSelectYourBook.setBounds(239, 101, 267, 34);
        contentPane.add(lblSelectYourBook);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(0, 0, 128));
        panel_2.setBounds(0, 62, 817, 21);
        contentPane.add(panel_2);
                
		displayBorrowed(memberId);

    }
    
    private void displayBorrowed(int memberId) {
        String urlString = "http://localhost/projectDAD/LibraryServer.php?action=getReturnedBooksHistory&memberID=" + memberId;
        // Parse JSON response from server
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

            // Debug: Print out the response received from server
            System.out.println("Response from server: " + response.toString());

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray jsonArray = jsonResponse.getJSONArray("results");

            if (jsonArray.length() == 0) {
                // No books found
                JOptionPane.showMessageDialog(frame, "No books borrowed", "Information", JOptionPane.INFORMATION_MESSAGE);
                // Clear the table model
                model.setRowCount(0);
            } else {
                // Clear existing table data
                model.setRowCount(0);
            }

            // Iterate over JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("Title");
                String isbn = jsonObject.getString("ISBN");
                String dateBorrowed = jsonObject.getString("dateBorrowed");
                String status = jsonObject.getString("status");

                // Add row to table model
                model.addRow(new Object[]{title, isbn, dateBorrowed, status});
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching borrowed books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
