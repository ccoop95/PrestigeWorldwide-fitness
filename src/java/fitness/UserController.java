/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author c0659824
 */
@Named
@ApplicationScoped
public class UserController {
    private List<Users> users = new ArrayList<>();
    private Users thisUser = new Users();
    
    public UserController() {
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                Users u = new Users();
                u.setId(rs.getInt("UserId"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setEmail(rs.getString("Email"));
                u.setName(rs.getString("Name"));
                u.setHeight(rs.getDouble("Height"));
                u.setWeight(rs.getDouble("Weight"));
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
