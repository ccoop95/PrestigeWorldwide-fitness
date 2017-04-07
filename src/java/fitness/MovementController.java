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
public class MovementController {
    private List<Movements> movements = new ArrayList<>();
    private Movements thisMovement = new Movements();
    
    public MovementController(){
        getMovementsFromDB();
    }
    
    private void getMovementsFromDB() {
        try (Connection conn = DBUtils.getConnection()) {
            movements = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movements");
            while (rs.next()) {
                Movements m = new Movements();
                m.setId(rs.getInt("id"));
                m.setMovementName(rs.getString("movementName"));
                movements.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovementController.class.getName()).log(Level.SEVERE, null, ex);
            movements = new ArrayList<>();
        }
    }
    public List<Movements> getMovements() {
        return movements;
    }
    
     public Movements getThisMovement() {
        return thisMovement;
    }
    
    public Movements getMovementById(int id){
        try (Connection conn = DBUtils.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movements WHERE id = " + id);
            while (rs.next()) {
                Movements m = new Movements();
                m.setId(rs.getInt("id"));
                m.setMovementName(rs.getString("movementName"));                
                thisMovement = m;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return thisMovement;
    }
    
    public int getMovementIdByName(String name){
        for (Movements w : movements) {
            if (w.getMovementName().equals(name)) {
                return w.id;
            }
        }
        return 0;
    }
    
    public void setThisMovement(Movements thisMovement) {
        this.thisMovement = thisMovement;
    }
    public String addMovement() {
        try (Connection conn = DBUtils.getConnection()) {
            String sql = "INSERT INTO movements (movementName) VALUES(?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, thisMovement.movementName);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        getMovementsFromDB();
        return "testing";
    }
}
