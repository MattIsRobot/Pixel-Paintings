package engine.io;

import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFW.*;

import java.awt.*;
import java.io.IOException;

public class Window {
	private int width;
	private int height;
	private int[] prevDims = new int[2];
	private String title;
	private long window;
	public int frames;
	public static long time;
	public Controller controller;
	private float backgroundR, backgroundG, backgroundB;
	private GLFWWindowSizeCallback sizeCallback;
	private boolean isResized = false;
	private boolean isResizedFull = false;
	private boolean isFullscreen = false;
	private int[] windowX = new int[1];
	private int[] windowY = new int[1];

	/**
	 * Create window with specified width, height, and title
	 * @param width
	 * @param height
	 * @param title
	 */
	public Window(int width, int height, String title){
		this.height = height;
		this.width = width;
		this.title = title;
		prevDims[0] = width;
		prevDims[1] = height;
	}

	/**
	 * Initializes the window
	 */
	public void init(){
		if(!glfwInit()) {
			System.err.println("Unable to initialize GLFW");
			return;
		}

		controller = new Controller();
		window = glfwCreateWindow(width, height, title, isFullscreen ? glfwGetPrimaryMonitor() : 0, 0);

		setIcon("/paintbrush.png");

		if(window == 0) {
			System.err.println("Unable to create window");
			return;
		}

		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		windowX[0] = (videoMode.width()-width)/2;
		windowY[0] = (videoMode.height()-height)/2;
		glfwSetWindowPos(window, windowX[0], windowY[0]);
		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		createCallbacks();

		glfwShowWindow(window);

		glfwSwapInterval(1);

		time = System.currentTimeMillis();
	}

	private void createCallbacks(){
		//assign controller callbacks to the generated window
		glfwSetKeyCallback(window, controller.getKeyboardCallback());
		glfwSetCursorPosCallback(window, controller.getMousePosCallback());
		glfwSetMouseButtonCallback(window, controller.getMouseBtnCallback());

		//assign window size callback
		sizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int newWidth, int newHeight) {
				if (isFullscreen){
					prevDims[0] = width;
					prevDims[1] = height;
				}
				width = newWidth;
				height = newHeight;
				isResized = true;
			}
		};
		glfwSetWindowSizeCallback(window, sizeCallback);
	}

	/**
	 * Update the window with graphics, size, etc.
	 */
	public void update(){
		if(isResized){
			GL11.glViewport(0,0,width,height);
			isResized = false;
		}
		if (isResizedFull) {
			if (isFullscreen) {
				glfwGetWindowPos(window, windowX, windowY);
				GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
				assert videoMode != null;
				glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, videoMode.width(), videoMode.height(), 0);
			}
			else {
				glfwSetWindowMonitor(window, 0, windowX[0], windowY[0], prevDims[0], prevDims[1], 0);
			}
			isResizedFull = false;
		}

		//set clear color
		GL11.glClearColor(backgroundR, backgroundG, backgroundB, 1f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);


		glfwPollEvents();

		//Renderer.update();

		// frame counter
		frames++;
		if (System.currentTimeMillis() > time + 1000){
			glfwSetWindowTitle(window, title + " | " + frames + "fps");
			time = System.currentTimeMillis();
			frames = 0;
		}
	}

	/**
	 * Colours window correctly
	 */
	public void swapBuffers(){
		glfwSwapBuffers(window);
	}

	/**
	 * Checks if window is to be closed
	 * @return if the window exit button has been pressed
	 */
	public boolean shouldClose(){
		return glfwWindowShouldClose(window);
	}

	/**
	 * Closes window
	 */
	public void destroy() {
		controller.destroy();
		glfwDestroyWindow(window);
	}

	public void setBackgroundColor(float r, float g, float b){
		backgroundR = r;
		backgroundG = g;
		backgroundB = b;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public long getWindow() {
		return window;
	}

	public boolean isFullscreen() {
		return isFullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		isFullscreen = fullscreen;
		isResizedFull = true;
	}

	public void setIcon(String path){
		/*glfwWindowHint(GLFW_DECORATED, 0);

		ImageParser icon = null;
		try {
			icon = ImageParser.loadImage(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		GLFWImage image = GLFWImage.malloc();
		GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
		image.set(icon.getWidth(), icon.getHeight(), icon.getImage());
		imagebf.put(0, image);
		glfwSetWindowIcon(window, imagebf);*/
	}
}
