package core.gfx;

import core.Engine;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class EngineGraphics {
    private Graphics
            GFX;

    private BufferStrategy
            BS;

    private int
            buffers = 3;

    private Engine
            Engine;

    private boolean
            isNoFill;

    private Color
            lastColor;

    public EngineGraphics(Engine Engine){
        this.Engine = Engine;
    }
    public int getNumBuffers() {
        return buffers;
    }
    public Graphics getGFX() {
        return GFX;
    }
    public BufferStrategy getBufferStrategy() {
        return BS;
    }
    public Engine getEngine() {
        return Engine;
    }
    public void setGFX(Graphics graphics) {
        GFX = graphics;
    }
    public void setBufferStrategy(BufferStrategy bufferStrategy) {
        BS = bufferStrategy;
    }
    public Color getLastColor() {
        return lastColor;
    }
    public void setLastColor(Color c) {
        lastColor = c;
    }
    public void fill(Color c) {
        lastColor = c;
    }
    public void fill(int r, int g, int b) {
        lastColor = Color.getHSBColor(r,g,b);
    }

    public boolean getNoFill() {
        return isNoFill;
    }
    public void noFill() {
        isNoFill = true;
    }

    public void setNumBuffers(int buffers) {
        this.buffers = buffers;
    }

}
