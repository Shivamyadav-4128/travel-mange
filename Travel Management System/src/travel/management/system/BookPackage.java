package travel.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BookPackage extends JFrame {

    private JPanel contentPane;
    private JTextField t1;
    private Choice c1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BookPackage frame = new BookPackage("");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BookPackage(String username) {

        setBounds(420, 220, 1100, 450);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Image
        ImageIcon i1 = new ImageIcon(
                ClassLoader.getSystemResource(
                        "travel/management/system/icons/bookpackage.jpg"));

        Image i3 = i1.getImage().getScaledInstance(
                500, 300, Image.SCALE_DEFAULT);

        ImageIcon i2 = new ImageIcon(i3);

        JLabel la1 = new JLabel(i2);
        la1.setBounds(450, 50, 700, 300);
        contentPane.add(la1);

        // Heading
        JLabel lblName = new JLabel("BOOK PACKAGE");
        lblName.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
        lblName.setBounds(118, 11, 300, 53);
        contentPane.add(lblName);

        // Username
        JLabel la2 = new JLabel("Username :");
        la2.setBounds(35, 70, 200, 14);
        contentPane.add(la2);

        JLabel l1 = new JLabel(username);
        l1.setBounds(271, 70, 200, 14);
        contentPane.add(l1);

        // Package
        JLabel lblId = new JLabel("Select Package :");
        lblId.setBounds(35, 110, 200, 14);
        contentPane.add(lblId);

        c1 = new Choice();
        c1.add("Gold Package");
        c1.add("Silver Package");
        c1.add("Bronze Package");
        c1.setBounds(271, 110, 150, 30);
        contentPane.add(c1);

        // Total Persons
        JLabel la3 = new JLabel("Total Persons");
        la3.setBounds(35, 150, 200, 14);
        contentPane.add(la3);

        t1 = new JTextField();
        t1.setText("0");
        t1.setBounds(271, 150, 150, 20);
        contentPane.add(t1);

        // ID Type
        JLabel lbl1 = new JLabel("ID :");
        lbl1.setBounds(35, 190, 200, 14);
        contentPane.add(lbl1);

        JLabel l2 = new JLabel();
        l2.setBounds(271, 190, 200, 14);
        contentPane.add(l2);

        // Number
        JLabel lbl2 = new JLabel("Number :");
        lbl2.setBounds(35, 230, 200, 14);
        contentPane.add(lbl2);

        JLabel l3 = new JLabel();
        l3.setBounds(271, 230, 200, 14);
        contentPane.add(l3);

        // Phone
        JLabel lbl3 = new JLabel("Phone :");
        lbl3.setBounds(35, 270, 200, 14);
        contentPane.add(lbl3);

        JLabel l4 = new JLabel();
        l4.setBounds(271, 270, 200, 14);
        contentPane.add(l4);

        // Total Price
        JLabel lblDeposite = new JLabel("Total Price :");
        lblDeposite.setBounds(35, 310, 200, 14);
        contentPane.add(lblDeposite);

        JLabel l5 = new JLabel();
        l5.setBounds(271, 310, 200, 14);
        l5.setForeground(Color.RED);
        contentPane.add(l5);

        // Load customer details
        try {

            Conn c = new Conn();

            String sql = "SELECT id_type, number, phone " +
                         "FROM customer WHERE username = ?";

            PreparedStatement pst = c.c.prepareStatement(sql);
            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                l2.setText(rs.getString("id_type"));
                l3.setText(rs.getString("number"));
                l4.setText(rs.getString("phone"));

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Customer details not found!"
                );
            }

            rs.close();
            pst.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error loading customer details: " + e.getMessage()
            );
        }

        // Check Price button
        JButton b1 = new JButton("Check Price");

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    int persons = Integer.parseInt(t1.getText());

                    if (persons <= 0) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Please enter valid number of persons"
                        );
                        return;
                    }

                    String p = c1.getSelectedItem();

                    int cost = 0;

                    if (p.equals("Gold Package")) {

                        cost = 12000;

                    } else if (p.equals("Silver Package")) {

                        cost = 25000;

                    } else if (p.equals("Bronze Package")) {

                        cost = 32000;
                    }

                    cost = cost * persons;

                    l5.setText("Rs " + cost);

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please enter a valid number"
                    );
                }
            }
        });

        b1.setBounds(50, 350, 120, 30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        contentPane.add(b1);

        // Book button
        JButton btnNewButton = new JButton("Book");

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Check price first
                if (l5.getText().equals("")) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please click Check Price first!"
                    );

                    return;
                }

                try {

                    Conn c = new Conn();

                    String sql =
                            "INSERT INTO bookPackage " +
                            "(username, package, persons, id_type, number, phone, price) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement pst = c.c.prepareStatement(sql);

                    pst.setString(1, username);
                    pst.setString(2, c1.getSelectedItem());
                    pst.setString(3, t1.getText());
                    pst.setString(4, l2.getText());
                    pst.setString(5, l3.getText());
                    pst.setString(6, l4.getText());
                    pst.setString(7, l5.getText());

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(
                            null,
                            "Package Booked Successfully"
                    );

                    pst.close();

                    setVisible(false);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Booking Error: " + ex.getMessage()
                    );
                }
            }
        });

        btnNewButton.setBounds(200, 350, 120, 30);
        btnNewButton.setBackground(Color.BLACK);
        btnNewButton.setForeground(Color.WHITE);
        contentPane.add(btnNewButton);

        // Back button
        JButton btnExit = new JButton("Back");

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        btnExit.setBounds(350, 350, 120, 30);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        contentPane.add(btnExit);

        getContentPane().setBackground(Color.WHITE);
    }
}