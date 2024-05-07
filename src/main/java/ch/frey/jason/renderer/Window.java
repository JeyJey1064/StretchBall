package ch.frey.jason.renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import org.lwjgl.system.MemoryUtil;

import ch.frey.jason.abstractClasses.Scene;

import ch.frey.jason.utils.Time;

public class Window {
	private long window;
	private final int WIDTH = 600, HEIGHT = 600;
	
	private static Scene currentScene = null;

	public void run() {

		init();		
		loop();


	}

	public void init() {
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		// returns GLFW_FALSE if an error occured.
		// until initialized it is thread unsafe
		if (!GLFW.glfwInit()) {
			// Handle initialization failure
			throw new IllegalStateException("Unable to Initialize GLFW");

		}
		
		// before glfwInit or else will be ignored
		// values with glfwInitHint are never reset
		// GLFW.GLFW_TRUE / GLFW.GLFW_FALSE werden gebraucht.
		// first set default options for WindowHints, then change them
		GLFW.glfwDefaultWindowHints();
		// TODO aluege wurum Visible uf False setze
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GL11.GL_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		// GLFW_FLOATING is used to debug. Window will always be on top



		// TODO MemoryUtil.NULL used instead of 0
		// WindowedMode
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Fenster", MemoryUtil.NULL, MemoryUtil.NULL);

		// Full Screen:
		// window = GLFW.glfwCreateWindow(640, 480, "My Title",
		// GLFW.glfwGetPrimaryMonitor(), MemoryUtil.NULL);

		// TODO Fullscreen and Window Mode together.

		// falls Fenster nicht kreiert.
		if (window == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to Create an LWJGL window!");
		}

		// change Window Size after initialisation
		GLFW.glfwSetWindowSize(window, WIDTH, HEIGHT);

		// destroy Window:
		// GLFW.glfwDestroyWindow(window);

		// Sets the Viewport of the Window
		// TODO Viewport �ndern funktioniert gerade nicht
		// GL11.glViewport(0, 0, WIDTH, HEIGHT);

		// Loops until the Window should close
		// hier drinnen d�rfen die Rendermethoden gebaut werden

		// TODO aluege was das macht
		GLFW.glfwMakeContextCurrent(window);

		// TODO aluege was das macht und wieso das mini FPS ned locked
		GLFW.glfwSwapInterval(1);

		// show the Window
		GLFW.glfwShowWindow(window);
		

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// (Anschienend sehr wichtig f�r bindings) bindings available for use.
		//TODO aluege wie das funktioniert
		GL.createCapabilities();
		
		changeScene(1);

	}
	public void loop(){
		//last Time that the Frame ran
		float beginTime = Time.getTime();
		float endTime;
		float dt = -1.0f;
		
		
		while (!GLFW.glfwWindowShouldClose(window)) {
			//Um alle Input eingaben zur verf�gung zu stellen. Does Poll it and make it available to Keylisteners.
			GLFW.glfwPollEvents();

			//TODO aluege wurum me das bruche ch�nnti
//			if(dt>0) {
//				currentScene.update(dt);
//			}
			
			//TODO aluege wie me hintergrund und gerendertes Dreieck macht
			//GL30.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
			//zerscht setze mer clearColor, denn flushe mers mitem Befehl unde
			//GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
			
			//TODO aluege was das macht
			GLFW.glfwSwapBuffers(window);
			
			
			endTime = Time.getTime();
			dt = endTime - beginTime;
			//TODO aluege wurum infinity Frames;
//			System.out.println(1.0f/dt);
			beginTime=endTime;
		
			currentScene.update(dt);
		}

		if (GLFW.glfwWindowShouldClose(window)) {
			cleanUp();

		}
	}
	
	public static void changeScene(int newScene) {
		switch (newScene) {
		case 0:
			currentScene = new LevelEditorScene();
			//currentScene.init();
			break;
		case 1:
			currentScene = new LevelScene();
			//currentScene.init();
			break;
		default:
			assert false : "Unknown Scnene '" + newScene + "'";
			break;
		}
	}

	private void cleanUp() {
		//TODO proper Cleanup
		// before application exists use terminate
		// destroys any remaining window, cursor, monitor etc. objects
		GLFW.glfwTerminate();
	}
	
	

	
}
	
