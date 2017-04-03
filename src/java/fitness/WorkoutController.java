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

/**
 *
 * @author c0659824
 */
public class WorkoutController {
    private List<Workouts> workouts = new ArrayList<>();
    private Workouts thisWorkout = new Workouts();
    
    public WorkoutController(){
        getWorkoutsFromDB();
    }
    
    private void getWorkoutsFromDB() {
        try (Connection conn = DBUtils.getConnection()) {
            workouts = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM workouts");
            while (rs.next()) {
                Workouts w = new Workouts(rs.getInt("id"),
                        rs.getString("workoutName"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getInt("userId"));
                workouts.add(w);
            }

        } catch (SQLException ex) {
            Logger.getLogger(WorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            workouts = new ArrayList<>();
        }
    }
    
    public List<Workouts> getWorkouts(){
        return workouts;
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
                pstmt.setString(1, thisWorkout.getName());
                pstmt.setString(2, thisWorkout.getCategory());
                pstmt.setString(3, thisWorkout.getDescription());
                pstmt.setInt(4, thisWorkout.getUserId());
                pstmt.setInt(5, thisWorkout.getId());
                System.out.println("Update: " + pstmt.executeUpdate());
            } else {
                String sql = "INSERT INTO posts (workoutName, category, descrption, userId) VALUES (?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, thisWorkout.getName());
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
        thisWorkout = getWorkoutByTitle(thisWorkout.getName());
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
            if (w.getName().equals(name)) {
                return w;
            }
        }
        return null;
    }
}
