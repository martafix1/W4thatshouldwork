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
public class Wall extends Entity{
      private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;
    private Color Maincolor;

    public Wall(int xPosition, int yPosition, int xSize, int ySize, Color Maincolor) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.Maincolor = Maincolor;
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

    public Color getMaincolor() {
        return Maincolor;
    }

    public void setMaincolor(Color Maincolor) {
        this.Maincolor = Maincolor;
    }

    public void Render(Graphics g){
    g.setColor(Maincolor);
    g.fillRect(xPosition, yPosition, xSize, ySize);
    }
    
    
    

}
