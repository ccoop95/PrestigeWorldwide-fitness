/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitness;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author c0659824
 */
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
                Movements m = new Movements(rs.getInt("id"),
                        rs.getString("movementName"));
                movements.add(m);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MovementController.class.getName()).log(Level.SEVERE, null, ex);
            movements = new ArrayList<>();
        }
    }
}
