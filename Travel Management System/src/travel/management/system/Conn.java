/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travel.management.system;

import java.sql.*;

public class Conn {
    Connection c;
    Statement s;

    public Conn() {
        try {
            // Updated driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Recommended full connection string
            c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tms", // host + port + db
                    "root",
                    "Sp143200@"
            );

            s = c.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

