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
    private static UserController instance = new UserController();
    
    public UserController() {
        /*try {
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
        }*/
        getUsersFromDB();
        instance = this;
    }
    
    private void getUsersFromDB() {
        try (Connection conn = DBUtils.getConnection()) {
            users = new ArrayList<>();
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
            users = new ArrayList<>();
        }
    }
    
    public List<Users> getUsers() {
        return users;
    }
    
    public static UserController getInstance() {
        return instance;
    }
    public String getUsernameById(int id) {
        for (Users u : users) {
            if (u.getId() == id) {
                return u.getUsername();
            }
        }
        return null;
    }
    
    public int getUserIdByUsername(String username) {
        for (Users u : users) {
            if (u.getUsername().equals(username)) {
                return u.getId();
            }
        }
        return -1;
    }

    public void addUser(String username, String password, String email, String name, double height, double weight) {
        try (Connection conn = DBUtils.getConnection()) {
            String passhash = DBUtils.hash(password);
            String sql = "INSERT INTO users (username, passhash, email, name, height, weight) VALUES(?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, passhash);
            pstmt.setString(3, email);
            pstmt.setString(4, name);
            pstmt.setDouble(5, height);
            pstmt.setDouble(6, weight);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        getUsersFromDB();
    }
    
}
