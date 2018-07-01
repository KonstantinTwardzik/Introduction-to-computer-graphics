//package Testat03;
//
//import Testat03.Shaders.Shaders;
//import Testat03.Utils.BufferHelper;
//import Testat03.Utils.MatrixHelper;
//import org.lwjgl.BufferUtils;
//import org.lwjgl.glfw.GLFW;
//import org.lwjgl.glfw.GLFWErrorCallback;
//import org.lwjgl.opengl.GL;
//
//import java.nio.FloatBuffer;
//import java.nio.IntBuffer;
//
//import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL15.*;
//import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL30.glBindVertexArray;
//import static org.lwjgl.opengl.GL30.glGenVertexArrays;
//import static org.lwjgl.system.MemoryUtil.NULL;
//
//public class TriangleViewer implements Runnable {
//
//    private int localModelMatrix, localViewMatrix, localProjectionMatrix;
//    private long window;
//    private int program;
//    private int indexCount;
//    private float[][] viewMatrix;
//    private float[][] projectionMatrix;
//
//    // starts the application
//    public static void main(String[] args) {
//        new TriangleViewer().run();
//    }
//
//    // starts the window and defines the main-loop
//    @Override
//    public void run() {
//        try {
//            initWindow();
//            GL.createCapabilities();
//            initBuffers();
//            initShaders();
//
//            localModelMatrix = glGetUniformLocation(program, "modelMatrix");
//            localViewMatrix = glGetUniformLocation(program, "viewMatrix");
//            localProjectionMatrix = glGetUniformLocation(program, "projectionMatrix");
//
//            glEnable(GL_DEPTH_TEST);
//            initViewProjection();
//
//            // Game Loop
//            while (!glfwWindowShouldClose(window)) {
//                frame();
//                glfwSwapBuffers(window);
//                glfwPollEvents();
//            }
//
//        } finally {
//            // Clean up
//            glfwTerminate();
//            //errorCallback.release();
//        }
//    }
//
//    //
//    private void frame() {
//        double time = GLFW.glfwGetTime();
//        float sin = (float) Math.sin(time);
//        float cos = (float) Math.cos(time);
//
//        float[][] modelMatrix = new float[][]{
//                {cos, 0.0f, -sin, 0.0f},
//                {0.0f, 1.0f, 0.0f, 0.0f},
//                {sin, 0.0f, cos, 0.0f},
//                {0.0f, 0.0f, 0.0f, 1.0f}
//        };
//
//        glUniformMatrix4fv(localModelMatrix, false, BufferHelper.convertToBuffer(modelMatrix));
//        glUniformMatrix4fv(localViewMatrix, true, BufferHelper.convertToBuffer(viewMatrix));
//        glUniformMatrix4fv(localProjectionMatrix, true, BufferHelper.convertToBuffer(projectionMatrix));
//
//        glClearColor(0.2f, 0.2f, 0.22f, 1.0f);
//        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
//    }
//
//    // Initializes model
//    private void initBuffers() {
//
//        // Vertices
//        int vao = glGenVertexArrays();
//        glBindVertexArray(vao);
//        int vboPosition = glGenBuffers();
//        float[] positions = {
//                -1.33f, -1.50f,  -1.62f,  1.0f,
//                -1.33f, -1.50f,   1.62f,  1.0f,
//                2.15f, -1.50f,    0.0f,  1.0f,
//                0.0f,   1.50f,    0.0f,  1.0f
//        };
//        FloatBuffer data = BufferUtils.createFloatBuffer(positions.length);
//        data.put(positions);
//        data.flip();
//        glBindBuffer(GL_ARRAY_BUFFER, vboPosition);
//        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
//        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0L);
//        glEnableVertexAttribArray(0);
//
//        // Colors
//        int vboColor = glGenBuffers();
//        float[] color = {
//                0.851f, 0.255f, 0.596f, 1.000f,
//                0.000f, 0.400f, 0.800f, 1.000f,
//                1.000f, 0.800f, 0.000f, 1.000f,
//                0.239f, 0.600f, 0.239f, 1.000f
//        };
//        FloatBuffer colorData = BufferUtils.createFloatBuffer(color.length);
//        colorData.put(color);
//        colorData.flip();
//        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
//        glBufferData(GL_ARRAY_BUFFER, colorData, GL_STATIC_DRAW);
//        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0L);
//        glEnableVertexAttribArray(1);
//
//        //Index Buffer
//        int[] indices = {
//                0, 1, 2,
//                0, 1, 3,
//                0, 2, 3,
//                1, 2, 3
//        };
//        indexCount = indices.length;
//        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
//        indexBuffer.put(indices);
//        indexBuffer.flip();
//        int ibo = glGenBuffers();
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
//        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
//    }
//
//    // Initializes shaders
//    private void initShaders() {
//
//        int vertexShader = Shaders.getVertexShader();
//        int fragmentShader = Shaders.getFragmentShader();
//
//        program = glCreateProgram();
//        glAttachShader(program, vertexShader);
//        glAttachShader(program, fragmentShader);
//        glLinkProgram(program);
//
//        glBindAttribLocation(program, 0, "in_pos");
//        glBindAttribLocation(program, 1, "in_color");
//        glUseProgram(program);
//    }
//
//    // Initializes window
//    private void initWindow() {
//        GLFWErrorCallback errorCallback;
//        glfwSetErrorCallback(errorCallback = GLFWErrorCallback
//                .createPrint(System.err));
//
//        if (!glfwInit())
//            throw new IllegalStateException("Unable to initialize GLFW");
//
//        glfwDefaultWindowHints();
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
//        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
//        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
//        glfwWindowHint(GLFW_SAMPLES, 8);
//
//        window = glfwCreateWindow(800, 800, "Triangle viewer", NULL, NULL);
//        if (window == NULL) {
//            throw new RuntimeException("Failed to create the GLFW window");
//        }
//        glfwMakeContextCurrent(window);
//        glfwSwapInterval(1);
//
//        glfwShowWindow(window);
//    }
//
//    // Initialzes the cameraView
//    private void initViewProjection() {
//        float[] camera = {0f, 0f, 6f};
//        float[] up = {0f, 1f, 0f};
//        float[] center = {0f, 0f, 0f};
//        viewMatrix = MatrixHelper.matrixLookAt(camera, up, center);
//        projectionMatrix = MatrixHelper.matrixPerspective(2f, 2f, 2f, 10f);
//    }
//}