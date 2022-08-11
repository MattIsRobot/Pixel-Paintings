package engine.io;

import org.lwjgl.system.MemoryStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static java.lang.System.exit;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class ImageParser {
	public ByteBuffer getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return heigh;
	}

	private ByteBuffer image;
	private int width, heigh;

	ImageParser(int width, int heigh, ByteBuffer image) {
		this.image = image;
		this.heigh = heigh;
		this.width = width;
	}
	public static ImageParser loadImage(String path) throws IOException {
		InputStream is = ImageParser.class.getResourceAsStream(path);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		byte[] bytes = buffer.toByteArray();
		ByteBuffer imageBuffer = ByteBuffer.wrap(bytes);

		ByteBuffer image;
		int width, heigh;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer comp = stack.mallocInt(1);
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);

			image = stbi_load_from_memory(imageBuffer, w, h, comp, 4);
			if (image == null) {
				System.out.println("Could not load image resources.");
				exit(1);
			}
			width = w.get();
			heigh = h.get();
		}
		return new ImageParser(width, heigh, image);
	}
}
