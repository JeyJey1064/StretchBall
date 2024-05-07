package ch.frey.jason.handlers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import ch.frey.jason.utils.MyObjectReader;

public class VAOVBOEBOHandler {
	private static int vaoID, vboID, eboID;
	//das bruchts programm TODO fileReader wo cha .obj lese
		private static float[] vertexArray = {
				//position                      //texture coordinates
				 0.5f,  0.5f, 0.0f,             1.0f, 0.0f, //top right   0
				-0.5f,  0.5f, 0.0f,             0.0f, 0.0f, //top left    1
				-0.5f, -0.5f, 0.0f,             0.0f, 1.0f, //bot left    2
				 0.5f, -0.5f, 0.0f,             1.0f, 1.0f};//bot right   3
		//important, counter clockwise order
		private static int[] elementArray = {
				//x          x
				//
				//
				//
				//x          x
				
				0, 1, 3, //top right triangle
				3, 1, 2  //bot left triangle
		};
											 //color
//		private static float[] colorArray = {1.0f, 0.0f, 0.0f, 1.0f, old color Array, you have to define colors in order of indicies
//											 0.0f, 1.0f, 0.0f, 1.0f,
//											 0.0f, 0.0f, 1.0f, 1.0f,
//											 1.0f, 0.0f, 1.0f, 1.0f};

//	private static float[] vertexArray = null;
//	private static int[] elementArray = null; 
	
	public static void initVAOVBOEBO() {
//		
//		MyObjectReader stall = new MyObjectReader("stall.obj");
//		vertexArray = stall.getMyVertexArray();
//		elementArray = stall.getIndiciesArray();
		
		//VAO
		//generate and send vao, vbo and ebo to the gpu
		//gen heisst generate, Array!s! wobei s für mehrere VertexArrays steht, was ein Vao ist
		vaoID = GL30.glGenVertexArrays();
		//bind damit wir die änderungen an diesem vao durchführen;
		GL30.glBindVertexArray(vaoID);
		
		
		//VBO
		//create a floatBuffer of vertecies (OpenGL prefers Floatbuffers because of efficiency). You have to tell the size of the buffer
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
		//put um Array in den Buffer hinzuzufügen, flip muss rein für opengl TODO was macht flip (switch vo writing zu reading mode)
		vertexBuffer.put(vertexArray).flip();
				
		//generate a buffer
		vboID = GL30.glGenBuffers();
		//bind the buffer so the changes are made to it TODO why have to say ArrayBuffer (GL_Array_Buffer = Vertex attributes)
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
		//wir sagen dsa es sich um einen arrayBuffer handelt und geben diesem den gebauten buffer und sagen nichts verändert
		//TODO glBufferData aluege
		//glBufferData = create new Buffer. Buffer bound is used to store the vertexBuffer in a Buffer
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexBuffer, GL30.GL_STATIC_DRAW);
		
		
		
		//EBO
		//create the indicies and upload
		//you have to tell the size of the buffer
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
		//add the element array and dont forget to flip for OpenGL
		elementBuffer.put(elementArray).flip();
		
		eboID = GL30.glGenBuffers();
		//element Array Buffer to tell OpenGL that we want to save indicies
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, eboID);
		GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL30.GL_STATIC_DRAW);
		
		
		//add Vertex Pointers (tell the GPU the offset of the attributes our VBO)
		int positionsSize = 3;
		int uvSize = 2;
		//size of one stride in Bytes. Size of one vertex, Float.BYTES is the size of Float in Bytes
		int vertexSizeInBytes = (positionsSize + uvSize) * Float.BYTES;
		
		//0 wege location wo mer im shader ageh hen, positionsSize will wie gross die Position isch
		//GL_FLOAT will es sich hiebi um e Float handlet wo mer witer gehn, TODO normalized
		//vertexSizeInBytes for stride length and 0 for the offset in Bytes of our attribute
		GL30.glVertexAttribPointer(0, positionsSize, GL30.GL_FLOAT, false, vertexSizeInBytes, 0);
		//enable to make it visible for Shader TODO genaue definition enable nachschlagen
		GL30.glEnableVertexAttribArray(0);
				
		GL30.glVertexAttribPointer(2, uvSize, GL30.GL_FLOAT, false, vertexSizeInBytes, (positionsSize) * Float.BYTES);
		GL30.glEnableVertexAttribArray(2);
	}
	public static int[] getElementArray() {
		return elementArray;
	}
	public static int getVaoID() {
		return vaoID;
	}

}
