package View;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReturnBook extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // ReturnBook frame = new ReturnBook();
                    // frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ReturnBook(int memberId) {
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
                new String[]{"ID", "Title", "ISBN", "Date Borrowed", "Status"}
        );

        table.setModel(model);

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(235);
        table.getColumnModel().getColumn(2).setPreferredWidth(149);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(133);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.GRAY);
        panel_1.setBounds(0, 459, 817, 35);
        contentPane.add(panel_1);

        JLabel lblSelectYourBook = new JLabel("SELECT YOUR BOOK TO RETURN");
        lblSelectYourBook.setFont(new Font("League Spartan", Font.PLAIN, 18));
        lblSelectYourBook.setBounds(239, 101, 300, 34);
        contentPane.add(lblSelectYourBook);

        Button buttonReturn = new Button("Return");
        buttonReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();

                // Check if any row is selected
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(frame, "Please select at least one book to return.", "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Iterate over selected rows and prepare data to send to PHP script
                for (int i : selectedRows) {
                	int id = (int) table.getValueAt(i, 0);
                    String title = (String) table.getValueAt(i, 1);
                    String isbn = (String) table.getValueAt(i, 2);

                    // Example: Call method to send data to PHP script
                    returnBook(id, title, isbn, memberId);
                }
            }
        });
        buttonReturn.setFont(new Font("League Spartan", Font.BOLD, 14));
        buttonReturn.setBounds(332, 417, 100, 26);
        contentPane.add(buttonReturn);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(0, 0, 128));
        panel_2.setBounds(0, 62, 817, 21);
        contentPane.add(panel_2);

        Button buttonRefresh = new Button("REFRESH");
        buttonRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayBorrowed(memberId);
            }
        });
        buttonRefresh.setFont(new Font("Dialog", Font.BOLD, 14));
        buttonRefresh.setBounds(77, 101, 115, 26);
        contentPane.add(buttonRefresh);

        displayBorrowed(memberId);

        // Set the custom renderer
        table.setDefaultRenderer(Object.class, new CustomRenderer());
    }

    private void displayBorrowed(int memberId) {
        String urlString = "http://localhost/projectDAD/LibraryServer.php?action=retrieveBorrowedBooksByMember&memberID=" + memberId;
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
            if (response.toString().startsWith("[")) {
                // JSONArray case: Multiple books
                JSONArray jsonArray = new JSONArray(response.toString());

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
                    int id = jsonObject.getInt("id");
                    String title = jsonObject.getString("title");
                    String isbn = jsonObject.getString("isbn");
                    String dateBorrowed = jsonObject.getString("dateBorrowed");
                    String status = jsonObject.getString("status");

                    // Add row to table model
                    model.addRow(new Object[]{id, title, isbn, dateBorrowed, status});
                }
            } else if (response.toString().startsWith("{")) {
                // JSONObject case: Single book
                JSONObject jsonObject = new JSONObject(response.toString());

                if (jsonObject.has("message") && jsonObject.getString("message").equals("No books found for this member")) {
                    // No books found
                    JOptionPane.showMessageDialog(frame, "No books borrowed", "Information", JOptionPane.INFORMATION_MESSAGE);
                    // Clear the table model
                    model.setRowCount(0);
                } else {
                	int id = jsonObject.getInt("id");
                    String title = jsonObject.getString("title");
                    String isbn = jsonObject.getString("isbn");
                    String dateBorrowed = jsonObject.getString("dateBorrowed");
                    String status = jsonObject.getString("status");

                    model.setRowCount(0);

                    // Add row to table model
                    model.addRow(new Object[]{id, title, isbn, dateBorrowed, status});
                }
            } else {
                // Unexpected response structure
                System.out.println("Unexpected response structure: " + response.toString());
                JOptionPane.showMessageDialog(frame, "Unexpected response structure", "Error", JOptionPane.ERROR_MESSAGE);
                // Clear the table model
                model.setRowCount(0);
            }

            // Check for books borrowed for more than 3 days
            int booksBorrowedMoreThan3Days = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = new Date();

            for (int i = 0; i < model.getRowCount(); i++) {
                String dateBorrowedStr = (String) model.getValueAt(i, 3);
                Date dateBorrowed = sdf.parse(dateBorrowedStr);
                long diffInMillies = Math.abs(currentDate.getTime() - dateBorrowed.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (diffInDays > 3) {
                    booksBorrowedMoreThan3Days++;
                }
            }

            if (booksBorrowedMoreThan3Days > 0) {
                JOptionPane.showMessageDialog(frame, "There are " + booksBorrowedMoreThan3Days + " book(s) that are borrowed for more than 3 days.\nPlease return those books.", "NOTICE", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching borrowed books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnBook(int id, String title, String isbn, int memberId) {
        try {
            String urlString = "http://localhost/projectDAD/LibraryServer.php?action=returnBorrowedBook";

            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String postData = "id=" + URLEncoder.encode(String.valueOf(id), "UTF-8") +
                              "&title=" + URLEncoder.encode(title, "UTF-8") +
                              "&isbn=" + URLEncoder.encode(isbn, "UTF-8") +
                              "&memberID=" + URLEncoder.encode(String.valueOf(memberId), "UTF-8");

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
                JOptionPane.showMessageDialog(frame, "Books returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Error returning books. Please try again.", "Error", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error returning books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Custom renderer to highlight rows with books borrowed for more than 3 days
    class CustomRenderer extends DefaultTableCellRenderer {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            try {
                String dateBorrowedStr = (String) table.getValueAt(row, 3);
                Date dateBorrowed = sdf.parse(dateBorrowedStr);
                long diffInMillies = Math.abs(currentDate.getTime() - dateBorrowed.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (diffInDays > 3) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(Color.WHITE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return c;
        }
    }
}