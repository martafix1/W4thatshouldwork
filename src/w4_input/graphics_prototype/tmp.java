/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package w4_input.graphics_prototype;

/**
 *
 * @author martin
 */
public class tmp {
    private Entity Slave;
    private int xSpeed;
    private int ySpeed;

    public tmp(Entity Slave, int xSpeed, int ySpeed) {
        this.Slave = Slave;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public Entity getSlave() {
        return Slave;
    }

    public void setSlave(Entity Slave) {
        this.Slave = Slave;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    
    public void move(){
    
    Slave.setxPosition(Slave.getxPosition()+xSpeed);
    Slave.setyPosition(Slave.getyPosition()+ySpeed);
    }
}
