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
public class Switch extends Entity{

    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;
    private Color color;
    private boolean Activated = false;
    private boolean Activation = false;
    private boolean Reset = false;
    private int activationTime;
    private int maxActivationTime;
    private short activationType;
    public static final short TOUCH = 1;
    public static final short FULL = 2;
    private Engine Slave;
    
public Switch(int xPosition, int yPosition, int xSize, int ySize, int activationTime, Engine Slave, Color color,short activationType) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.activationTime = activationTime;
        this.maxActivationTime = activationTime;
        this.Slave = Slave;
        this.color =  color;
        this.activationType = activationType;
}

    public void activate(){
    Slave.setActivated(true);
        System.out.println("ACTIVATE");
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

    public boolean isActivation() {
        return Activation;
    }

    public void setActivation(boolean Activation) {
        this.Activation = Activation;
    }

    public int getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(int activationTime) {
        this.activationTime = activationTime;
    }

    public int getMaxActivationTime() {
        return maxActivationTime;
    }

    public void setMaxActivationTime(int maxActivationTime) {
        this.maxActivationTime = maxActivationTime;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public short getActivationType() {
        return activationType;
    }

    public void setActivationType(short activationType) {
        this.activationType = activationType;
    }

    public Entity getSlave() {
        return Slave;
    }

    public void setSlave(Engine Slave) {
        this.Slave = Slave;
    }

    
    public void reset(){
    Reset = true;
    Activation = true;
    Activated = false;
    activationTime += 3;
    }
    
    
    public void Render(Graphics g,int baseRenderPointX,int baseRenderPointY) {
        xPosition += baseRenderPointX;
        yPosition += baseRenderPointY;
        
        g.setColor(color);
        g.fillRect(xPosition, yPosition, xSize, ySize);
        g.setColor(Color.GRAY);
        g.drawRect(xPosition+2, yPosition+2, xSize-5, ySize-5);
        g.setColor(Color.BLACK);
        g.fillRect(xPosition+3, yPosition+3, xSize-6, ySize-6);
        
        g.setColor(Color.pink);
        g.fillRect((xPosition+3)+(int)(((float)xSize/2)*((float)activationTime/(float)maxActivationTime)),(yPosition+3)+(int)(((float)ySize/2)*((float)activationTime/(float)maxActivationTime)),(xSize-6)-(int)((float)(xSize-6)*(float)activationTime/(float)maxActivationTime),(ySize-6)-(int)((float)(ySize-6)*(float)activationTime/(float)maxActivationTime));
        
        xPosition -= baseRenderPointX;
        yPosition -= baseRenderPointY;
        
        
        
    }
    

    public void update() {
       
        if(Activation){
        
        color = Color.orange;
        if(activationTime>0){
        activationTime--;
        }
        
        }
        if(activationTime==0){
        color = Color.GREEN;
        Activated = true;
        Activation =false;
        activationTime --;
        activate();
        } 
        if(Reset)
        {
        activationTime+=2;
        if(activationTime>=maxActivationTime){
        activationTime=maxActivationTime;
        Activation=false;
        Reset=false;
        color = Color.RED;
        }
        
        }
        
        
    }
    


}
