/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package w4_input.graphics_prototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author martin
 */
public class Wall extends Entity{
    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;
    private boolean[] activeSides = {false,false,false,false};
    private BufferedImage texture;
    private Color TouchColor;
    private Color MainColor;
    
    public Wall(int xPosition, int yPosition, int xSize, int ySize,Color MainColor,Color TouchColor) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.MainColor = MainColor;
        this.TouchColor = TouchColor;
        
        
    }
    
    
     
    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
        //System.out.println("w4_input.graphics_prototype.Wall.setxPosition()");
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

   

    public boolean[] getActiveSides() {
        return activeSides;
    }

    public void setActiveSides(boolean[] activeSides) {
        this.activeSides = activeSides;
    }
    
    /*public void loadTexture(){
    try{
         texture = ImageIO.read(new File("metalwall.png"));
         } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Render.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }*/
    
    public void Render(Graphics g, int baseRenderPointX, int baseRenderPointY){
    xPosition+=baseRenderPointX;
        yPosition+=baseRenderPointY;
        
        g.setColor(MainColor);
    g.fillRect(xPosition, yPosition, xSize, ySize);
        /*Graphics2D g2d = (Graphics2D) g.create();

        g2d.setPaint(new TexturePaint(texture, new Rectangle(0, 0, 400, 400)));
        g2d.fillRect(xPosition, yPosition, xSize, ySize);
        g2d.dispose();*/
    g.setColor(TouchColor);
    if(activeSides[0]){
    g.fillRect(xPosition, yPosition,xSize/4,ySize);
    
    }
    if(activeSides[1]){
    g.fillRect(xPosition, yPosition,xSize, (ySize/4));
    }
    if(activeSides[2]){
    g.fillRect(xPosition+xSize-(xSize/4), yPosition,(xSize/4), ySize);
    }
    if(activeSides[3]){
    g.fillRect(xPosition, yPosition+ySize-(ySize/4), xSize, (ySize/4));
    }
    
    xPosition-=baseRenderPointX;
    yPosition-=baseRenderPointY;
    
    }
    
    
    

}
