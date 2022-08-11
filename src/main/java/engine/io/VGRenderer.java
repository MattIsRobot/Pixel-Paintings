package engine.io;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;

public class VGRenderer extends Renderer {
	private Shape shape;

	public VGRenderer(Shape shape, double x, double y){
		super(x, y);

	}

	@Override
	public void draw(Window window){
		double[][] points = shape.getPoints();
		glBegin(shape.getShapeType());
			for (int i = 0; i < points.length; i++) {

			}
		glEnd();
	}
}
