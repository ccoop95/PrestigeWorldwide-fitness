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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author c0659824
 */
@Named
@ApplicationScoped
public class WorkoutMovementController {
    private List<WorkoutMovements> workoutMovements = new ArrayList<>();
    private WorkoutMovements thisWorkoutMovement = new WorkoutMovements();
    
    public WorkoutMovementController(){
        getWorkoutMovementsFromDB();
    }
    
    private void getWorkoutMovementsFromDB() {
        try (Connection conn = DBUtils.getConnection()) {
            workoutMovements = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM workoutMovements");
            while (rs.next()) {
                WorkoutMovements w = new WorkoutMovements();
                w.setMovementId(rs.getInt("movementId"));
                w.setSets(rs.getInt("wSets"));
                w.setReps(rs.getInt("wReps"));
                w.setWeight(rs.getDouble("weight"));
                w.setWorkoutId(rs.getInt("workoutId"));
                workoutMovements.add(w);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkoutMovementController.class.getName()).log(Level.SEVERE, null, ex);
            workoutMovements = new ArrayList<>();
        }
    }
    public List<WorkoutMovements> getWorkoutMovements() {
        return workoutMovements;
    }
    
    public WorkoutMovements getWorkoutMovementsByWorkoutId(int id){
        try (Connection conn = DBUtils.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM workoutMovements WHERE workoutId = " + id);
            while (rs.next()) {
                WorkoutMovements wm = new WorkoutMovements();
                wm.setMovementId(rs.getInt("movementId"));
                wm.setSets(rs.getInt("reps"));
                wm.setReps(rs.getInt("sets"));
                wm.setWeight(rs.getDouble("weight"));
                wm.setWorkoutId(rs.getInt("workoutId"));                
                thisWorkoutMovement = wm;
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkoutMovementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return thisWorkoutMovement;
    }

    public WorkoutMovements getThisWorkoutMovement() {
        return thisWorkoutMovement;
    }

    public void setThisWorkoutMovement(WorkoutMovements thisWorkoutMovement) {
        this.thisWorkoutMovement = thisWorkoutMovement;
    }
    
    
}
