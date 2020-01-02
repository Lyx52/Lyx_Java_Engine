package core.gfx.cameras;

import core.Engine;
import core.geometry.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;

public class FPSCamera {

    public static final Vector
        yAxis = new Vector(0,1,0);

    private Vector
        pos,
        forward,
        up,
        centerPosition;
    public float
        mouseSensitivity = 0.5f;

    private Engine
        engine;

    public FPSCamera(Engine engine) {
        this(engine, new Vector(0,0,0), new Vector(0,0,1), new Vector(0,1,0));
    }
    public FPSCamera(Engine engine, Vector pos, Vector forward, Vector up) {
        this.engine = engine;
        this.pos = pos;
        this.forward = forward.normalize();
        this.up = up.normalize();
        this.centerPosition = new Vector(engine.width / 2, engine.height / 2);
    }
    public void input() {
        float moveAmt = 10 * engine.deltaTime;

        Vector
            deltaPos = (new Vector(engine.mouseX, engine.mouseY)).sub(centerPosition);

        if (engine.getKey(KeyEvent.VK_W))
            move(getForward(), moveAmt);
        if (engine.getKey(KeyEvent.VK_S))
            move(getForward(), -moveAmt);
        if (engine.getKey(KeyEvent.VK_A))
            move(getLeft(), moveAmt);
        if (engine.getKey(KeyEvent.VK_D))
            move(getRight(), moveAmt);

        boolean
            rotY = deltaPos.getX() != 0,
            rotX = deltaPos.getY() != 0;

        if (rotY)
            rotateY(deltaPos.getX() * mouseSensitivity);

        if (rotX)
            rotateX(deltaPos.getY() * mouseSensitivity);

        if (rotY || rotX)
            engine.setMouseLocation(centerPosition);
    }
    public void move(Vector dir, float amt) {
        pos = pos.add(dir.multiply(amt));
    }
    public void rotateX(float theta) {
        Vector
            Haxis = yAxis.cross(forward).normalize();

        forward = forward.rotate3D(theta, Haxis).normalize();
        up = forward.cross(Haxis).normalize();
    }
    public void rotateY(float theta) {
        Vector
            Haxis = yAxis.cross(forward).normalize();

        forward = forward.rotate3D(theta, Haxis).normalize();
        up = forward.cross(Haxis).normalize();
    }

    //GET
    public Vector getPos() {
        return pos;
    }
    public Vector getForward() {
        return forward;
    }
    public Vector getUp() {
        return up;
    }

    public Vector getLeft() {
        return forward.cross(up).normalize();
    }
    public Vector getRight() {
        return forward.cross(forward).normalize();
    }
    //SET
    public void setPos(Vector pos) {
        this.pos = pos;
    }
    public void setForward(Vector forward) {
        this.forward = forward;
    }
    public void setUp(Vector up) {
        this.up = up;
    }
}
