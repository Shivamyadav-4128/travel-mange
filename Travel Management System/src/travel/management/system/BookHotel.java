package travel.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BookHotel extends JFrame {

    private JPanel contentPane;
    JTextField t1, t2;
    Choice c1, c2, c3;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BookHotel frame = new BookHotel("");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BookHotel(String username) {

        setBounds(420, 220, 1100, 600);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ================= IMAGE =================

        ImageIcon i1 = new ImageIcon(
                ClassLoader.getSystemResource(
                        "travel/management/system/icons/book.jpg"));

        Image i3 = i1.getImage().getScaledInstance(
                500, 300, Image.SCALE_DEFAULT);

        ImageIcon i2 = new ImageIcon(i3);

        JLabel la1 = new JLabel(i2);
        la1.setBounds(450, 100, 700, 300);
        contentPane.add(la1);

        // ================= HEADING =================

        JLabel lblName = new JLabel("BOOK HOTEL");
        lblName.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
        lblName.setBounds(118, 11, 300, 53);
        contentPane.add(lblName);

        // ================= USERNAME =================

        JLabel la2 = new JLabel("Username :");
        la2.setBounds(35, 70, 200, 14);
        contentPane.add(la2);

        JLabel l1 = new JLabel(username);
        l1.setBounds(271, 70, 200, 14);
        contentPane.add(l1);

        // ================= SELECT HOTEL =================

        JLabel lblId = new JLabel("Select Hotel :");
        lblId.setBounds(35, 110, 200, 14);
        contentPane.add(lblId);

        c1 = new Choice();

        Conn c = new Conn();

        try {

            ResultSet rs = c.s.executeQuery("SELECT name FROM hotels");

            while (rs.next()) {
                c1.add(rs.getString("name"));
            }

            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error loading hotels: " + e.getMessage()
            );
        }

        c1.setBounds(271, 110, 150, 30);
        contentPane.add(c1);

        // ================= TOTAL PERSONS =================

        JLabel la3 = new JLabel("Total Persons");
        la3.setBounds(35, 150, 200, 14);
        contentPane.add(la3);

        t1 = new JTextField();
        t1.setText("0");
        t1.setBounds(271, 150, 150, 20);
        contentPane.add(t1);

        // ================= NUMBER OF DAYS =================

        JLabel la4 = new JLabel("Number of Days");
        la4.setBounds(35, 190, 200, 14);
        contentPane.add(la4);

        t2 = new JTextField();
        t2.setText("0");
        t2.setBounds(271, 190, 150, 20);
        contentPane.add(t2);

        // ================= AC / NON AC =================

        JLabel la5 = new JLabel("AC / Non-AC");
        la5.setBounds(35, 230, 200, 14);
        contentPane.add(la5);

        c2 = new Choice();
        c2.add("AC");
        c2.add("Non-AC");
        c2.setBounds(271, 230, 150, 30);
        contentPane.add(c2);

        // ================= FOOD =================

        JLabel la6 = new JLabel("Food Included :");
        la6.setBounds(35, 270, 200, 14);
        contentPane.add(la6);

        c3 = new Choice();
        c3.add("Yes");
        c3.add("No");
        c3.setBounds(271, 270, 150, 30);
        contentPane.add(c3);

        // ================= ID =================

        JLabel lbl1 = new JLabel("ID :");
        lbl1.setBounds(35, 310, 200, 14);
        contentPane.add(lbl1);

        JLabel l2 = new JLabel();
        l2.setBounds(271, 310, 200, 14);
        contentPane.add(l2);

        // ================= NUMBER =================

        JLabel lbl2 = new JLabel("Number :");
        lbl2.setBounds(35, 350, 200, 14);
        contentPane.add(lbl2);

        JLabel l3 = new JLabel();
        l3.setBounds(271, 350, 200, 14);
        contentPane.add(l3);

        // ================= PHONE =================

        JLabel lbl3 = new JLabel("Phone :");
        lbl3.setBounds(35, 390, 200, 14);
        contentPane.add(lbl3);

        JLabel l4 = new JLabel();
        l4.setBounds(271, 390, 200, 14);
        contentPane.add(l4);

        // ================= TOTAL PRICE =================

        JLabel lblDeposite = new JLabel("Total Price :");
        lblDeposite.setBounds(35, 430, 200, 14);
        contentPane.add(lblDeposite);

        JLabel l5 = new JLabel();
        l5.setBounds(271, 430, 200, 14);
        l5.setForeground(Color.RED);
        contentPane.add(l5);

        // =====================================================
        // LOAD CUSTOMER DETAILS
        // =====================================================

        try {

            String sql =
                    "SELECT id_type, number, phone " +
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
                        "Customer details not found for: " + username
                );
            }

            rs.close();
            pst.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error loading customer details: "
                    + e.getMessage()
            );
        }

        // =====================================================
        // CHECK PRICE BUTTON
        // =====================================================

        JButton b1 = new JButton("Check Price");

        b1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    String hotelName = c1.getSelectedItem();

                    int persons = Integer.parseInt(t1.getText());
                    int days = Integer.parseInt(t2.getText());

                    if (persons <= 0 || days <= 0) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Enter valid persons and days."
                        );

                        return;
                    }

                    Conn c = new Conn();

                    String sql =
                            "SELECT cost_per_day, food_charges, ac_charges " +
                            "FROM hotels WHERE name = ?";

                    PreparedStatement pst =
                            c.c.prepareStatement(sql);

                    pst.setString(1, hotelName);

                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {

                        int cost =
                                Integer.parseInt(
                                        rs.getString("cost_per_day"));

                        int food =
                                Integer.parseInt(
                                        rs.getString("food_charges"));

                        int ac =
                                Integer.parseInt(
                                        rs.getString("ac_charges"));

                        String acprice = c2.getSelectedItem();
                        String foodprice = c3.getSelectedItem();

                        int totalPerPersonPerDay = cost;

                        if (acprice.equals("AC")) {
                            totalPerPersonPerDay += ac;
                        }

                        if (foodprice.equals("Yes")) {
                            totalPerPersonPerDay += food;
                        }

                        int total =
                                totalPerPersonPerDay
                                * persons
                                * days;

                        l5.setText("Rs " + total);
                    }

                    rs.close();
                    pst.close();

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please enter numbers only for persons and days."
                    );

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Error calculating price: "
                            + ex.getMessage()
                    );
                }
            }
        });

        b1.setBounds(50, 470, 120, 30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        contentPane.add(b1);

        // =====================================================
        // BOOK BUTTON
        // =====================================================

        JButton btnNewButton = new JButton("Book");

        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    // Check price first
                    if (l5.getText().equals("")) {

                        JOptionPane.showMessageDialog(
                                null,
                                "First click Check Price."
                        );

                        return;
                    }

                    Conn c = new Conn();

                    /*
                     * booking_id is AUTO_INCREMENT,
                     * so we DO NOT insert it manually.
                     */

                    String sql =
                            "INSERT INTO bookHotel " +
                            "(username, hotel, persons, days, ac, food, id_type, number, phone, price) "
                            +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement pst =
                            c.c.prepareStatement(sql);

                    pst.setString(1, l1.getText());
                    pst.setString(2, c1.getSelectedItem());
                    pst.setString(3, t1.getText());
                    pst.setString(4, t2.getText());
                    pst.setString(5, c2.getSelectedItem());
                    pst.setString(6, c3.getSelectedItem());
                    pst.setString(7, l2.getText());
                    pst.setString(8, l3.getText());
                    pst.setString(9, l4.getText());
                    pst.setString(10, l5.getText());

                    pst.executeUpdate();

                    pst.close();

                    JOptionPane.showMessageDialog(
                            null,
                            "Hotel Booked Successfully"
                    );

                    setVisible(false);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Error booking hotel: "
                            + ex.getMessage()
                    );
                }
            }
        });

        btnNewButton.setBounds(200, 470, 120, 30);
        btnNewButton.setBackground(Color.BLACK);
        btnNewButton.setForeground(Color.WHITE);
        contentPane.add(btnNewButton);

        // =====================================================
        // BACK BUTTON
        // =====================================================

        JButton btnExit = new JButton("Back");

        btnExit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        btnExit.setBounds(350, 470, 120, 30);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        contentPane.add(btnExit);

        getContentPane().setBackground(Color.WHITE);
    }
}