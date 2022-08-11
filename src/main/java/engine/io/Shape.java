package engine.io;

import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_POLYGON;

public class Shape {
	private int shapeType;
	private double[][] points;

	public Shape(double[][] points){
		this.points = points;
		switch (points.length){
			default: shapeType = GL_POLYGON;
			case 2: shapeType = GL_LINE;
			case 3: shapeType = GL_TRIANGLES;
			case 4: shapeType = GL_QUADS;
		}



	}

	public double[][] getPoints(){
		return points;
	}

	public int getShapeType(){
		return shapeType;
	}
}
