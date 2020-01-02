package core;

import core.display.EngineFrame;
import core.geometry.Vector;
import core.gfx.EngineGraphics;
import core.image.Image;
import core.input.KeyManager;
import core.input.MouseManager;
import core.state.EngineState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Engine implements EngineConstants, Runnable {
    private boolean
        isRunning;

    private Thread
        EngineThread;

    private EngineFrame
        Display;
    private EngineState
        currentState;
    public Handler
        EngineHandler;

    private EngineGraphics
        EngineGFX;

    private KeyManager
        InputManager;
    private MouseManager
        mouseManager;
    public float
        deltaTime,
        frameRate = DEFAULT_FPS;

    public int
        width,
        height,
        frameCount,
        mouseX,
        mouseY;

    String
        frameTitle;

    public Engine(String FRAME_TITLE, int FRAME_WIDTH, int FRAME_HEIGHT) {
        this.width = FRAME_WIDTH;
        this.height = FRAME_HEIGHT;
        this.frameTitle = FRAME_TITLE;
        initEngine();
    }

    private void initEngine () {
        Display = new EngineFrame(frameTitle, width, height);
        InputManager = new KeyManager();
        mouseManager = new MouseManager();

        Display.getFrame().addMouseListener(mouseManager);
        Display.getFrame().addKeyListener(InputManager);

        EngineHandler = new Handler(this);
        EngineGFX = new EngineGraphics(this);
    }

    @Override
    public void run () {
        double timePerTick = 1000000000 / frameRate;
        double delta = 0;
        long currentTime;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;
        long lastFrameTime = lastTime;
        while (isRunning) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / timePerTick;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1) {
                EngineTick();
                EngineRender();

                deltaTime = (float)(currentTime - lastFrameTime) / 1000000000;
                lastFrameTime = currentTime;
                ticks++;
                frameCount++;
                delta--;
            }
            if (timer >= 1000000000) {
                frameRate = ticks;
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }
    public void setCurrentState(EngineState state) {
        currentState = state;
    }
    private void EngineTick() {
        mouseX = MouseInfo.getPointerInfo().getLocation().x;
        mouseY = MouseInfo.getPointerInfo().getLocation().y;
        if (currentState != null)
            currentState.OnTick();
        OnTick();
    }
    private void EngineRender() {
        EngineGFX.setBufferStrategy(EngineGFX.getEngine().getDisplay().getCanvas().getBufferStrategy());

        if (EngineGFX.getBufferStrategy() == null) {
            EngineGFX.getEngine().getDisplay().getCanvas().createBufferStrategy(EngineGFX.getNumBuffers());
            return;
        }
        if (EngineGFX.getGFX() != null)
            EngineGFX.getGFX().clearRect(0,0,width,height); //REPLACES BACKGROUND FOR NOW
        EngineGFX.setGFX(EngineGFX.getBufferStrategy().getDrawGraphics());

        if (currentState != null)
            currentState.OnRender();

        OnRender();

        //Finish rendering
        EngineGFX.getBufferStrategy().show();
        EngineGFX.getGFX().dispose();
    }
    public synchronized void start () {
        if (isRunning)
            return;
        isRunning = true;
        EngineThread = new Thread(this);
        EngineThread.start();
    }
    public synchronized void stop () {
        if (!isRunning)
            return;
        isRunning = false;
        try {
            EngineThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void OnRender();
    public abstract void OnTick();



    //Engine functions
    public BufferedImage loadBufferedImage(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    public Image loadImage(String path) {
        try {
            return new Image(ImageIO.read(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    public void frameRate(float fps) {
        frameRate = Math.min(fps, MAX_FPS);
    }
    public void drawImg(Image img, int x, int y, int width, int height) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().drawImage(img.getBufferedImage(),x,y,width,height,null);
        }
    }
    public void drawImg(BufferedImage img, int x, int y, int width, int height) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().drawImage(img,x,y,width,height,null);
        }
    }
    public void triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().setColor(EngineGFX.getLastColor());
            if (EngineGFX.getNoFill()) {
                EngineGFX.getGFX().drawLine(x1, y1, x2, y2);
                EngineGFX.getGFX().drawLine(x2, y2, x3, y3);
                EngineGFX.getGFX().drawLine(x3, y3, x1, y1);
            } else {
                int[]
                        xpoints = {x1, x2, x3},
                        ypoints = {y1, y2, y3};
                EngineGFX.getGFX().fillPolygon(xpoints, ypoints, 3);
            }
        }
    }
    public void line(int x1, int y1, int x2, int y2) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().setColor(EngineGFX.getLastColor());
            EngineGFX.getGFX().drawLine(x1,y1,x2,y2);
        }
    }
    public void rect(int x, int y, int width, int height) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().setColor(EngineGFX.getLastColor());
            if (EngineGFX.getNoFill()) {
                EngineGFX.getGFX().drawRect(x, y, width, height);
            } else EngineGFX.getGFX().fillRect(x, y, width, height);
        }
    }
    public void circle(int x, int y, int r) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().setColor(EngineGFX.getLastColor());
            if (EngineGFX.getNoFill()) {
                EngineGFX.getGFX().drawOval(x, y, r, r);
            } else EngineGFX.getGFX().fillOval(x, y, r, r);
        }
    }
    public void ellipse(int x, int y, int height, int width) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().setColor(EngineGFX.getLastColor());
            if (EngineGFX.getNoFill()) {
                EngineGFX.getGFX().drawOval(x, y, height, width);
            } else EngineGFX.getGFX().fillOval(x, y, height, width);
        }
    }
    public void arc(int x, int y, int width, int length, int startAngle, int arcAngle) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().setColor(EngineGFX.getLastColor());
            if (EngineGFX.getNoFill()) {
                EngineGFX.getGFX().drawArc(x,y,width,length,startAngle,arcAngle);
            } else EngineGFX.getGFX().fillArc(x,y,width,length,startAngle,arcAngle);
        }
    }
    //FIX ITS NOT WORKING...
    //    public void background(Color c) {
