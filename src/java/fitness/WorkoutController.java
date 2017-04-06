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
    public Workouts getCurrentWorkout(){
        return thisWorkout;
    }
    public void setCurrentWorkout(Workouts currentWorkout){
        this.thisWorkout = currentWorkout;
    }
    
    public String viewWorkout(Workouts workout) {
        thisWorkout = workout;
        return "viewWorkout";
    }
    
    public String addWorkout() {
        thisWorkout = new Workouts(-1, "", "", "", -1);
        return "editWorkot";
    }
    
    public String editWorkout(){
        return "editPost";
    }
    
    public String cancelWorkout(){
        int id = thisWorkout.getId();
        getWorkoutsFromDB();
        thisWorkout = getWorkoutById(id);
        return "viewPost";
    }
        
    public String saveWorkout(Users user) {
        try (Connection conn = DBUtils.getConnection()) {
            // If there's a current post, update rather than insert
            if (thisWorkout.getId() >= 0) {
                String sql = "UPDATE workouts SET workoutName = ?, category = ?, description = ?, userId= ?, where id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, thisWorkout.getWorkoutName());
                pstmt.setString(2, thisWorkout.getCategory());
                pstmt.setString(3, thisWorkout.getDescription());
                pstmt.setInt(4, thisWorkout.getUserId());
                pstmt.setInt(5, thisWorkout.getId());
                System.out.println("Update: " + pstmt.executeUpdate());
            } else {
                String sql = "INSERT INTO posts (workoutName, category, descrption, userId) VALUES (?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, thisWorkout.getWorkoutName());
                pstmt.setString(2, thisWorkout.getCategory());
                pstmt.setString(3, thisWorkout.getDescription());
                pstmt.setInt(4, user.getId());
                System.out.println("Insert: " + pstmt.executeUpdate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        getWorkoutsFromDB();
        // Update the currentPost so that its details appear after navigation
        thisWorkout = getWorkoutByTitle(thisWorkout.getWorkoutName());
        return "viewWorkout";
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
