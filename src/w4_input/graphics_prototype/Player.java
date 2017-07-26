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

    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;
    private Color color;
    private int xMovmentImput;
    private int yMovmentImput;
    private boolean shiftPressed;
    //private boolean Freezed;
    private boolean Alive;
    private float xSpeed;
    private float ySpeed;
    private float f = (float) 0.1;
    private int Ft = 2;
    private int xMoveTo;
    private int yMoveTo;
    private boolean DoMoveTo;
    private float HP;
    private float MaxHP;
    private float DMGTaken;

    public Player(int xPosition, int yPosition, int xSize, int ySize, Color color, float MaxHP,boolean  Alive) {
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
    
    public void TakeDMG(float DMGTaken){
    this.DMGTaken=DMGTaken;
    }

    public void Render(Graphics g,int baseRenderPointX, int baseRenderPointY) {
        xPosition+=baseRenderPointX;
        yPosition+=baseRenderPointY;
        g.setColor(color);
        g.fillRect(xPosition, yPosition, xSize, ySize);
        xPosition-=baseRenderPointX;
        yPosition-=baseRenderPointY;
        
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
    
    
    
    public void MoveInput(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed, boolean  shiftPressed) {

        if (upPressed) {
            yMovmentImput = -Ft;
        }
        if (downPressed) {
            yMovmentImput = Ft;
        }
        if (!downPressed && !upPressed) {
            yMovmentImput = 0;
        }
        if (rightPressed) {
            xMovmentImput = Ft;
        }
        if (leftPressed) {
            xMovmentImput = -Ft;
        }
        if (!rightPressed && !leftPressed) {
            xMovmentImput = 0;
        }
        if(shiftPressed)
        {
        this.shiftPressed = true;
        }
        if(!shiftPressed)
        {
        this.shiftPressed = false;
        }
        
    }

    public void update() {
       
        if(Alive){
        
            
            HP-=DMGTaken;
            DMGTaken = 0;
        if(HP<=0){
        Alive = false;
        
        }
            
            
            int modifier = 10;
        if(shiftPressed){
        modifier /= 2;
        }
        
        
        /*if(DoMoveTo){
        
        }else*/
        xSpeed =xMovmentImput*modifier;
        xSpeed = xSpeed*f;
        ySpeed =yMovmentImput*modifier;
        ySpeed = ySpeed*f;
        xPosition+=xSpeed;
        yPosition+=ySpeed;
      /*  System.out.println(xSpeed);
        System.out.println(ySpeed); */
        }
    }
    
}
