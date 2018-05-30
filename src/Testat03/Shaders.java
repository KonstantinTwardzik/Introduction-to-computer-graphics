package Testat03;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

class Shaders {

    static int getVertexShader() {
        return createShader(GL_VERTEX_SHADER, defineVertexShader());
    }

    static int getFragmentShader() {
        return createShader(GL_FRAGMENT_SHADER, defineFragmentShader());
    }

    private static String defineVertexShader() {
        return "#version 330			                                                        \n"
                + "in vec4 in_pos;			                                                    \n"
                + "in vec4 in_color;		                                                    \n"
                + "out vec4 var_color;		                                                    \n"
                + "uniform mat4 modelMatrix;                                                    \n"
                + "uniform mat4 viewMatrix;                                                     \n"
                + "uniform mat4 projectionMatrix;                                               \n"
                + "void main(){				                                                    \n"
                + "		gl_Position = in_pos;                                                   \n"
                + "     gl_Position = projectionMatrix * viewMatrix * modelMatrix * gl_Position;\n"
                + "		var_color = in_color;                                                   \n"
                + "}						                                                    \n";
    }

    private static String defineFragmentShader() {
        return "#version 330						    \n"
                + "in vec4 var_color;                   \n"
                + "out vec4 out_color;					\n"
                + "void main(){							\n"
                + "		out_color = var_color;			\n"
                + "}									\n";
    }

    private static int createShader(int shaderType, String shaderSource) {
        // initiate Shader
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, shaderSource);
        glCompileShader(shader);

        // error handling
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            String error = glGetShaderInfoLog(shader);
            String shaderTypeString = null;
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
