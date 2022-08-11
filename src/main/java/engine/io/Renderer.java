package engine.io;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30C.glGenFramebuffers;

public class Renderer {
	private boolean enabled = true;
	private double x;
	private double y;

	public Renderer(double x, double y) {
		this.x = x;
		this.y = y;
	}

	private static ArrayList<ArrayList<double[]>> vertices = new ArrayList<>();
	private static Texture texture;
	private static Text text;

	public static void clearVertices() {
		Renderer.vertices.clear();
		shape = 0;
	}

	public static void incrementShape() {
		Renderer.shape++;
		vertices.add(new ArrayList<>());
	}

	private static int shape = 0;

	public static void addVertex(double[] vertex) {
		if (shape >= vertices.size()) vertices.add(new ArrayList<>());
		Renderer.vertices.get(shape).add(vertex);
	}

	public static void initTextures(){
		texture = new Texture("src/main/resources/icon.png");
		text = new Text("src/main/resources/fonts/Louis_George_Cafe.ttf", 400);
	}

	public static void update() {
		//Text text = new Text("fonts/Louis_George_Cafe.ttf", 16f);
		text.drawString("hi", -1, -1, -0.75, -0.75, 255, 255, 0);

		glColor3d(255,255,255);
		texture.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
			glTexCoord2d(0,1);
			glVertex2d(-0.5, -0.5);

			glTexCoord2d(1,1);
			glVertex2d(0.5, -0.5);

			glTexCoord2d(1,0);
			glVertex2d(0.5, 0.5);

			glTexCoord2d(0,0);
			glVertex2d(-0.5, 0.5);
		glEnd();


		if (vertices.size() > 0) {
			for (int shapeNum = 0; shapeNum < vertices.size(); shapeNum++) {
				double[][] colours = new double[vertices.get(shapeNum).size()][3];

				double speed = 250;
				double animTime = (((System.currentTimeMillis() / 1000d) * speed) % (256 * 3));

				double base = 768 / (double) vertices.get(shapeNum).size() - 1;

				for (int i = 0; i < vertices.get(shapeNum).size(); i++) {
					double adjusted = (animTime + (base * i)) % 768;
					int x = (int) (adjusted / 256);
					double y = adjusted % 256;
					int x2 = x == 0 ? 2 : x - 1;
					colours[i][x] = y / 255;
					colours[i][x2] = (255 - y) / 255;
				}

				//double length = 0.5;
				//double angle;

				if (vertices.get(shapeNum).size() >= 3) {
					//draw to window
					glBegin(GL_POLYGON);
					for (int i = 0; i < vertices.get(shapeNum).size(); i++) {
						//angle = (2 * Math.PI) / vertices * i + Math.PI * 0.5;
						//glVertex2d(length * Math.cos(angle), length * Math.sin(angle));
						glColor3d(colours[i][0], colours[i][1], colours[i][2]);
						glVertex2d(vertices.get(shapeNum).get(i)[0], vertices.get(shapeNum).get(i)[1]);
					}
					glEnd();
				}
			}
		}

	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void draw(Window window) {

	}
}
