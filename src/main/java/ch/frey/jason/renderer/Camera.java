package ch.frey.jason.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

	private Matrix4f projectionMatrix, viewMatrix, transformationMatrix;
	private Vector2f position;
	
	//TODO versto was mer mit dr Matrix genau mache
	public Camera(Vector2f position) {
		this.position = position;
		//to not get any errors
		this.projectionMatrix = new Matrix4f();
		this.viewMatrix = new Matrix4f();
		this.transformationMatrix = new Matrix4f();
		//System.out.println(transformationMatrix);
		adjustProjection();
	}
	
	public Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		transformationMatrix.identity();
		//transformationMatrix.translate(translation);
		
		//achtung cast, maybe gohts hie ned TODO �u� hie falsch
		//transformationMatrix.rotateXYZ((float) Math.toRadians(rx), (float) Math.toRadians(ry), (float) Math.toRadians(rz));
		//transformationMatrix.scale(scale, scale, scale);
		
		return transformationMatrix;
		
	}
	public void adjustProjection() {
		//setzt d Matrix als identity Matrix, identity Matrix macht eis 1 pro zile und dr rescht null unzwar das diagonal vo tl to br
		projectionMatrix.identity();
		projectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f);
	}
	public Matrix4f getViewMatrix() {
		
		//luegt in richtig 1 in Z
		Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
		//define top of Cam
		Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
		this.viewMatrix.identity();
		//1. Komponente Kameraposition, 2.Position Position + cameraFront (C    *) 3. Komponente where is the top of cam
		viewMatrix = viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f), cameraFront.add(position.x, position.y, 0.0f), cameraUp);
		
		return this.viewMatrix;
	}
	public Matrix4f getProjectionMatrix() {
		return this.projectionMatrix;
	}
}
