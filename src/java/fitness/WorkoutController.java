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
public class WorkoutController {
    private List<Workouts> workouts = new ArrayList<>();
    private List<Workouts> myWorkouts = new ArrayList<>();
    private Workouts thisWorkout = new Workouts();
    private static WorkoutController instance = new WorkoutController();
    
    public WorkoutController(){
        getWorkoutsFromDB();
        instance = this;
    }
    
    private void getWorkoutsFromDB() {
        try (Connection conn = DBUtils.getConnection()) {
            workouts = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM workouts");
            while (rs.next()) {
                Workouts w = new Workouts();
                w.setId(rs.getInt("id"));
                w.setWorkoutName(rs.getString("workoutName"));
                w.setCategory(rs.getString("category"));
                w.setDescription(rs.getString("description"));
                w.setUserId(rs.getInt("userId"));
                workouts.add(w);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            workouts = new ArrayList<>();
        }
    }
    public List<Workouts> getWorkouts() {
        return workouts;
    }
    
    public List<Workouts> getMyWorkouts(int userId) {
        getMyWorkoutsFromDB(userId);
        return myWorkouts;
    }

    public static WorkoutController getInstance() {
        return instance;
    }
    public Workouts getThisWorkout(){
        return thisWorkout;
    }
    public void setThisWorkout(Workouts currentWorkout){
        this.thisWorkout = currentWorkout;
    }
    public void setUserId(int userId){
        thisWorkout.userId = userId;
    }
    public String viewWorkout(int id) {
        thisWorkout = getWorkoutById(id);
        return "viewWorkout";
    }
      
    public String addMovements(int id) {
        thisWorkout = getWorkoutById(id);
        return "addMovements";
    }
    
    public String cancelWorkout(){
        int id = thisWorkout.getId();
        getWorkoutsFromDB();
        thisWorkout = getWorkoutById(id);
        return "viewPost";
    }
    
    public String addWorkout() {
        try (Connection conn = DBUtils.getConnection()) {
            String sql = "INSERT INTO workouts (workoutName, category, description, userId) VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, thisWorkout.workoutName);
            pstmt.setString(2, thisWorkout.category);
            pstmt.setString(3, thisWorkout.description);
            pstmt.setInt(4, thisWorkout.userId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        getWorkoutsFromDB();
        return "testing";
    }
    
    public Workouts getWorkoutById(int id){
        for (Workouts w : workouts) {
            if (w.getId() == id) {
                return w;
            }
        }
        return null;
    }
    
    public Workouts getWorkoutByTitle(String name) {
        for (Workouts w : workouts) {
            if (w.getWorkoutName().equals(name)) {
                return w;
            }
        }
        return null;
    }
    
    private void getMyWorkoutsFromDB(int userId) {
        try (Connection conn = DBUtils.getConnection()) {
            myWorkouts = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM workouts WHERE userId = " + userId);
            while (rs.next()) {
                Workouts w = new Workouts();
                w.setId(rs.getInt("id"));
                w.setWorkoutName(rs.getString("workoutName"));
                w.setCategory(rs.getString("category"));
                w.setDescription(rs.getString("description"));
                w.setUserId(rs.getInt("userId"));
                myWorkouts.add(w);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            myWorkouts = new ArrayList<>();
        }
    }
}
