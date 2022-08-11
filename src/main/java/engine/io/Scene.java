package engine.io;

public class Scene {
	String layoutFile;
	Renderer[] renderers;
	Window window;
	Scene loadScene;

	public Scene (String layoutFile, Window window){
		this.layoutFile = layoutFile;
		this.window = window;
	}

	public void setActive(){

	}

	private void loadScene(Scene loadScreen){

	}

	private void init(String layoutFile){
		//transition to black
		SceneLoader thread = new SceneLoader(layoutFile);
		thread.start();
		while(thread.isAlive()){
			//show black
			loadScene(loadScene);
		}
	}

	private void update(){
		for (Renderer r : renderers) {
			if (r.isEnabled()) r.draw(window);
		}

	}
}
