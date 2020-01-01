package core;

import core.display.EngineFrame;
import core.geometry.Vector;
import core.gfx.EngineGraphics;
import core.input.KeyManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public abstract class Engine implements EngineConstants, Runnable {
    private boolean
        isRunning;

    private Thread
        EngineThread;

    private EngineFrame
        Display;

    public Handler
        EngineHandler;
    private EngineGraphics
        EngineGFX;
    private KeyManager
        InputManager;
    public float
        deltaTime;

    public int
        width,
        height,
        frameCount;
    float
        frameRate = DEFAULT_FPS;
    String
        frameTitle = "";

    public Engine(String FRAME_TITLE, int FRAME_WIDTH, int FRAME_HEIGHT) {
        this.width = FRAME_WIDTH;
        this.height = FRAME_HEIGHT;
        this.frameTitle = FRAME_TITLE;
        initEngine();
    }

    private void initEngine () {
        Display = new EngineFrame(frameTitle, width, height);
        InputManager = new KeyManager();

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
    private void EngineTick() {
        OnTick();
    }
    private void EngineRender() {
        EngineGFX.setBufferStrategy(EngineGFX.getEngine().getDisplay().getCanvas().getBufferStrategy());

        if (EngineGFX.getBufferStrategy() == null) {
            EngineGFX.getEngine().getDisplay().getCanvas().createBufferStrategy(EngineGFX.getNumBuffers());
            return;
        }
        EngineGFX.setGFX(EngineGFX.getBufferStrategy().getDrawGraphics());

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

    public void frameRate(float fps) {
        frameRate += fps <= MAX_FPS ? fps : MAX_FPS - frameRate;
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
    public void background(Color c) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().setColor(c);
            EngineGFX.getGFX().fillRect(0, 0, this.width, this.height);
        }
    }
    public void background(int r, int g, int b) {
        if (EngineGFX.getGFX() != null) {
            EngineGFX.getGFX().setColor(Color.getHSBColor(r, g, b));
            EngineGFX.getGFX().fillRect(0, 0, this.width, this.height);
        }
    }
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

    public EngineFrame getDisplay() {
        return Display;
    }
    public Handler getHandler() {
        return EngineHandler;
    }
    public KeyManager getInputManager() {
        return InputManager;
    }

    public boolean getKey(int keyCode) {
        return InputManager.Keys[keyCode];
    }

    //Vector functions
    public Vector VectorSub(Vector vector1, Vector vector2) {
        return new Vector(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY(), vector1.getZ() - vector2.getZ());
    }

    public Vector VectorAdd(Vector vector1, Vector vector2) {
        return new Vector(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY(), vector1.getZ() + vector2.getZ());
    }

    public Vector VectorMultiply(Vector vector1, float k) {

        return new Vector(vector1.getX() * k, vector1.getY() * k, vector1.getZ() * k);
    }

    public Vector VectorDivide(Vector vector1, float k) {
        return new Vector(vector1.getX() / k, vector1.getY() / k, vector1.getZ() / k);
    }

    public Vector VectorNormalize(Vector vector1) {
        float length = VectorLength(vector1);
        return new Vector(vector1.getX() / length, vector1.getY() / length, vector1.getZ() / length);
    }
    public Vector CrossProduct(Vector vector1, Vector vector2) {
        Vector tempVector = new Vector(0,0,0);
        tempVector.setX(vector1.getY() * vector2.getZ() - vector1.getZ() * vector2.getY());
        tempVector.setY(vector1.getZ() * vector2.getX() - vector1.getX() * vector2.getZ());
        tempVector.setZ(vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX());
        return tempVector;
    }

    public Vector IntersectionPlane(Vector plane_p, Vector plane_n, Vector lineStart, Vector lineEnd) {
        plane_n = VectorNormalize(plane_n);

        float plane_d = -VectorDotProduct(plane_n, plane_p);
        float ad = VectorDotProduct(lineStart, plane_n);
        float bd = VectorDotProduct(lineEnd, plane_n);
        float t = (-plane_d - ad) / (bd - ad);

        Vector lineStartToEnd = VectorSub(lineEnd, lineStart);
        Vector lineToIntersect = VectorMultiply(lineStartToEnd, t);
        return VectorAdd(lineStart, lineToIntersect);
    }
    public float VectorDotProduct(Vector vector1, Vector vector2) {
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY() + vector1.getZ() * vector2.getZ();
    }
    public float VectorLength(Vector vector1) {
        return (float)(Math.sqrt(VectorDotProduct(vector1, vector1)));
    }
    public float dist(Vector vector1, Vector plane_n, Vector plane_p) {
        vector1 = VectorNormalize(vector1);
        return (plane_n.getX() * vector1.getX() + plane_n.getY() * vector1.getY() + plane_n.getZ() * vector1.getZ() - VectorDotProduct(plane_n, plane_p));
    }
}
