/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package w4_input.graphics_prototype;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author martin
 */
public class Engine extends Entity {

    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;
    private Color color = Color.RED;
    private boolean Activated = false;
    private boolean Running = false;
    private boolean ScndPossition = false;
    private Entity Slave;
    private float xSpeed;
    private float ySpeed;
    private float speed;
    private float xSpeedRounder;
    private float ySpeedRounder;
    private int xPositionA;
    private int yPositionA;
    private int xPositionB;
    private int yPositionB;
    

    public Engine(int xPositionB, int yPositionB, Entity Slave, float speed) {
        this.xPositionB = xPositionB;
        this.yPositionB = yPositionB;
        this.xSize = 12;
        this.ySize = 8;
        this.Slave = Slave;
        this.speed = speed;
        this.xPosition = this.xPositionA = Slave.getxPosition();
        this.yPosition = this.yPositionA = Slave.getyPosition();
        

    }

    public Engine(int xPosition, int yPosition, int xSize, int ySize, Entity Slave, float speed, int xPositionA, int yPositionA, int xPositionB, int yPositionB) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.Slave = Slave;
        this.speed = speed;
        this.xPositionA = xPositionA;
        this.yPositionA = yPositionA;
        this.xPositionB = xPositionB;
        this.yPositionB = yPositionB;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public boolean isActivated() {
        return Activated;
    }

    public void setActivated(boolean Activated) {
        this.Activated = Activated;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Entity getSlave() {
        return Slave;
    }

    public void setSlave(Entity Slave) {
        this.Slave = Slave;
    }

    public void reset() {
        Activated = false;
    }

    public float speedVectorToTime(int xDistance, int yDistance, float speed) {
        float distance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        return distance / speed;
    }

    public void Render(Graphics g, int baseRenderPointX, int baseRenderPointY) {
        xPosition += baseRenderPointX;
        yPosition += baseRenderPointY;
        Slave.setxPosition(20);
        g.setColor(Color.GRAY);
        g.drawRect(xPosition, yPosition, xSize - 1, ySize - 1);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(xPosition + 1, yPosition + 1, xSize - 3, ySize - 3);
        g.setColor(color);
        g.fillRect(xPosition + 2, yPosition + 2, xSize - 4, ySize - 4);

        xPosition -= baseRenderPointX;
        yPosition -= baseRenderPointY;

    }

    public void update() {
        Slave.setxPosition(200);
        System.out.println("Slave X"+Slave.getxPosition());
        /*
        if (Activated) {
            color = Color.orange;
            if (!ScndPossition) {
                if (!Running) {
                    int xDistance = xPositionB - Slave.getxPosition();
                    int yDistance = yPositionB - Slave.getyPosition();
                    float T = speedVectorToTime(xDistance, yDistance, speed);
                    xSpeed = xDistance / T;
                    ySpeed = yDistance / T;
                    Running = true;
                    xSpeedRounder = xSpeed;
                    ySpeedRounder = ySpeed;
                    Slave.setxPosition(Slave.getxPosition() + (int) xSpeedRounder);
                    Slave.setyPosition(Slave.getyPosition() + (int) ySpeedRounder);
                    System.out.println(!Running);
                } else {

                    
                    xSpeedRounder -= (int) xSpeed;
                    ySpeedRounder -= (int) ySpeed;
                    System.out.println(xSpeedRounder);
                    xSpeedRounder += xSpeed;
                    ySpeedRounder += ySpeed;
                    
                    Slave.setxPosition(Slave.getxPosition() + (int) xSpeedRounder);
                    Slave.setyPosition(Slave.getyPosition() + (int) ySpeedRounder);
                    if (xSpeed > ySpeed) {
                        if ((xPositionB - 2) < Slave.getxPosition() && Slave.getxPosition() < (xPositionB + 2)) {
                            Running = false;
                            Activated = false;
                            ScndPossition = true;
                            Slave.setxPosition(xPositionB);
                            Slave.setyPosition(yPositionB);
                        }
                    } else {
                        if ((yPositionB - 2) < Slave.getyPosition() && Slave.getyPosition() < (yPositionB + 2)) {
                            Running = false;
                            Activated = false;
                            ScndPossition = true;
                            Slave.setxPosition(xPositionB);
                            Slave.setyPosition(yPositionB);
                        }

                    }

                }
            } else {

                if (!Running) {
                    int xDistance = xPositionB - Slave.getxPosition();
                    int yDistance = yPositionB - Slave.getyPosition();
                    float T = speedVectorToTime(xDistance, yDistance, speed);
                    xSpeed = xDistance / T;
                    ySpeed = yDistance / T;
                    Running = true;
                    xSpeedRounder = xSpeed;
                    ySpeedRounder = ySpeed;
                    Slave.setxPosition(Slave.getxPosition() + (int) xSpeedRounder);
                    Slave.setyPosition(Slave.getyPosition() + (int) ySpeedRounder);

                } else {

                    xSpeedRounder -= (int) xSpeed;
                    ySpeedRounder -= (int) ySpeed;
                    xSpeedRounder += xSpeed;
                    ySpeedRounder += ySpeed;
                    Slave.setxPosition(Slave.getxPosition() + (int) xSpeedRounder);
                    Slave.setyPosition(Slave.getyPosition() + (int) ySpeedRounder);
                    if (xSpeed > ySpeed) {
                        if ((xPositionB - 2) < Slave.getxPosition() && Slave.getxPosition() < (xPositionB + 2)) {
                            Running = false;
                            Activated = false;
                            ScndPossition = true;
                            Slave.setxPosition(xPositionB);
                            Slave.setyPosition(yPositionB);
                        }
                    } else {
                        if ((yPositionB - 2) < Slave.getyPosition() && Slave.getyPosition() < (yPositionB + 2)) {
                            Running = false;
                            Activated = false;
                            ScndPossition = true;
                            Slave.setxPosition(xPositionB);
                            Slave.setyPosition(yPositionB);
                        }

                    }

                }

            }

        }else{
        if(ScndPossition){color = Color.GREEN;}
        if(!ScndPossition){color = Color.RED;}
            
        }
        */
    }

}
