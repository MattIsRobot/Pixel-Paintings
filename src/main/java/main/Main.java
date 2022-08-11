package main;

import Gameplay.Tile;
import engine.io.Controller;
import engine.io.Renderer;
import engine.io.Texture;
import engine.io.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main implements Runnable {
	public Thread game;
	public Window window;
	public final int WIDTH = 1080, HEIGHT = 720;
	public final String TITLE = "Pixel Paintings";

	private int gridX = 30;
	private int gridY = gridX;
	private Tile[] pixels = new Tile[gridX*gridY];
	private Tile[] pallette = new Tile[16];
	int curColour = 0;
	Texture brushIcon;


	public void start() {
		game = new Thread(this, "game");
		game.start();
	}

	public void init() {
		window = new Window(WIDTH, HEIGHT, TITLE);
		window.setBackgroundColor(34/255,34/255,34/255);
		window.init();
		Renderer.initTextures();
		brushIcon = new Texture("src/main/resources/paintbrush.png");

		double wX = window.getWidth();
		double wY = window.getHeight();
		double gSizeX = 0;
		double gSizeY = 0;
		double pSizeX = 0;
		double pSizeY = 0;
		if(wX > wY){
			gSizeY = (double) 2/3 * 2 / (double) gridY;
			gSizeX = gSizeY * wY/wX;
			pSizeY = (double) 2/12;
			pSizeX = gSizeX*gridX/16;
		}

		double gXO = 0-gSizeX*gridX/2;
		double gYO = -0.5;
		double pYO = (double) -2/3;

		for (int i = 0; i < gridY; i++) {
			for (int j = 0; j < gridX; j++) {
				pixels[i*gridY+j] = new Tile(gXO+j*gSizeX,gYO+i*gSizeY, gSizeX, gSizeY);
			}
		}

		for (int i = 0; i < 16; i++) {
			pallette[i] = new Tile(gXO+i*pSizeX,pYO, pSizeX, pSizeY);
			pallette[i].setColour(i);
		}
	}

	public void run() {
		init();
		do {
			update();
			render();
			//toggle fullscreen
			if (Controller.isKeyDown(GLFW_KEY_F11) || (Controller.isKeyDown(GLFW_KEY_LEFT_ALT) && Controller.isKeyDown(GLFW_KEY_ENTER))) window.setFullscreen(!window.isFullscreen());
		}
		while (!window.shouldClose() && !Controller.isKeyDown(GLFW_KEY_ESCAPE));
		window.destroy();
	}

	private void update() {
		//System.out.println("Updating Game!");
		window.update();

		for (int i = 0; i < pixels.length; i++) {
			pixels[i].draw();
		}
		for (int i = 0; i < pallette.length; i++) {
			pallette[i].draw();
		}

		double xB = pallette[curColour].getX();
		double yB = pallette[curColour].getY();
		double xSB = pallette[curColour].getSizeX();
		double ySB = pallette[curColour].getSizeY();
		double ratio = (double) window.getWidth()/window.getHeight();
		double bY = yB + (ySB - (xSB * ratio)) / 2;


		glColor3d(1, 1, 1);
		brushIcon.bind();
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBegin(GL_QUADS);
			glTexCoord2d(0,1);
			glVertex2d(xB, bY);

			glTexCoord2d(1,1);
			glVertex2d(xB+xSB, bY);

			glTexCoord2d(1,0);
			glVertex2d(xB+xSB, bY + xSB * ratio);

			glTexCoord2d(0,0);
			glVertex2d(xB, bY + xSB * ratio);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);

		if (Controller.isBtnDown(GLFW_MOUSE_BUTTON_LEFT)) {
			double x = (double) bounds(0, window.getWidth(), (int) Controller.getMouseX())/window.getWidth()*2-1;
			double y = (double) bounds(0, window.getHeight(), (int) Controller.getMouseY())/window.getHeight()*2-1;
			y*=-1;
			//System.out.println(x + "\t" + y);

			for (int i = 0; i < pixels.length; i++) {
				if(pixels[i].isClicked(x, y)){
					pixels[i].setColour(curColour);
					break;
				}
			}

			for (int i = 0; i < pallette.length; i++) {
				if(pallette[i].isClicked(x, y)){
					curColour = pallette[i].getColour();
					break;
				}
			}


		}
		if (Controller.isKeyDown(GLFW_KEY_C)) {
			for (int i = 0; i < pixels.length; i++) {
				pixels[i].setColour(1);
			}
		}

		//if (Controller.isBtnDown(GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Controller.getMouseX() + ", Y: " + Controller.getMouseY());
		//if (Controller.isKeyDown(GLFW_KEY_EQUAL)) Renderer.setVertices(Renderer.getVertices()+1);
		//if (Controller.isKeyDown(GLFW_KEY_MINUS)) Renderer.setVertices(Math.max((Renderer.getVertices() - 1), 3));
		//System.out.println("X: " + Controller.getMouseX() + ", Y: " + Controller.getMouseY());
	}

	private void render() {
		//System.out.println("Rendering Game!");
		window.swapBuffers();
	}

	private int bounds(int low, int high, int x) {
		if (x < low) return low;
		return Math.min(x, high);
	}

	public static void main(String[] args) {
		new Main().start();
	}
}