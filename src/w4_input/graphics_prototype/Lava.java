/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package w4_input.graphics_prototype;

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
public class Lava extends Entity{
    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;
    private float Dmg;
    private BufferedImage texture;

    public Lava(int xPosition, int yPosition, int xSize, int ySize, float Dmg) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.Dmg = Dmg;
        try{
         texture = ImageIO.read(new File("lava.png"));
         } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Render.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public float getDmg() {
        return Dmg;
    }

    public void setDmg(float Dmg) {
        this.Dmg = Dmg;
    }
    
    public void Render(Graphics g,int baseRenderPointX, int baseRenderPointY) {
       xPosition+=baseRenderPointX;
    yPosition+=baseRenderPointY;
        
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setPaint(new TexturePaint(texture, new Rectangle(0, 0, 400, 400)));
        g2d.fillRect(xPosition, yPosition, xSize, ySize);
        g2d.dispose();
        xPosition-=baseRenderPointX;
    yPosition-=baseRenderPointY;
        
    }
    
}
