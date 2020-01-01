package core.entity;

import java.awt.*;

public abstract class Entity {
    public float
            x,y,
            width,height;

    private Rectangle
            EntityBounds;
    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        EntityBounds = new Rectangle(0,0, (int)width, (int)height);
    }
    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public abstract void render();
    public abstract void tick();
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Rectangle getEntityBounds() {
        return EntityBounds;
    }

    public void setEntityBounds(Rectangle entityBounds) {
        EntityBounds = entityBounds;
    }
}
