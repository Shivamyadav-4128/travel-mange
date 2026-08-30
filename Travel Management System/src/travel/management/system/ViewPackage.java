package travel.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ViewPackage extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    ViewPackage frame = new ViewPackage("");
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ViewPackage(String username) {

        setBounds(580, 220, 850, 450);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Image
        ImageIcon i1 = new ImageIcon(
                ClassLoader.getSystemResource(
                        "travel/management/system/icons/bookedDetails.jpg"));

        Image i3 = i1.getImage().getScaledInstance(
                500, 350, Image.SCALE_DEFAULT);

        ImageIcon i2 = new ImageIcon(i3);

        JLabel la1 = new JLabel(i2);
        la1.setBounds(450, 40, 350, 350);
        contentPane.add(la1);

        // Heading
        JLabel lblName = new JLabel("VIEW PACKAGE DETAILS");
        lblName.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
        lblName.setBounds(88, 11, 350, 53);
        contentPane.add(lblName);

        // Username
        JLabel lb3 = new JLabel("Username :");
        lb3.setBounds(35, 70, 200, 14);
        contentPane.add(lb3);

        JLabel l1 = new JLabel();
        l1.setBounds(271, 70, 200, 14);
        contentPane.add(l1);

        // Package
        JLabel lblId = new JLabel("Package :");
        lblId.setBounds(35, 110, 200, 14);
        contentPane.add(lblId);

        JLabel l2 = new JLabel();
        l2.setBounds(271, 110, 200, 14);
        contentPane.add(l2);

        // Number of Persons
        JLabel lb2 = new JLabel("Number of Persons :");
        lb2.setBounds(35, 150, 200, 14);
        contentPane.add(lb2);

        JLabel l3 = new JLabel();
        l3.setBounds(271, 150, 200, 14);
        contentPane.add(l3);

        // ID Type
        JLabel lblName_1 = new JLabel("ID :");
        lblName_1.setBounds(35, 190, 200, 14);
        contentPane.add(lblName_1);

        JLabel l4 = new JLabel();
        l4.setBounds(271, 190, 200, 14);
        contentPane.add(l4);

        // Number
        JLabel lblGender = new JLabel("Number :");
        lblGender.setBounds(35, 230, 200, 14);
        contentPane.add(lblGender);

        JLabel l5 = new JLabel();
        l5.setBounds(271, 230, 200, 14);
        contentPane.add(l5);

        // Phone
        JLabel lblCountry = new JLabel("Phone :");
        lblCountry.setBounds(35, 270, 200, 14);
        contentPane.add(lblCountry);

        JLabel l6 = new JLabel();
        l6.setBounds(271, 270, 200, 14);
        contentPane.add(l6);

        // Price
        JLabel lblReserveRoomNumber = new JLabel("Price :");
        lblReserveRoomNumber.setBounds(35, 310, 200, 14);
        contentPane.add(lblReserveRoomNumber);

        JLabel l7 = new JLabel();
        l7.setBounds(271, 310, 200, 14);
        contentPane.add(l7);

        // Get package details
        try {

            Conn c = new Conn();

           String sql =
        "SELECT username, package, persons, id_type, number, phone, price " +
        "FROM bookPackage WHERE username = ? " +
        "ORDER BY booking_id DESC LIMIT 1";

            PreparedStatement pst = c.c.prepareStatement(sql);

            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                l1.setText(rs.getString("username"));
                l2.setText(rs.getString("package"));
                l3.setText(rs.getString("persons"));
                l4.setText(rs.getString("id_type"));
                l5.setText(rs.getString("number"));
                l6.setText(rs.getString("phone"));
                l7.setText(rs.getString("price"));

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "No package booking found for " + username
                );
            }

            rs.close();
            pst.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error loading package details: " + e.getMessage()
            );
        }

        // Back button
        JButton btnExit = new JButton("Back");

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        btnExit.setBounds(160, 350, 120, 30);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        contentPane.add(btnExit);

        getContentPane().setBackground(Color.WHITE);
    }
}