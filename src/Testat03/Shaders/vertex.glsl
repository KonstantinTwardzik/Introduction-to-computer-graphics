#version 330

in vec4 in_pos;
in vec4 in_color;
out vec4 var_color;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(){
	gl_Position = in_pos;
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * gl_Position;
	var_color = in_color;
}

