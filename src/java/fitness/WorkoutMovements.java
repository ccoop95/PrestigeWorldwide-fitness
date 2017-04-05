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
    int sets;
    int reps;
    double weight;
    int workoutId;

    public WorkoutMovements() {
    }

    public WorkoutMovements(int movementId, int sets, int reps, double weight, int workoutId) {
        this.movementId = movementId;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.workoutId = workoutId;
    }

    public int getMovementId() {
        return movementId;
    }

    public void setMovementId(int movementId) {
        this.movementId = movementId;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

}
