package ch.frey.jason.handlers;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

public class TextureHandler {
	private int textureID;
	
	private File texture = null;
	public void initTexture(String fileName) {
		texture = new File("Assets/Textures/" + fileName);

				textureID = GL30.glGenTextures();
				GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureID);
				
				//set Texture parameters
				//Repeat in both directions (S steht f�r X, T steht f�r Y)
				//TODO aluege was die Parameter genau wen
				GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_REPEAT);
				GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_REPEAT);
				//GLTextureMinFilter says it is stretching, GLNearest says to pixelate. TODO Parameter genau aluege.
				GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
				//For GL_Mag_Filter for shrinking.
				GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
				
				//we use Buffers to give it to the GPU
				IntBuffer width = BufferUtils.createIntBuffer(1);
				IntBuffer height = BufferUtils.createIntBuffer(1);
				//3 channels for rgb, 4 for rgba
				IntBuffer channels = BufferUtils.createIntBuffer(1);
				//fist string file path, then buffers of width height and channels and 0 to declare that it should take it how it actually is. 
				ByteBuffer image = STBImage.stbi_load(texture.getAbsolutePath(), width, height, channels, 0);
				
				//Wenn das Bild laden konnte
				if (image != null) {
					
					//TODO Parameter aluege
					if (channels.get(0) == 3) {
						
						GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGB, width.get(0), height.get(0), 0, GL30.GL_RGB, GL30.GL_UNSIGNED_BYTE, image);	
					}else if (channels.get(0) == 4) {
						GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA, width.get(0), height.get(0), 0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, image);	
					} else {
						assert false : "Error: (Texture) Unknown number of Channels '" + channels.get(0) + "'";
					}
					
				} else {
					assert false: "Could not load Image: " + texture.getAbsolutePath();
					System.err.println("Could not load Image: " + texture.getAbsolutePath());
				}
				//stbImage leeren
				STBImage.stbi_image_free(image);
				
//				//creates all nescecarry Mipmaps for the currently bound Texture
//				GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D_ARRAY);
				
	}
	public int getTextureID() {
		return textureID;
	}

}