//        if (EngineGFX.getGFX() != null) {
//            EngineGFX.getGFX().setColor(c);
//            EngineGFX.getGFX().clearRect(0, 0, this.width, this.height);
//        }
//    }
//    public void background(int r, int g, int b) {
//        if (EngineGFX.getGFX() != null) {
//            EngineGFX.getGFX().setColor(Color.getHSBColor(r, g, b));
//            EngineGFX.getGFX().clearRect(0, 0, this.width, this.height);
//        }
//    }
    public void background(BufferedImage img) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().drawImage(img, 0,0, this.width, this.height, null);
        }
    }
    public void noFill() {
        EngineGFX.noFill();
    }
    public void fill(Color c) {
        EngineGFX.setLastColor(c);
    }
    public void fill(int r, int g, int b) {
        EngineGFX.setLastColor(Color.getHSBColor(r,g,b));
    }

    //GET FUNCTIONS
    public Vector lineIntersect(Vector p0, Vector p1, Vector p2, Vector p3) {
        float
            A1 = p1.y - p0.y,
            B1 = p0.x - p1.x,
            C1 = A1 * p0.x + B1 * p0.y,
            A2 = p3.y - p2.y,
            B2 = p2.x - p3.x,
            C2 = A2 * p2.x + B2 * p2.y,
            denominator = A1 * B2 - A2 * B1;
        if(denominator == 0) {
            return null;
        }
        return new Vector((B2 * C1 - B1 * C2) / denominator, (A1 * C2 - A2 * C1) / denominator);
    }
    public Vector segmentIntersect(Vector p0, Vector p1, Vector p2, Vector p3) {
        float
            A1 = p1.y - p0.y,
            B1 = p0.x - p1.x,
            C1 = A1 * p0.x + B1 * p0.y,
            A2 = p3.y - p2.y,
            B2 = p2.x - p3.x,
            C2 = A2 * p2.x + B2 * p2.y,
            denominator = A1 * B2 - A2 * B1;

        if(denominator == 0) return null;

        float
            intersectX = (B2 * C1 - B1 * C2) / denominator,
            intersectY = (A1 * C2 - A2 * C1) / denominator,
            rx0 = (intersectX - p0.x) / (p1.x - p0.x),
            ry0 = (intersectY - p0.y) / (p1.y - p0.y),
            rx1 = (intersectX - p2.x) / (p3.x - p2.x),
            ry1 = (intersectY - p2.y) / (p3.y - p2.y);

        if(((rx0 >= 0 && rx0 <= 1) || (ry0 >= 0 && ry0 <= 1)) && ((rx1 >= 0 && rx1 <= 1) || (ry1 >= 0 && ry1 <= 1))) {
            return new Vector(intersectX, intersectY);
        } else return null;
    }
    public EngineFrame getDisplay() {
        return Display;
    }
    public Handler getHandler() {
        return EngineHandler;
    }
    public KeyManager getInputManager() {
        return InputManager;
    }
    public void setMouseLocation(Vector vector) {
        try {
            Robot r = new Robot();
            r.mouseMove((int)vector.getX(), (int)vector.getY());
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public boolean getKey(int keyCode) {
        return InputManager.Keys[keyCode];
    }
}
