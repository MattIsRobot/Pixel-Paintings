package engine.io;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Controller {
	private static boolean[] keys = new boolean[GLFW_KEY_LAST];
	private static boolean[] btns = new boolean[GLFW_MOUSE_BUTTON_LAST];
	private static double mouseX, mouseY;

	private GLFWKeyCallback keyboard;
	private GLFWCursorPosCallback mousePos;
	private GLFWMouseButtonCallback mouseBtn;

	public Controller(){
		keyboard = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				keys[key] = (action != GLFW_RELEASE);
			}
		};
		mousePos = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double x, double y) {
				mouseX = x;
				mouseY = y;
			}
		};
		mouseBtn = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				btns[button] = (action != GLFW_RELEASE);
			}
		};
	}

	public void destroy() {
		keyboard.free();
		mouseBtn.free();
		mousePos.free();
	}

	public static boolean isKeyDown(int key){
		return keys[key];
	}

	public static boolean isBtnDown(int btn){
		return btns[btn];
	}

	public static double getMouseX() {
		return mouseX;
	}

	public static double getMouseY() {
		return mouseY;
	}

	public GLFWKeyCallback getKeyboardCallback() {
		return keyboard;
	}

	public void setKeyboardCallback(GLFWKeyCallback keyboard) {
		this.keyboard = keyboard;
	}

	public GLFWCursorPosCallback getMousePosCallback() {
		return mousePos;
	}

	public void setMousePosCallback(GLFWCursorPosCallback mousePos) {
		this.mousePos = mousePos;
	}

	public GLFWMouseButtonCallback getMouseBtnCallback() {
		return mouseBtn;
	}

	public void setMouseBtnCallback(GLFWMouseButtonCallback mouseBtn) {
		this.mouseBtn = mouseBtn;
	}


}
