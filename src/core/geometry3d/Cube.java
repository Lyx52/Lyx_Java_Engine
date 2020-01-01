package core.geometry3d;

import core.geometry.Mesh;
import core.geometry.Triangle;
import core.geometry.Vector;

public class Cube extends Mesh {

    public Cube(float x, float y, float z, float width, float height, float length) {
        super(new Triangle[] {
                //X,Y,Z
                //South
                new Triangle(new Vector(x,y,z), new Vector(x,y + height,z), new Vector(x + width,y + height,z)),
                new Triangle(new Vector(x,y,z), new Vector(x + width,y + height,z), new Vector(x + width,y,z)),

                //East
                new Triangle(new Vector(x + width,y,z), new Vector(x + width,y + height,z), new Vector(x + width,y + height,z + length)),
                new Triangle(new Vector(x + width,y,z), new Vector(x + width,y + height,z + length), new Vector(x + width,y,z + length)),

                //North
                new Triangle(new Vector(x + width,y,z + length), new Vector(x + width,y + height,z + length), new Vector(x,y + height,z + length)),
                new Triangle(new Vector(x + width,y,z + length), new Vector(x,y + height,z + length), new Vector(x,y,z + length)),

                //West
                new Triangle(new Vector(x,y,z + length), new Vector(x,y + height,z + length), new Vector(x,y + height,z)),
                new Triangle(new Vector(x,y,z + length), new Vector(x,y + height,z), new Vector(x,y,z)),

                //Top
                new Triangle(new Vector(x,y + height,z), new Vector(x,y + height,z + length), new Vector(x + width,y + height,z + length)),
                new Triangle(new Vector(x,y + height,z), new Vector(x + width,y + height,z + length), new Vector(x + width,y + height,z)),

                //Bottom
                new Triangle(new Vector(x + width,y,z + length), new Vector(x,y,z + length), new Vector(x,y,z)),
                new Triangle(new Vector(x + width,y,z + length), new Vector(x,y,z), new Vector(x + width,y,z)),
        });
    }
}
