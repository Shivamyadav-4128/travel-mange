package travel.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URI;

public class Paytm extends JFrame {

    Paytm() {

        setTitle("Pay using Paytm");
        setLayout(null);
        setBounds(600, 220, 800, 600);

        // Heading
        JLabel heading = new JLabel("Pay using Paytm");
        heading.setFont(new Font("Raleway", Font.BOLD, 40));
        heading.setBounds(50, 30, 400, 50);
        add(heading);

        // Paytm image
        ImageIcon i1 = new ImageIcon(
            ClassLoader.getSystemResource(
                "travel/management/system/icons/paytm.jpeg"
            )
        );

        Image i2 = i1.getImage().getScaledInstance(
            500, 350, Image.SCALE_SMOOTH
        );

        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(120, 120, 500, 350);
        add(image);

        // Pay button
        JButton pay = new JButton("Pay");
        pay.setBounds(620, 30, 80, 40);

        pay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    Desktop.getDesktop().browse(
                        new URI("https://paytm.com/")
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Unable to open Paytm website."
                    );
                }
            }
        });

        add(pay);

        // Back button
        JButton back = new JButton("Back");
        back.setBounds(620, 80, 80, 40);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        add(back);

        getContentPane().setBackground(Color.WHITE);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Paytm();
    }
}