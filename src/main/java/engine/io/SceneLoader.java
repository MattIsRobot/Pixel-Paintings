package engine.io;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SceneLoader extends Thread{

	private final String layoutFile;
	private boolean complete = false;

	public SceneLoader(String layoutFile){
		this.layoutFile = layoutFile;
	}

	public void run() {
		File layout = new File(this.layoutFile);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(layout);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}

		complete = true;
	}

	public boolean isComplete() {
		return complete;
	}
}