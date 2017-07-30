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
 * @author m
 */
public class Player extends Entity {

    private Color color;
    private int xMovmentImput;
    private int yMovmentImput;
    private float xSpeedRounder;
    private float ySpeedRounder;
    private float speed;
    private boolean shiftPressed;
    //private boolean Freezed;
    private boolean Alive;
    private float xSpeed;
    private float ySpeed;
    private float friction = 0.5f;
    private int xMoveTo;
    private int yMoveTo;
    private boolean DoMoveTo;
    private float HP;
    private float MaxHP;
    private float DMGTaken;
    private short DMGTakenTimeout;
    public static final short DMGTakenTimeoutMax = 120;

    public Player(int xPosition, int yPosition, int xSize, int ySize, Color color, float MaxHP, boolean Alive) {
        this.speed = 2;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.color = color;
        this.xMovmentImput = 0;
        this.yMovmentImput = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.DoMoveTo = false;
        this.MaxHP = MaxHP;
        this.HP = MaxHP;
        this.Alive = Alive;

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void TakeDMG(float DMGTaken) {
        this.DMGTaken = DMGTaken;
        DMGTakenTimeout += DMGTakenTimeoutMax *(Math.abs(DMGTaken*2)/MaxHP)*10 ;
        if(DMGTakenTimeout>DMGTakenTimeoutMax){
        DMGTakenTimeout = DMGTakenTimeoutMax;
        }
        
    }

    public float speedVectorToTime(int xDistance, int yDistance, float speed) {
        float distance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        return distance / speed;
    }

    public void Render(Graphics g, int baseRenderPointX, int baseRenderPointY) {
        xPosition += baseRenderPointX;
        yPosition += baseRenderPointY;
        g.setColor(color);
        g.fillRect(xPosition, yPosition, xSize, ySize);
        g.setColor(new Color(1f,0f,0f,(float)DMGTakenTimeout/DMGTakenTimeoutMax));
        g.fillRect(xPosition -5, yPosition-15, xSize+10, 10);
        g.setColor(new Color(0f,1f,0f,(float)DMGTakenTimeout/DMGTakenTimeoutMax));
        g.fillRect(xPosition -5, yPosition-15, (int)((xSize+10)*(HP/MaxHP)), 10);
        xPosition -= baseRenderPointX;
        yPosition -= baseRenderPointY;
        
    }

    public float getHP() {
        return HP;
    }

    public void setHP(float HP) {
        this.HP = HP;
    }

    public float getMaxHP() {
        return MaxHP;
    }

    public void setMaxHP(float MaxHP) {
        this.MaxHP = MaxHP;
    }

    public boolean isAlive() {
        return Alive;
    }

    public void setAlive(boolean Alive) {
        this.Alive = Alive;
    }

    public void MoveInput(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed, boolean shiftPressed) {

        if (upPressed) {
            yMovmentImput = -1;
        }
        if (downPressed) {
            yMovmentImput = 1;
        }
        if (!downPressed && !upPressed) {
            yMovmentImput = 0;
        }
        if (rightPressed) {
            xMovmentImput = 1;
        }
        if (leftPressed) {
            xMovmentImput = -1;
        }
        if (!rightPressed && !leftPressed) {
            xMovmentImput = 0;
        }
        if (shiftPressed) {
            this.shiftPressed = true;
        }
        if (!shiftPressed) {
            this.shiftPressed = false;
        }

    }

    public void update() {

        
        if(DMGTakenTimeout>0){
        DMGTakenTimeout--;
        }
        if (Alive) {
        float modifier = 2;
            HP -= DMGTaken;    
            DMGTaken = 0;
            if (HP <= 0) {
                Alive = false;

            }
            if (shiftPressed) {
                modifier /= 4;
            }
            
            
            xSpeed += (xMovmentImput * modifier);
            ySpeed += (yMovmentImput * modifier);
            xSpeed -= (xSpeed * friction);
            ySpeed -= (ySpeed * friction);
            if(Math.abs(xSpeed)<0.00001){xSpeed = 0f;}
            if(Math.abs(ySpeed)<0.00001){ySpeed = 0f;}
            if (Math.abs(xSpeedRounder) >= 1) {
                int i = (int) xSpeedRounder;
                xSpeedRounder -= i;
            }
            if (Math.abs(ySpeedRounder) >= 1) {
                int i = (int) ySpeedRounder;
                ySpeedRounder -= i;
            }
            
            xSpeedRounder += xSpeed;
            ySpeedRounder += ySpeed;
            //System.out.println("SpeedX"+xSpeed+" Y"+ySpeed);
            

            xPosition += (int)xSpeedRounder;
            yPosition += (int)ySpeedRounder;

            /*  System.out.println(xSpeed);
        System.out.println(ySpeed); */
        }
    }

}
