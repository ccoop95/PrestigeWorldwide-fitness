/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitness;

/**
 *
 * @author c0659824
 */
public class WorkoutMovements {
    int movementId;
    int workoutId;
    int wSets;
    int wReps;
    double weight;

    public WorkoutMovements() {
    }

    public WorkoutMovements(int movementId, int workoutId, int wSets, int wReps, double weight) {
        this.movementId = movementId;
        this.workoutId = workoutId;
        this.wSets = wSets;
        this.wReps = wReps;
        this.weight = weight;
    }

    public int getMovementId() {
        return movementId;
    }

    public void setMovementId(int movementId) {
        this.movementId = movementId;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getwSets() {
        return wSets;
    }

    public void setwSets(int wSets) {
        this.wSets = wSets;
    }

    public int getwReps() {
        return wReps;
    }

    public void setwReps(int wReps) {
        this.wReps = wReps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    
}
