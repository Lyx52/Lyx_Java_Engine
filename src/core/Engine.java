package core;

import core.display.EngineFrame;
import core.geometry.Vector;
import core.geometry3d.matrix.Mat4x4;
import core.gfx.EngineGraphics;
import core.image.Image;
import core.input.KeyManager;

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

    public Handler
        EngineHandler;

    private EngineGraphics
        EngineGFX;

    private KeyManager
        InputManager;

    public float
        deltaTime,
        frameRate = DEFAULT_FPS;

    public int
        width,
        height,
        frameCount;

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
        frameRate += fps <= MAX_FPS ? fps : MAX_FPS - frameRate;
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
    public Vector IntersectionPlane(Vector plane_p, Vector plane_n, Vector lineStart, Vector lineEnd) {
        plane_n.normalize();

        float plane_d = -plane_n.DotProduct(plane_p);
        float ad = lineStart.DotProduct(plane_n);
        float bd = lineEnd.DotProduct(plane_n);
        float t = (-plane_d - ad) / (bd - ad);

        Vector lineStartToEnd = lineEnd.sub(lineStart);
        Vector lineToIntersect = lineStartToEnd.multiply(t);
        return lineStart.add(lineToIntersect);
    }

    //Matrix functions
        static Mat4x4 MatrixIdentity() {
        Mat4x4 matrix = new Mat4x4();

        matrix.setMat(0,0,1);
        matrix.setMat(1,1,1);
        matrix.setMat(2,2,1);
        matrix.setMat(3,3,1);
        return matrix;
    }
    static Mat4x4 MatrixRotateX(float angle) {
        Mat4x4 matrix = new Mat4x4();

        matrix.setMat(0,0,1);
        matrix.setMat(1,1,(float) Math.cos(angle));
        matrix.setMat(1,2,(float) Math.sin(angle));
        matrix.setMat(2,1,(float) -Math.sin(angle));
        matrix.setMat(2,2,(float) Math.cos(angle));
        matrix.setMat(3,3,1);

        return matrix;
    }

    static Mat4x4 MatrixRotateY(float angle) {
        Mat4x4 matrix = new Mat4x4();

        matrix.setMat(0,0,(float) Math.cos(angle));
        matrix.setMat(0,2,(float) Math.sin(angle));
        matrix.setMat(2,0,(float) -Math.sin(angle));
        matrix.setMat(1,1,1);
        matrix.setMat(2,2,(float) Math.cos(angle));
        matrix.setMat(3,3,1);

        return matrix;
    }

    static Mat4x4 MatrixRotateZ(float angle) {
        Mat4x4 matrix = new Mat4x4();

        matrix.setMat(0,0,(float) Math.cos(angle));
        matrix.setMat(0,1,(float) Math.sin(angle));
        matrix.setMat(1,0,(float) -Math.sin(angle));
        matrix.setMat(1,1,(float) Math.cos(angle));
        matrix.setMat(2,2,1);
        matrix.setMat(3,3,1);

        return matrix;
    }

    static Mat4x4 MatrixTranslate(float x, float y, float z) {
        Mat4x4 matrix = new Mat4x4();

        matrix.setMat(0,0,1);
        matrix.setMat(1,1,1);
        matrix.setMat(2,2,1);
        matrix.setMat(3,3,1);

        matrix.setMat(3,0,x);
        matrix.setMat(3,1,y);
        matrix.setMat(3,2,z);

        return matrix;
    }

    static Mat4x4 MatrixProject(float fovDegrees, float aspectRatio, float far, float near) {
        float fovRad = (float) (1.0f / Math.tan(fovDegrees * 0.5f / 180.0f * Math.PI));

        Mat4x4 matrix = new Mat4x4();

        matrix.setMat(0,0,aspectRatio * fovRad);
        matrix.setMat(1,1, fovRad);
        matrix.setMat(2,2, far / (far - near));
        matrix.setMat(3,2, (-far * near) / (far - near));
        matrix.setMat(2,3, 1.0f);
        matrix.setMat(3,3, 0.0f);

        return matrix;
    }
}
