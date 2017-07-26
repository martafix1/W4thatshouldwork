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
    protected int EstaminatedTicks = 60;
    protected int EstaminatedFPS = 120;
    protected int ScreenWidth = 800;
    protected int ScreenHeight = 650;
    protected int baseRenderPointX = 0;
    protected int baseRenderPointY = 0;
    public static final int left = 0;
    public static final int top = 1;
    public static final int right = 2;
    public static final int down = 3;
    protected int level = 1;
    MouseInput mouse;
    // Keyboard polling
    KeyboardInput keyboard;
    protected Player player = new Player(100, 100, 0, 0, Color.YELLOW, 100, false);
    
    protected Finish finish ;
    protected Spawner spawner ;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean shiftPressed = false;
    private Color wallcolor = Color.WHITE;
    private Color walltouchcolor = Color.RED;
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    private ArrayList<Lava> lavas = new ArrayList<Lava>();
    private ArrayList<Switch> switches = new ArrayList<Switch>();
    private ArrayList<Engine> engines = new ArrayList<Engine>();
    File f = new File("wallz.data");
    boolean v = true;
    File g = new File("text.txt");

    public Render() {
        super();
        readText(g);
        this.isRunning = false;
        this.paused = false;
        this.setSize(new Dimension(ScreenWidth, ScreenHeight));
        finish = new Finish(350, 350, 50, 50, 300);
        spawner = new Spawner(100, 100, 35, 34, 120);
        this.lavas.add(new Lava(500, 400, 100, 100, 6));
        this.walls.add(new Wall(315, 325, 10, 100, wallcolor, walltouchcolor));
        this.walls.add(new Wall(425, 325, 10, 100, wallcolor, walltouchcolor));
        this.walls.add(new Wall(325, 315, 100, 10, wallcolor, walltouchcolor));
        this.walls.add(new Wall(325, 425, 100, 10, wallcolor, walltouchcolor));
        this.engines.add(new Engine(315,300, walls.get(0),2));
        this.switches.add(new Switch(150,400,30,30, 120, engines.get(0), Color.CYAN,Switch.TOUCH));
        
        //outputWallstoFile(f);
        //walls = null;
        spawner.setActivation(true);

        //walls.get(0).loadTexture();
    }

    public void readText(File g) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(g));
            line = br.readLine();
            while (line != null) {

                String[] text = line.split(":");
                ArrayList<String> l = new ArrayList<>(Arrays.asList(text));
                if ("framerate".equals(l.get(0))) {
                    EstaminatedFPS = Integer.parseInt(l.get(1));
                    System.out.println(EstaminatedFPS);
                }
                if ("width".equals(l.get(0))) {
                    ScreenWidth = Integer.parseInt(l.get(1));
                    System.out.println(ScreenWidth);
                }
                if ("height".equals(l.get(0))) {
                    ScreenHeight = Integer.parseInt(l.get(1));
                    System.out.println(ScreenHeight);
                }

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
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            walls = (ArrayList<Wall>) ois.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void outputWallstoFile(File f) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(walls);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
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

    public void spawn(Spawner spawner) {
        player.setxPosition(spawner.getxPosition());
        player.setyPosition(spawner.getyPosition());
        player.setxSize(spawner.getxSize());
        player.setySize(spawner.getySize());
        player.setMaxHP(100);
        player.setHP(player.getMaxHP());
        player.setColor(Color.YELLOW);
        player.setAlive(true);

    }
    

    
    public boolean playerColisionFull(Entity finish) {
        if (player.getxPosition() > finish.getxPosition() && player.getxPosition() + player.getxSize() < finish.getxPosition() + finish.getxSize()) {
            if (player.getyPosition() > finish.getyPosition() && player.getyPosition() + player.getySize() < finish.getyPosition() + finish.getySize()) {
                return true;
            }
        }
        return false;
    }

    public boolean playerColisionTouch(Entity wall) {

        Point PlayerCenter = new Point(player.getxPosition() + (player.getxSize() / 2), player.getyPosition() + (player.getySize() / 2));

        if (player.getxPosition() + player.getxSize() > wall.getxPosition() && player.getxPosition() < wall.getxPosition() + wall.getxSize() && player.getyPosition() + player.getySize() > wall.getyPosition() && player.getyPosition() < wall.getyPosition() + wall.getySize()) {
            {
                return true;
            }
        }

        return false;
    }

    public int getPenX(Entity wall) {
        int Tx = (wall.getxPosition() + wall.getxSize() / 2) - (player.getxPosition() + player.getxSize() / 2);
        int PenX = (player.getxSize() / 2) + (wall.getxSize() / 2) - Math.abs(Tx);
        if (Tx > 0) {
            PenX += 1;
        }
        return PenX;
    }

    public int getPenY(Entity wall) {
        int Ty = (wall.getyPosition() + wall.getySize() / 2) - (player.getyPosition() + player.getySize() / 2);
        int PenY = (player.getySize() / 2) + (wall.getySize() / 2) - Math.abs(Ty);
        if (Ty > 0) {
            PenY += 1;
        }
        return PenY;
    }

    public float playerOverlap(Entity wall) {
        float PenX = getPenX(wall);
        float PenY = getPenY(wall);
        if (PenX < 0 || PenY < 0) {
            return 0;
        }

        if (PenX > player.getxSize()) {
            PenX = player.getxSize();
        }
        if (PenY > player.getySize()) {
            PenY = player.getySize();
        }
        float OverlapX = PenX / player.getxSize();
        float OverlapY = PenY / player.getySize();

        return OverlapX * OverlapY;

    }

    public boolean[] CollisonBlock(Entity wall) {

        int Tx = (wall.getxPosition() + wall.getxSize() / 2) - (player.getxPosition() + player.getxSize() / 2);
        int Ty = (wall.getyPosition() + wall.getySize() / 2) - (player.getyPosition() + player.getySize() / 2);
        boolean[] activeSides = {false, false, false, false};
        int PenX = (player.getxSize() / 2) + (wall.getxSize() / 2) - Math.abs(Tx);
        int PenY = (player.getySize() / 2) + (wall.getySize() / 2) - Math.abs(Ty);

        if (PenX <= PenY) {

            if (Tx < 0) {
                player.setxPosition(player.getxPosition() + PenX);
                activeSides[right] = true;
            }
            if (Tx > 0) {
                player.setxPosition(player.getxPosition() - PenX - 1);
                activeSides[left] = true;
            }
        } else {
            if (Ty < 0) {
                player.setyPosition(player.getyPosition() + PenY);
                activeSides[down] = true;
            }
            if (Ty > 0) {
                player.setyPosition(player.getyPosition() - PenY - 1);
                activeSides[top] = true;
            }

        }

        return activeSides;
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
        spawner.update();
        if (spawner.isActivated()) {
            spawn(spawner);
            spawner.setActivated(false);

        }
        if (spawner.getActivationTime() < 0 && !playerColisionTouch(spawner)) {
            spawner.reset();
        }
        if (spawner.getActivationTime() == spawner.getMaxActivationTime() && playerColisionTouch(spawner)) {
            spawner.setActivation(true);
        }

        if (!player.isAlive()) {
            if (finish.isActivation() || finish.isActivated()) {
                if(!finish.isReset())
                {finish.reset();}

            } else {
                spawner.setActivation(true);

            }
        }

        if (player.getxPosition() + baseRenderPointX < 100) {
            baseRenderPointX += (100 - (baseRenderPointX + player.getxPosition()));
        }
        if (player.getxPosition() + baseRenderPointX > this.getWidth() - 100) {
            baseRenderPointX -= ((baseRenderPointX + player.getxPosition()) - (this.getWidth() - 100));
        }

        if (player.getyPosition() + baseRenderPointY < 100) {
            baseRenderPointY += (100 - (baseRenderPointY + player.getyPosition()));
        }
        if (player.getyPosition() + baseRenderPointY > this.getHeight() - 100) {
            baseRenderPointY -= ((baseRenderPointY + player.getyPosition()) - (this.getHeight() - 100));
        }

        if (v) {
            readWallsfromFile(f);
            v = false;
        }
        if (playerColisionTouch(finish)) {
            finish.setActivation(true);
        }
        if (playerColisionFull(finish)) {

        }
        
        for (Lava l : this.lavas) {
            if (playerColisionTouch(l)) {
                player.TakeDMG(l.getDmg() * playerOverlap(l));

            }
        }
        for (Switch s : this.switches) {
            if(s.getActivationType() == s.TOUCH){
                if(playerColisionTouch(s)){
                s.setActivation(true);
                    System.out.println("TOUCH");
                }}
            if(s.getActivationType() == s.FULL){
                if(playerColisionFull(s)){
                s.setActivation(true);
                }
            }
            s.update();
        }
        for (Engine e : this.engines) {
            e.update();
            
        }
        for (Wall w : this.walls) {
            boolean[] activeSides = {false, false, false, false};
            if (playerColisionTouch(w)) {

                activeSides = CollisonBlock(w);
                w.setActiveSides(activeSides);
            } else {
                w.setActiveSides(activeSides);
            }
            
        }
    }

    private void render() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = buffer.getDrawGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), 100);
        g.setColor(Color.orange);
        g.drawString("FPS: " + runtimeFPS, 20, 20);
        g.drawString("FPS realtime calculation: " + FPS, 20, 40);
        g.drawString("Player X" + player.getxPosition() + " Y" + player.getyPosition() + " HP" + player.getHP() + "/" + player.getMaxHP(), 20, 60);
        g.drawString("Player-Lava PenX" + getPenX(lavas.get(0)) + " PenY" + getPenY(lavas.get(0)) + "|Overlap" + (float) playerOverlap(lavas.get(0)), 20, 80);
        g.setColor(Color.PINK);
        g.drawRect(baseRenderPointX, baseRenderPointY, (int) this.getMinimumSize().getWidth(), (int) this.getMinimumSize().getHeight());
        g.drawRect(baseRenderPointX - 1, baseRenderPointY - 1, this.getWidth() + 2, this.getHeight() + 2);

        spawner.Render(g, baseRenderPointX, baseRenderPointY);
        finish.Render(g, baseRenderPointX, baseRenderPointY);
        for (Lava l : this.lavas) {
            l.Render(g, baseRenderPointX, baseRenderPointY);
        }
        player.Render(g, baseRenderPointX, baseRenderPointY);

        for (Wall w : this.walls) {
            w.Render(g, baseRenderPointX, baseRenderPointY);
        }
        
        for(Switch s : this.switches )
        {
            s.Render(g, baseRenderPointX, baseRenderPointY);
        }
        
        for(Engine e : this.engines )
        {
            e.Render(g, baseRenderPointX, baseRenderPointY);
        }

//g.fillPolygon(x,y,4);
        g.dispose();
        buffer.show();
    }

}
