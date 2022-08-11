package engine.io;

import org.lwjgl.opengl.GL;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.lwjgl.opengl.GL11.*;

public class Text {
	private Font font;

	public Text(String fontFile, int size) {
		this.font = loadFont(fontFile, size);
	}

	private Font loadFont(String fontFile, int size){
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, Files.newInputStream(new File(fontFile).toPath()));
			return font.deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private BufferedImage createCharImage(char c){
		BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setFont(this.font);
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		graphics2D.dispose();

		int charWidth = fontMetrics.charWidth(c);
		int charHeight = fontMetrics.getHeight();

		if (charWidth == 0) return null;

		bufferedImage = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
		graphics2D = bufferedImage.createGraphics();
		graphics2D.setFont(this.font);
		graphics2D.setPaint(Color.WHITE); //can be adjusted afterwards while drawing
		graphics2D.drawString(String.valueOf(c), 0, fontMetrics.getAscent());
		graphics2D.dispose();
		return bufferedImage;
	}

	/**
	 * draws string of text to the current window context
	 * @param text to be displayed
	 * @param x lower left x coordinate
	 * @param y lower left y coordinate
	 * @param x2 upper right x coordinate
	 * @param y2 upper right y coordinate
	 */
	public void drawString(String text, double x, double y, double x2, double y2){
		BufferedImage[] chars = new BufferedImage[text.length()];
		int width = 0;
		int height = 0;
		for (int i = 0; i < text.length(); i++) {
			chars[i] = createCharImage(text.charAt(i));
			width += chars[i].getWidth();
			height = chars[i].getHeight();
		}
		BufferedImage img=  new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = img.createGraphics();
		int cWidth = 0;
		for (BufferedImage bi:chars) {
			graphics2D.drawImage(bi, cWidth, 0, null);
			cWidth += bi.getWidth();
		}
		graphics2D.dispose();
		Texture texture = new Texture(img);
		texture.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2d(0,1);
		glVertex2d(x, y);
		glTexCoord2d(1,1);
		glVertex2d(x2, y);
		glTexCoord2d(1,0);
		glVertex2d(x2, y2);
		glTexCoord2d(0,0);
		glVertex2d(x, y2);
		glEnd();

	}

	/**
	 * draws string of text to the current window context
	 * @param text to be displayed
	 * @param x lower left x coordinate
	 * @param y lower left y coordinate
	 * @param window the current window context
	 */
	public void drawString(String text, double x, double y, Window window){
		BufferedImage[] chars = new BufferedImage[text.length()];
		int width = 0;
		int height = 0;
		for (int i = 0; i < text.length(); i++) {
			chars[i] = createCharImage(text.charAt(i));
			width += chars[i].getWidth();
			height = chars[i].getHeight();
		}
		BufferedImage img=  new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = img.createGraphics();
		int cWidth = 0;
		for (BufferedImage bi:chars) {
			graphics2D.drawImage(bi, cWidth, 0, null);
			cWidth += bi.getWidth();
		}
		graphics2D.dispose();
		Texture texture = new Texture(img);
		texture.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2d(0,1);
		glVertex2d(x, y);
		glTexCoord2d(1,1);
		glVertex2d(x+((double)img.getWidth()/(double)window.getWidth()*2-1), y);
		glTexCoord2d(1,0);
		glVertex2d(((double)img.getWidth()/(double)window.getWidth()*2-1), ((double)img.getHeight()/(double)window.getHeight()*2-1));
		glTexCoord2d(0,0);
		glVertex2d(x, ((double)img.getHeight()/(double)window.getHeight()*2-1));
		glEnd();

	}

	/**
	 * draws string of text to the current window context
	 * @param text to be displayed
	 * @param x lower left x coordinate
	 * @param y lower left y coordinate
	 * @param x2 upper right x coordinate
	 * @param y2 upper right y coordinate
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public void drawString(String text, double x, double y, double x2, double y2, double r, double g, double b){
		BufferedImage[] chars = new BufferedImage[text.length()];
		int width = 0;
		int height = 0;
		for (int i = 0; i < text.length(); i++) {
			chars[i] = createCharImage(text.charAt(i));
			width += chars[i].getWidth();
			height = chars[i].getHeight();
		}
		BufferedImage img=  new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = img.createGraphics();
		int cWidth = 0;
		for (BufferedImage bi:chars) {
			graphics2D.drawImage(bi, cWidth, 0, null);
			cWidth += bi.getWidth();
		}
		graphics2D.dispose();
		Texture texture = new Texture(img);
		texture.bind();
		glColor3d(r, g, b);

		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2d(0,1);
		glVertex2d(x, y);
		glTexCoord2d(1,1);
		glVertex2d(x2, y);
		glTexCoord2d(1,0);
		glVertex2d(x2, y2);
		glTexCoord2d(0,0);
		glVertex2d(x, y2);
		glEnd();
	}


	/**
	 * draws string of text to the current window context
	 * @param text to be displayed
	 * @param x lower left x coordinate
	 * @param y lower left y coordinate
	 * @param window the current window context
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public void drawString(String text, double x, double y, Window window, double r, double g, double b){
		BufferedImage[] chars = new BufferedImage[text.length()];
		int width = 0;
		int height = 0;
		for (int i = 0; i < text.length(); i++) {
			chars[i] = createCharImage(text.charAt(i));
			width += chars[i].getWidth();
			height = chars[i].getHeight();
		}
		BufferedImage img=  new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = img.createGraphics();
		int cWidth = 0;
		for (BufferedImage bi:chars) {
			graphics2D.drawImage(bi, cWidth, 0, null);
			cWidth += bi.getWidth();
		}
		graphics2D.dispose();
		Texture texture = new Texture(img);
		texture.bind();
		glColor3d(r, g, b);
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2d(0,1);
		glVertex2d(x, y);
		glTexCoord2d(1,1);
		glVertex2d(x+((double)img.getWidth()/(double)window.getWidth()*2-1), y);
		glTexCoord2d(1,0);
		glVertex2d(((double)img.getWidth()/(double)window.getWidth()*2-1), ((double)img.getHeight()/(double)window.getHeight()*2-1));
		glTexCoord2d(0,0);
		glVertex2d(x, ((double)img.getHeight()/(double)window.getHeight()*2-1));
		glEnd();


	}
}