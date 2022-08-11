package Gameplay;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_QUADS;

public class Tile {
	private int colour = 1;
	public double sizeX;

	public double getSizeX() {
		return sizeX;
	}

	public void setSizeX(double sizeX) {
		this.sizeX = sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}

	public void setSizeY(double sizeY) {
		this.sizeY = sizeY;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double sizeY;
	private double x, y;

	public Tile(double x, double y, double sizeX, double sizeY){
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void draw(){
		//black outline
		glBegin(GL_QUADS);
			glColor3d(0,0,0);
			glVertex2d(x, y);
			glVertex2d(x+sizeX, y);
			glVertex2d(x+sizeX, y+sizeY);
			glVertex2d(x, y+sizeY);
		glEnd();
		double[] colourA = getColour(colour);
		double offsetX = sizeX*0.05;
		double offsetY = sizeY*0.05;
		//actual coloured square
		glBegin(GL_QUADS);
			glColor3d(colourA[0]/255,colourA[1]/255,colourA[2]/255);
			glVertex2d(x+offsetX, y+offsetY);
			glVertex2d(x+sizeX-offsetX, y+offsetY);
			glVertex2d(x+sizeX-offsetX, y+sizeY-offsetY);
			glVertex2d(x+offsetX, y+sizeY-offsetY);
		glEnd();
	}

	public boolean isClicked(double mX, double mY){
		return x < mX && mX < x + sizeX && y < mY && mY < y + sizeY;
	}

	private double[] getColour(int colour){
		switch(colour){
			default: return (new double[]{255, 255, 255});
			case 1: return (new double[]{228, 228, 228});
			case 2: return (new double[]{136, 136, 136});
			case 3: return (new double[]{34, 34, 34});
			case 4: return (new double[]{225, 167, 209});
			case 5: return (new double[]{229, 0, 0});
			case 6: return (new double[]{229, 149, 0});
			case 7: return (new double[]{160, 106, 66});
			case 8: return (new double[]{229, 217, 0});
			case 9: return (new double[]{148, 224, 68});
			case 10: return (new double[]{2, 190, 1});
			case 11: return (new double[]{0, 211, 221});
			case 12: return (new double[]{0, 131, 199});
			case 13: return (new double[]{0, 0, 234});
			case 14: return (new double[]{207, 110, 228});
			case 15: return (new double[]{130, 0, 128});
		}
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}
}
