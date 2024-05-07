package ch.frey.jason.handlers;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import ch.frey.jason.utils.MyShaderReader;

public class ShaderHandler {

	private static int vertexID;
	private static int fragmentID;
	private static int shaderProgramm;

	public static void init() {

		MyShaderReader sr = new MyShaderReader();
		
		String VertexShader = sr.myShaderReader("vertex");
		String FragmentShader = sr.myShaderReader("fragment");
		
		
		//"init function"
		//compile and link shaders
		
		//kreeier vertex shader
		vertexID = GL30.glCreateShader(GL30.GL_VERTEX_SHADER);
		
		//wo befindet sich dr shader
		GL30.glShaderSource(vertexID, VertexShader);
		
		//compilier dr shader
		GL30.glCompileShader(vertexID);
		
		//Check for Errors in Compilation, erscht welles �berpr�eft, denn was �berpr�eft
		int succes = GL30.glGetShaderi(vertexID, GL30.GL_COMPILE_STATUS);
		
		if (succes == GL30.GL_FALSE) {
			
			int len= GL30.glGetShaderi(vertexID, GL30.GL_INFO_LOG_LENGTH);
			System.err.println("Vertex Shader Compilation failed in file default.glsl!");
			//wege C brucht glGetShaderInfo beides, d Id und d L�ngi
			System.out.println(GL30.glGetShaderInfoLog(vertexID, len));
			
			//TODO aluege was das macht
			assert false: "";
		
		}
		//kreeier fragment shader
		fragmentID = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER);
		
		//wo befindet sich dr shader
		GL30.glShaderSource(fragmentID, FragmentShader);
		
		//compilier dr shader
		GL30.glCompileShader(fragmentID);
		
		//Check for Errors in Compilation, erscht welles �berpr�eft, denn was �berpr�eft
		succes = GL30.glGetShaderi(fragmentID, GL30.GL_COMPILE_STATUS);
		
		if (succes == GL30.GL_FALSE) {
			
			int len = GL30.glGetShaderi(fragmentID, GL30.GL_INFO_LOG_LENGTH);
			System.err.println("Fragment Shader Compilation failed in file default.glsl!");
			//wege C brucht glGetShaderInfo beides, d Id und d L�ngi
			System.out.println(GL30.glGetShaderInfoLog(fragmentID, len));
			
			assert false: "";
		
		}

		//creation shaderprogramm
		shaderProgramm = GL30.glCreateProgram();
		//attach the 2 shaders to the programm
		GL30.glAttachShader(shaderProgramm, vertexID);
		GL30.glAttachShader(shaderProgramm, fragmentID);
		//linking of the shaderprogramms
		GL30.glLinkProgram(shaderProgramm);;
		
		//test for success
		succes = GL30.glGetProgrami(shaderProgramm, GL30.GL_LINK_STATUS);
		if(succes == GL30.GL_FALSE) {
			int len = GL30.glGetProgrami(shaderProgramm, GL30.GL_INFO_LOG_LENGTH);
			System.err.println("Linking of the Shaders failed!");
			//wege C bruchts d L�ngi
			System.out.println(GL30.glGetProgramInfoLog(shaderProgramm, len));
			assert false: "";
		}
	}
	public static int getShaderProgramm() {
		return shaderProgramm;
	}
	
	public static void uploadUniformMatrix(String varName, Matrix4f mat4) {
		int varLocation = GL30.glGetUniformLocation(ShaderHandler.getShaderProgramm(), varName);
		//4x4 Matrix so 16
		FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
		
		

		mat4.get(matBuffer);
		GL30.glUniformMatrix4fv(varLocation, false, matBuffer);
		
	}

}
