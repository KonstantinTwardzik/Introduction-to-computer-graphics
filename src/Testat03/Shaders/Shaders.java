package Testat03.Shaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shaders {

    public static int getVertexShader() {
        return createShader(GL_VERTEX_SHADER, ShaderReader.readFile("./src/Testat03/Shaders/vertex.glsl"));
    }

    public static int getFragmentShader() {
        return createShader(GL_FRAGMENT_SHADER, ShaderReader.readFile("./src/Testat03/Shaders/fragment.glsl"));
    }

    private static int createShader(int shaderType, String shaderSource) {
        // initiate Shader
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, shaderSource);
        glCompileShader(shader);

        // error handling
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            String error = glGetShaderInfoLog(shader);
            String shaderTypeString;
            switch (shaderType) {
                case GL_VERTEX_SHADER:
                    shaderTypeString = "vertex";
                    break;
                case GL_FRAGMENT_SHADER:
                    shaderTypeString = "fragment";
                    break;
                default:
                    shaderTypeString = "unknown";
                    break;
            }
            System.err.println("Compile error in " + shaderTypeString + " shader:\n" + error);
        }

        return shader;
    }

}
