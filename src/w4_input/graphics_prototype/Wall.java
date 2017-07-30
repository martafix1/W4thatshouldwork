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
    
    private boolean[] activeSides = {false,false,false,false};
    private Color TouchColor;
    private Color MainColor;
    private BufferedImage texture;
    public Wall(int xPosition, int yPosition, int xSize, int ySize,Color MainColor,Color TouchColor) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.MainColor = MainColor;
        this.TouchColor = TouchColor;
        
        
    }
    
    
     
    @Override
    public int getxPosition() {
        return xPosition;
    }

    @Override
    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
        
    }

    @Override
    public int getyPosition() {
        return yPosition;
    }

    @Override
    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public int getxSize() {
        return xSize;
    }

    @Override
    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    @Override
    public int getySize() {
        return ySize;
    }

    @Override
    public void setySize(int ySize) {
        this.ySize = ySize;
    }

   

    public boolean[] getActiveSides() {
        return activeSides;
    }

    public void setActiveSides(boolean[] activeSides) {
        this.activeSides = activeSides;
    }
    
    public void loadTexture(){
    try{
         texture = ImageIO.read(new File("brickwall.jpg"));
         } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Render.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void Render(Graphics g, int baseRenderPointX, int baseRenderPointY){
    xPosition+=baseRenderPointX;
        yPosition+=baseRenderPointY;
        
        g.setColor(MainColor);
        g.fillRect(xPosition, yPosition, xSize, ySize);
        try{
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setPaint(new TexturePaint(texture, new Rectangle(0, 0, 64, 64)));
        g2d.fillRect(xPosition, yPosition, xSize, ySize);
        g2d.dispose();}catch(Exception ex){
        
        }
        /*g.setColor(TouchColor);
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
        }*/
    
    xPosition-=baseRenderPointX;
    yPosition-=baseRenderPointY;
    
    }
    
    
    
    

}
