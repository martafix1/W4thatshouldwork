/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package w4_input.graphics_prototype;

/**
 *
 * @author m
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author m
 */
public class Render extends Canvas implements Runnable {

    private boolean isRunning;
    private boolean paused;
    protected int runtimeFPS = 0;
    protected int FPS = 0;
    protected long totalticks;
    public static final int EstaminatedTicks = 60;
    public static final int EstaminatedFPS = 120;
    public static final int False = 1;
    public static final int left = 2;
    public static final int right = 3;
    public static final int top = 4;
    public static final int down = 5;
    protected int level = 1;
    MouseInput mouse;
    // Keyboard polling
    KeyboardInput keyboard;
    protected Player player = new Player(100, 100, 35, 35, Color.yellow);
    protected Finish finish = new Finish(350, 350, 50, 50, Color.DARK_GRAY, Color.PINK);
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean shiftPressed = false;
    private Color wallcolor = Color.WHITE;
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    File f = new File("wallz.data");
    boolean v = true;
    File g = new File("text.txt");
    public Render() {
        super();
        this.isRunning = false;
        this.paused = false;
        this.setSize(new Dimension(800, 650));
        this.walls.add(new Wall(300, 300, 10, 100, wallcolor));
        outputWallstoFile(f);
        walls = null;
        readText(g);
     
    }
    public void readText(File g){
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(g));
            line = br.readLine();
           while(line != null){
               
               String[] text = line.split("c");
               ArrayList<String> l = new ArrayList<>(Arrays.asList(text));
               int x = Integer.parseInt(l.get(0));
               System.out.println(x);
               System.out.println(l.toString());
           line = br.readLine();
           }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Render.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void readWallsfromFile(File f) {
        try{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        walls = (ArrayList<Wall>) ois.readObject();
        }
        catch(FileNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    
    }
    
    public void outputWallstoFile(File f) {
        try{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(walls);
        }
        catch(FileNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    
    }
    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {

        long lastTimeCycle = System.nanoTime();
        long lastTimeOutput = System.currentTimeMillis();
        double unprocessedTicks = 0;
        double nsPerTick = Math.pow(10, 9) / EstaminatedTicks;

        int ticks = 0;

        double unprocessedFPS = 0;
        double nsPerFPS = Math.pow(10, 9) / EstaminatedFPS;

        while (this.isRunning) {

            if (keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
                this.setIsRunning(false);
                System.out.println("w4_input.graphics_prototype.Render.run(keyboard.keyDownOnce( KeyEvent.VK_ESCAPE ))");
            }
            KeyBinder();

            long nowTimeCylce = System.nanoTime();

            nsPerTick = Math.pow(10, 9) / EstaminatedTicks;
            nsPerFPS = Math.pow(10, 9) / EstaminatedFPS;
            unprocessedTicks += (nowTimeCylce - lastTimeCycle) / nsPerTick;
            unprocessedFPS += (nowTimeCylce - lastTimeCycle) / nsPerFPS;
            lastTimeCycle = nowTimeCylce;

            while (unprocessedTicks >= 1) {
                ticks++;
                this.update();
                unprocessedTicks--;

            }
            while (unprocessedFPS >= 1) {

                unprocessedFPS--;

                FPS++;

                keyboard.poll();

                mouse.poll();

                this.render();
                if (System.currentTimeMillis() - lastTimeOutput > 1000) {
                    lastTimeOutput += 1000;
                    System.out.println("Ticks: " + ticks + " , FPS: " + FPS + " , Toatal Ticks: " + totalticks);
                    runtimeFPS = FPS;
                    FPS = 0;
                    ticks = 0;

                }

            }

        }
    }

    public void start() {
        this.isRunning = true;
        Thread t = new Thread(this);
        keyboard = new KeyboardInput();
        addKeyListener(keyboard);
        this.addKeyListener(keyboard);
        
       
        
        // Add mouse listeners
        mouse = new MouseInput();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        t.start();

    }

    public boolean playerColisionFull(Entity finish) {
        if (player.getxPosition() > finish.getxPosition() && player.getxPosition() + player.getxSize() < finish.getxPosition() + finish.getxSize()) {
            if (player.getyPosition() > finish.getyPosition() && player.getyPosition() + player.getySize() < finish.getyPosition() + finish.getySize()) {
                return true;
            }
        }
        return false;
    }
    
    public int playerColisionTouch(Entity wall) {
        
        Point PlayerCenter = new Point(player.getxPosition() + (player.getxSize() / 2), player.getyPosition() + (player.getySize() / 2));
        
        if(player.getxPosition()+player.getxSize()>wall.getxPosition() && player.getxPosition()< wall.getxPosition()+wall.getxSize() && player.getyPosition()+player.getySize()>wall.getyPosition() && player.getyPosition()< wall.getyPosition()+wall.getySize() )
        {
            
            
           {
             return this.left;
            }
            
            
            
            
            
            
        }
        
        
            return this.False; 
    }

    public void sleep(int time) {
        sleep(20);
    }

    private void KeyBinder(/*KeyboardInput keyboard, boolean upPressed,boolean downPressed ,boolean leftPressed, boolean rightPressed */) {
        if (upPressed == false) {
            if (keyboard.keyDown(KeyEvent.VK_W) || keyboard.keyDown(KeyEvent.VK_UP)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();keyboard.keyDown(KeyEvent.VK_W) || keyboard.keyDown(KeyEvent.VK_UP)");
                upPressed = true;
                downPressed = false;
            }
        }
        if (downPressed == false) {
            if (keyboard.keyDown(KeyEvent.VK_S) || keyboard.keyDown(KeyEvent.VK_DOWN)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();keyboard.keyDown(KeyEvent.VK_S) || keyboard.keyDown(KeyEvent.VK_DOWN)");
                downPressed = true;
                upPressed = false;
            }
        }
        if (leftPressed == false) {
            if (keyboard.keyDown(KeyEvent.VK_A) || keyboard.keyDown(KeyEvent.VK_LEFT)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();keyboard.keyDown(KeyEvent.VK_A) || keyboard.keyDown(KeyEvent.VK_LEFT)");
                leftPressed = true;
                rightPressed = false;
            }
        }
        if (rightPressed == false) {
            if (keyboard.keyDown(KeyEvent.VK_D) || keyboard.keyDown(KeyEvent.VK_RIGHT)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();keyboard.keyDown(KeyEvent.VK_D) || keyboard.keyDown(KeyEvent.VK_RIGHT)");
                rightPressed = true;
                leftPressed = false;
            }
        }
        if (shiftPressed == false) {
            if (keyboard.keyDown(KeyEvent.VK_SHIFT)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();keyboard.keyDown(KeyEvent.VK_A) || keyboard.keyDown(KeyEvent.VK_LEFT)");
                shiftPressed = true;
                
            }
        }
        
        if (upPressed == true) {
            if (!keyboard.keyDown(KeyEvent.VK_W) && !keyboard.keyDown(KeyEvent.VK_UP)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();!keyboard.keyDown(KeyEvent.VK_W) || !keyboard.keyDown(KeyEvent.VK_UP");
                upPressed = false;
            }
        }
        if (downPressed == true) {
            if (!keyboard.keyDown(KeyEvent.VK_S) && !keyboard.keyDown(KeyEvent.VK_DOWN)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();!keyboard.keyDown(KeyEvent.VK_S) || !keyboard.keyDown(KeyEvent.VK_DOWN)");
                downPressed = false;
            }
        }
        if (leftPressed == true) {
            if (!keyboard.keyDown(KeyEvent.VK_A) && !keyboard.keyDown(KeyEvent.VK_LEFT)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();!keyboard.keyDown(KeyEvent.VK_A) || !keyboard.keyDown(KeyEvent.VK_LEFT)");
                leftPressed = false;
            }
        }
        if (rightPressed == true) {
            if (!keyboard.keyDown(KeyEvent.VK_D) && !keyboard.keyDown(KeyEvent.VK_RIGHT)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();!keyboard.keyDown(KeyEvent.VK_D) || !keyboard.keyDown(KeyEvent.VK_RIGHT)");
                rightPressed = false;
            }
        }
        
        if (shiftPressed == true) {
            if (!keyboard.keyDown(KeyEvent.VK_SHIFT)) {
                //System.out.println("w4_input.graphics_prototype.Render.KeyBinder();keyboard.keyDown(KeyEvent.VK_A) || keyboard.keyDown(KeyEvent.VK_LEFT)");
                shiftPressed = false;
                
            }
        }
    }

    private void update() {
        player.MoveInput(upPressed, downPressed, leftPressed, rightPressed, shiftPressed);
        player.update();
        finish.update();
        
        if(v){readWallsfromFile(f);v=false;}
        if (playerColisionFull(finish)) {
            finish.setColor(Color.green);
            finish.setScndcolor(Color.BLACK);

        }
        for (Wall w : this.walls) {
            if(this.left==playerColisionTouch(w)){
            w.setMaincolor(Color.RED);
            }else{w.setMaincolor(Color.WHITE);}
        }
        
        
        
    }

    private void render() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = buffer.getDrawGraphics();

        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), 100);
        g.setColor(Color.orange);
        g.drawString("FPS: " + runtimeFPS, 20, 20);
        g.drawString("FPS realtime calculation: " + FPS, 20, 40);
        
        
        finish.Render(g);
        player.Render(g);
         
        for (Wall w : this.walls) {
            w.Render(g);
        }
        
        //g.fillPolygon(x,y,4);
        g.dispose();
        buffer.show();
    }

}
