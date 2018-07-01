/**
 * 
 */
package Sphere;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;


/**
 * Rahmenprogramm uebung2
 * einfacher Shader mit Transformation
 * Einfuehrung in die Computergrafik
 *@author F. N. Rudolph (c) 2016, V. Baguio
 *25.04.2016
 */
public class Kugel implements Runnable, Observer{
	/* initalizing the controller */
	MatrixController matrixController;
	public static void main(String[] args) {new Kugel().run();} // main funktion
	public Kugel() { // constructor
		super();
		matrixController = new MatrixController("Modelmatrix", 800);
		matrixController.addObserver(this);
	}
	@Override
	public void update(Observable o, Object arg) {
		double [][] tmp =  matrixController.getmGesamt();
		for (int i = 0; i < 16; i++){
			modelMatrix [i] = (float) tmp [ i / 4][i%4];
		}
	}
	
	/* Open GL attributes */
	
	private long window;
	private int width = 600, height = 600;
	private float aspectRatio = (float) width / (float) height;
	
	private GLFWErrorCallback errorCallback;
	private int vertexShader;
	private int fragmentShader;
	private int program;
	private int vao=1, vboPosition=1, vboUV=2;
	private int locModelMat; // Handle fuer Uniform modellMatrix
	private int locViewMat;
	private int locProjMat;
	private int locAspectRatio; // Handle fuer Uniform aspectRatio
	private float [] backGround = {
			1f, 1f, 0f, 1f
	};

	// Die Model Matrix
	// wird mit der Einheitsmatrix initialisiert
	private float [] modelMatrix = {
			1f, 0f, 0f, 0f,
			0f, 1f, 0f, 0f,
			0f, 0f, 1f, 0f,
			0f, 0f, 0f, 1f
	};
	
	private float[][] viewMatrix, projMatrix;
	
	private float [] vertices = sphereVertices(100, 100, 1f);

	private int count = (vertices != null ? vertices.length : 0) / 4;
	private float [] uvs;

	private float[] sphereVertices(int resolutionV, int resolutionH, float r){
		return null;
	}
	
	// siehe Kugelformel (Wikipedia)
	private float[] pointOnSphere(float theta, float phi, float r){
		return new float[]{
				(float)(Math.sin(theta) * Math.cos(phi) * r),	//x
				(float)(Math.sin(theta) * Math.sin(phi) * r),	//y
				(float)(Math.cos(theta) * r),					//z	
		};
	}
	
	@Override
	public void run() {
		System.out.println("Uebung2.run()");
		initWindow();
		GL.createCapabilities();
		initShaders();
		initBuffers();
		initTexture();
		SetupMatrices();
		
		locModelMat = glGetUniformLocation(program, "modelMatrix");
		locViewMat = glGetUniformLocation(program, "viewMatrix");
		locProjMat = glGetUniformLocation(program, "projMatrix");
		locAspectRatio = glGetUniformLocation(program, "aspectRatio");

		while (glfwWindowShouldClose(window) == false) {
			frame();
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
		// Aufraeumen
		glfwHideWindow(window);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		glDeleteProgram(program);
		System.exit(0);
	}
	
	private void initTexture(){
		try {
			BufferedImage img = ImageIO.read(new File("2k_earth_daymap.jpg"));
			TextureConverter.makeTextureFromBufferedImage(img);
			int locTexture = glGetUniformLocation(program, "u_texture");
			glUniform1i(locTexture, 0);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void frame(){
		glEnable(GL_DEPTH_TEST);
		
		// Matrizen setzen
		glUniformMatrix4fv(locModelMat, true, convert2Buffer(modelMatrix));
		glUniform1f(locAspectRatio, aspectRatio);
		
		glUniformMatrix4fv(locViewMat, true, convert2Buffer(viewMatrix));
		glUniformMatrix4fv(locProjMat, true, convert2Buffer(projMatrix));
		
		// Hintergrund loeschen
		glClearColor(backGround[0], backGround[1], backGround[2], backGround[3]);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// Geometrie zeichnen
		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, count);
	}
	
	private void SetupMatrices(){
		float[] camPos = {2.0f, 2.0f, 1.0f};
		float[] center = {0f,0f,0f};
		float[] up = {0f,1f,0f};
		viewMatrix = MatrixHelper.matrixLookAt(camPos, up, center);
		projMatrix = MatrixHelper.matrixPerspective(2f, 2f, 2f, 10f);
	}
	
	/**
	 * Fenster initialisieren
	 */
	private void initWindow() {
		System.out.println("LWJGL version: " + Version.getVersion());
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback
				.createPrint(System.err));

		if (glfwInit() != true)
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		// glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_ANY_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		window = glfwCreateWindow(width, height, "Uebung 1 Demo", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);

		glfwShowWindow(window);
	}
	
	/**
	 * Shader definieren
	 */
	private void initShaders() {
		String vertexSource = "";
		String fragmentSource = "";
		try {
			vertexSource = readFile("src/kugelHandout/vertex.txt");
			fragmentSource = readFile("src/kugelHandout/fragment.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertexShader,vertexSource);
		glCompileShader(vertexShader);
		shaderErrorCheck(vertexShader, "VertexShader");
		glShaderSource(fragmentShader,fragmentSource);
		glCompileShader(fragmentShader);
		shaderErrorCheck(fragmentShader, "FragmentShader");
		program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		glBindAttribLocation(program, 1, "in_pos");
		glBindAttribLocation(program, 2, "in_uv");
		glBindFragDataLocation(program, 0, "out_color");
		glLinkProgram(program);
		glUseProgram(program);
	}


	private void initBuffers() {
		if (vao > -1) {
			glDeleteBuffers(vao);
			glDeleteVertexArrays(vboPosition);
			glDeleteVertexArrays(vboUV);
		}
		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vboPosition = glGenBuffers();
		float[] pos = vertices;
		FloatBuffer data = BufferUtils.createFloatBuffer(pos.length);
		data.put(pos);
		data.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboPosition);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 16, 0L);

		// UV Koordinaten
		
		vboUV = glGenBuffers();
		float[] col = uvs;
		data = BufferUtils.createFloatBuffer(col.length);
		data.put(col);
		data.flip();

		glBindBuffer(GL_ARRAY_BUFFER, vboUV);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glEnableVertexAttribArray(2);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 8, 0L);
	}
	

	/**
	 * Fehlermeldungen f�r Shader
	 */
	private void shaderErrorCheck(int vertexShader, String name) {
		String log = glGetShaderInfoLog(vertexShader);
		if (log.length() > 0)
			System.err.println("Build log " + name + ": \n" + log);
		if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
			glDeleteShader(vertexShader);
			throw new RuntimeException("Shader compilation error:\n" + log);
		}
	}
	
	/**
	 * Reads a text file to a String and returns it
	 */
	static String readFile(String path) throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, Charset.defaultCharset());
	}
	
	/**
	 * Erstellt ein Floatbuffer aus einem Floatarray
	 * @param data
	 * @return
	 */
	private static FloatBuffer convert2Buffer(float [] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		   buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Erstellt einen Floatbuffer aus einem 2D Floatarray
	 * @param array Matrize mit floats
	 * @return Floatbuffer f�r LWJGL
	 * @author Stefan Bodenschatz
	 */
	private static FloatBuffer convert2Buffer(float[][] array){
		int cap=0;
		for(float[] a:array){
			cap+=a.length;
		}
		FloatBuffer buff = BufferUtils.createFloatBuffer(cap);
		for(float[] a:array){
			buff.put(a);
		}
		buff.flip();
		return buff;
	}
}









