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
public class Movements {
    int id;
    String movementName;

    public Movements() {
    }
  
    public Movements(int id, String name) {
        this.id = id;
        this.movementName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovementName() {
        return movementName;
    }

    public void setMovementName(String name) {
        this.movementName = name;
    }
}
