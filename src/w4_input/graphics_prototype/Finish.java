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
public class Finish extends Entity{
    
    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;
    private Color color;
    private Color scndcolor;
    private float xSpeed;
    private float ySpeed;


    public Finish(int xPosition, int yPosition, int xSize, int ySize, Color color,Color scndcolor) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.color = color;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.scndcolor = scndcolor;
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

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getScndcolor() {
        return scndcolor;
    }

    public void setScndcolor(Color scndcolor) {
        this.scndcolor = scndcolor;
    }
    
    public void Render(Graphics g) {
        
        g.setColor(scndcolor);
        g.drawRect(xPosition, yPosition, xSize, ySize);
        g.drawRect(xPosition+1, yPosition+1, xSize-2, ySize-2);
        g.drawRect(xPosition+2, yPosition+2, xSize-4, ySize-4);
        g.setColor(color);
        g.fillRect(xPosition+3, yPosition+3, xSize-6, ySize-6);
        

        
        
    }

    

    public void update() {
       
             
        
        /*if(DoMoveTo){
        
        }else*/{
        xPosition+=xSpeed;
        yPosition+=ySpeed;
      /*  System.out.println(xSpeed);
        System.out.println(ySpeed); */
        }
    }

}
