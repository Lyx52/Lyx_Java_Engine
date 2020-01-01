package core.display;

import javax.swing.*;
import java.awt.*;

public class EngineFrame {
    protected final int
            MINIMUM_WIDTH = 512,
            MINIMUM_HEIGHT = 512;

    private JFrame frame;
    private Canvas canvas;

    private int
            width,
            height;

    private String
            frameTitle = "";
    public EngineFrame(String FRAME_TITLE, int FRAME_WIDTH, int FRAME_HEIGHT) {
        if (FRAME_HEIGHT < MINIMUM_HEIGHT || FRAME_WIDTH < MINIMUM_WIDTH) {
            this.width = MINIMUM_WIDTH;
            this.height = MINIMUM_HEIGHT;
            this.frameTitle = FRAME_TITLE;
            throw new RuntimeException("Minimum width/height size is 512");
        } else {
            this.width = FRAME_WIDTH;
            this.height = FRAME_HEIGHT;
            this.frameTitle = FRAME_TITLE;
        }

        createDisplay();
    }
    private void createDisplay() {
        Dimension resolution = new Dimension(width,height);
        frame = new JFrame(frameTitle);
        frame.setMinimumSize(resolution);
        frame.setMaximumSize(resolution);
        frame.setSize(resolution);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        canvas = new Canvas();
        canvas.setFocusable(false);
        canvas.setMaximumSize(resolution);
        canvas.setMinimumSize(resolution);
        canvas.setPreferredSize(resolution);
        frame.add(canvas);
        frame.pack();
    }

    public void setResizeable(boolean IS_RESIZEABLE) {
        frame.setResizable(IS_RESIZEABLE);
    }
    public void setTitle(String FRAME_TITLE) {
        frame.setTitle(FRAME_TITLE);
    }
    public JFrame getFrame() {
        return frame;
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}