package bloomslevel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BLOOMSLEVEL {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

class LoginFrame extends JFrame implements ActionListener {
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;
    Connection conn;

    public LoginFrame() {
        setTitle("User - Login");
        setSize(380, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 250, 255));

        JLabel title = new JLabel("Welcome");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(130, 20, 250, 30);
        panel.add(title);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUser.setBounds(50, 70, 80, 25);
        panel.add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(140, 70, 180, 25);
        panel.add(txtUser);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPass.setBounds(50, 110, 80, 25);
        panel.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(140, 110, 180, 25);
        panel.add(txtPass);

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnLogin.setBackground(new Color(100, 149, 237));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBounds(140, 160, 180, 30);
        btnLogin.addActionListener(this);
        panel.add(btnLogin);

        add(panel);
        connectDB();
        setVisible(true);
    }

    void connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/OneDrive/Desktop/SRMAP/Apps/javaapp.db");
            System.out.println("Connected to DB - LoginFrame");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + e);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String username = txtUser.getText().trim();
        String password = String.valueOf(txtPass.getPassword()).trim();

        try {
            String query = "SELECT * FROM users WHERE uname = ? AND pwd = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new BloomLevelFrame(conn);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Login Error: " + ex);
        }
    }
}

class BloomLevelFrame extends JFrame implements ActionListener {
    JLabel lblCode, lblLevel, lblDesc;
    JTextField txtCode;
    JComboBox<String> comboLevel;
    JTextArea txtDesc;
    JButton btnAdd, btnUpdate, btnDelete, btnSearch;
    JTable table;
    DefaultTableModel tableModel;
    Connection conn;

    public BloomLevelFrame(Connection connection) {
        this.conn = connection;

        setTitle("Bloom's Level Manager");
        setSize(600, 600);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblCode = new JLabel("Bloom Code:");
        txtCode = new JTextField();

        lblLevel = new JLabel("Bloom Level:");
        String[] levels = {"-- Select Bloom Level --", "Remember", "Understand", "Apply", "Analyze", "Evaluate", "Create"};
        comboLevel = new JComboBox<>(levels);

        lblDesc = new JLabel("Description:");
        txtDesc = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(txtDesc);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");

        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnSearch.addActionListener(this);

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; add(lblCode, gbc);
        gbc.gridx = 1; add(txtCode, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(lblLevel, gbc);
        gbc.gridx = 1; add(comboLevel, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(lblDesc, gbc);
        gbc.gridx = 1; add(scrollPane, gbc);

        JPanel btnPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnSearch);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // Table setup
        String[] columns = {"ID", "Bloom Code", "Bloom Level", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        // 👇 Custom styling for JTable
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(100, 149, 237)); // blue
        header.setForeground(Color.WHITE);              // white text
        header.setFont(new Font("SansSerif", Font.BOLD, 14)); // bold font

        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));

        // Zebra striping (alternate row color)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 240, 255));
                } else {
                    c.setBackground(new Color(173, 216, 230)); // highlight on select
                }
                return c;
            }
        });

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(550, 150));
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(tableScroll, gbc);

        viewBlooms();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) addBloom();
        else if (e.getSource() == btnUpdate) updateBloom();
        else if (e.getSource() == btnDelete) deleteBloom();
        else if (e.getSource() == btnSearch) searchBloom();
    }

    void clearFields() {
        txtCode.setText("");
        comboLevel.setSelectedIndex(0);
        txtDesc.setText("");
    }

    void addBloom() {
        try {
            String code = txtCode.getText().trim();
            String level = (String) comboLevel.getSelectedItem();
            String desc = txtDesc.getText().trim();

            if (code.isEmpty() || level.equals("-- Select Bloom Level --") || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            String query = "INSERT INTO blooms_level (bloom_code, bloom_level, bloom_description) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, code);
            pst.setString(2, level);
            pst.setString(3, desc);
            pst.executeUpdate();
            pst.close();

            JOptionPane.showMessageDialog(this, "Bloom's Level Added!");
            clearFields();
            viewBlooms();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex);
        }
    }

    void searchBloom() {
        try {
            String code = txtCode.getText().trim();
            if (code.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Bloom Code to search.");
                return;
            }

            String query = "SELECT * FROM blooms_level WHERE bloom_code = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, code);
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0);
            if (rs.next()) {
                comboLevel.setSelectedItem(rs.getString("bloom_level"));
                txtDesc.setText(rs.getString("bloom_description"));
                tableModel.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("bloom_code"),
                        rs.getString("bloom_level"),
                        rs.getString("bloom_description")
                });
            } else {
                JOptionPane.showMessageDialog(this, "No record found.");
            }

            pst.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Search Error: " + ex);
        }
    }

    void updateBloom() {
        try {
            String code = txtCode.getText().trim();
            String level = (String) comboLevel.getSelectedItem();
            String desc = txtDesc.getText().trim();

            if (code.isEmpty() || level.equals("-- Select Bloom Level --") || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required to update!");
                return;
            }

            String selectQuery = "SELECT * FROM blooms_level WHERE bloom_code = ?";
            PreparedStatement pstSelect = conn.prepareStatement(selectQuery);
            pstSelect.setString(1, code);
            ResultSet rs = pstSelect.executeQuery();

            if (rs.next()) {
                String updateQuery = "UPDATE blooms_level SET bloom_level = ?, bloom_description = ? WHERE bloom_code = ?";
                PreparedStatement pstUpdate = conn.prepareStatement(updateQuery);
                pstUpdate.setString(1, level);
                pstUpdate.setString(2, desc);
                pstUpdate.setString(3, code);
                pstUpdate.executeUpdate();
                pstUpdate.close();

                JOptionPane.showMessageDialog(this, "Updated Successfully!");
                viewBlooms();
            } else {
                JOptionPane.showMessageDialog(this, "No record found to update.");
            }

            pstSelect.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update Error: " + ex);
        }
    }

    void deleteBloom() {
        String code = JOptionPane.showInputDialog(this, "Enter Bloom Code to Delete:");
        if (code != null && !code.trim().isEmpty()) {
            try {
                String query = "DELETE FROM blooms_level WHERE bloom_code = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, code.trim());

                int result = pst.executeUpdate();
                pst.close();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Deleted Successfully.");
                    viewBlooms();
                } else {
                    JOptionPane.showMessageDialog(this, "No record found.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Delete Error: " + ex);
            }
        }
    }

    void viewBlooms() {
        try {
            String query = "SELECT * FROM blooms_level";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("bloom_code"),
                        rs.getString("bloom_level"),
                        rs.getString("bloom_description")
                });
            }

            pst.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "View Error: " + ex);
        }
    }
}
