package ch.frey.jason.renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

import ch.frey.jason.abstractClasses.Scene;
import ch.frey.jason.handlers.ShaderHandler;
import ch.frey.jason.handlers.TextureHandler;
import ch.frey.jason.handlers.VAOVBOEBOHandler;

public class LevelScene extends Scene{
	public LevelScene() {		
		System.out.println("Inside Level Scene!");
	}

	@Override
	public void update(float dt) {

		Camera camera = new Camera(new Vector2f());
		//TODO should only be called ones I think
//		ShaderHandler.uploadUniformMatrix("uProjectionMatrix", camera.getProjectionMatrix());
//		ShaderHandler.uploadUniformMatrix("uViewMatrix", camera.getViewMatrix());
		ShaderHandler.uploadUniformMatrix("uTransformationMatrix", camera.createTransformationMatrix(new Vector3f(2, 2, 2), 0, 0, 0, 1.0f));
		ShaderHandler.init();
		VAOVBOEBOHandler.initVAOVBOEBO();
		TextureHandler marioTexture = new TextureHandler();
		marioTexture.initTexture("testImage.png");
		
		//draw our stuff
		//Bind Shader Programm used
		GL30.glUseProgram(ShaderHandler.getShaderProgramm());
		
		//bind the Texture
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, marioTexture.getTextureID());

		//bind VAO that we are using
		GL30.glBindVertexArray(VAOVBOEBOHandler.getVaoID());
		
		//enable GLAttribute Pointers
		//TODO aluege wie de Name entstoht
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		
		//drawing mode, how many indicies we draw, Ints but with no + or - TODO 0 am schluss aluege
		GL30.glDrawElements(GL30.GL_TRIANGLES, VAOVBOEBOHandler.getElementArray().length, GL30.GL_UNSIGNED_INT, 0);
		
		//unbind everithyng
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		
		GL30.glBindVertexArray(0);
		GL30.glUseProgram(0);
		
		
	}
}
