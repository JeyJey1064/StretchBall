package ch.frey.jason.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class MyObjectReader {
	
	private float[] verteciesArray = null;
	private float[] normalsArray = null;
	private float[] texturesArray = null;
	private int[] indiciesArray = null;
	private float[] myVertexArray = null;
	
	private List<Vector3f> vertecies = new ArrayList<Vector3f>();
	private List<Vector2f> textures = new ArrayList<Vector2f>();
	private List<Vector3f> normals = new ArrayList<Vector3f>();
	private List<Integer> indicies = new ArrayList<Integer>();
	private List<Float> myVertexList = new ArrayList<Float>();
	
	public MyObjectReader(String objectFileName) {
		// so deklariert damit fileReader auch ausserhalb von Try Catch erkannt wird
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("Assets/Models/" + objectFileName);
		} catch (FileNotFoundException e1) {
			System.err.println("File " + objectFileName + " was not found!");
			e1.printStackTrace();
		}

		String line;
		String[] currentLine = null;


		BufferedReader reader = new BufferedReader(fileReader);

		// try and catch for if there are any invalid form formats used
		try {
			while (true) {

				line = reader.readLine();
				// Vertex komponente sind mit " " getrennt und steckt das ine String Array
				// parseFloat macht e String zuneme Float
				currentLine = line.split(" ");
				
				if (line.startsWith("v ")) {

					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					vertecies.add(vertex);

				} else if (line.startsWith("vt ")) {

					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
				} else if (line.startsWith("vn ")) {

					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					normals.add(normal);
				} else if (line.startsWith("f ")) {
					// jetzt sind mer fertig mit alle Listene und cheu die in Arrays hinzuef�ege wie
					// mers gern hette.
					verteciesArray = new float[vertecies.size() * 3];
					texturesArray = new float[vertecies.size() * 2];
					normalsArray = new float[vertecies.size() * 3];
					break;
				}

				

			}
			while (line != null) {
				if (!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}

				currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
								
				processVertex(vertex1, indicies, textures, normals, texturesArray, normalsArray);
				processVertex(vertex2, indicies, textures, normals, texturesArray, normalsArray);
				processVertex(vertex3, indicies, textures, normals, texturesArray, normalsArray);
				
				line = reader.readLine();
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		verteciesArray = new float[vertecies.size()*3];
		indiciesArray = new int[indicies.size()];
		
		int vertexPointer = 0;
		for(Vector3f vertex:vertecies) {
			verteciesArray[vertexPointer++] = vertex.x;
			verteciesArray[vertexPointer++] = vertex.y;
			verteciesArray[vertexPointer++] = vertex.z;

		}
		for(int i = 0; i<indicies.size();i++){
			indiciesArray[i] = indicies.get(i);
		}
		generateMyVertexArray();

	}
	
	private void generateMyVertexArray() {
		int counter = 0;
		int verteciesArrayCounter = 0;
		int texturesArrayCounter = 0;
		for(int i = 0; i < verteciesArray.length + texturesArray.length; i++) {
			if(counter >= 0 && counter <= 2) {
				
				myVertexList.add(verteciesArray[verteciesArrayCounter]);
				verteciesArrayCounter++;
			
			}else if(counter >= 3 && counter <= 4) {
				myVertexList.add(texturesArray[texturesArrayCounter]);	
				texturesArrayCounter++;
				
			}
			
			if(counter <= 4) {
				counter++;
			}else {
				counter = 0;
			}
		}
		
		myVertexArray = new float[myVertexList.size()];
		counter = 0;
		for(Float f:myVertexList) {
			myVertexArray[counter] = myVertexList.get(counter);
			counter++;
		}
		
	}
	

	private static void processVertex(String[] vertexData, List<Integer> indicies, List<Vector2f> textures,
			List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
		
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indicies.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer*2] = currentTex.x;
		//TODO maybe isches ned 1 -, wenn doch denn will OpenGL Texture coordinates vo obe links afoht und blender unde links (oder umgekehrt)
		textureArray[currentVertexPointer*2 + 1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3+1] = currentNorm.y;
		normalsArray[currentVertexPointer*3+2] = currentNorm.z;

		
		
	}
	
	public float[] getVerteciesArray() {
		return verteciesArray;
	}


	public float[] getNormalsArray() {
		return normalsArray;
	}


	public float[] getTexturesArray() {
		return texturesArray;
	}


	public int[] getIndiciesArray() {
		return indiciesArray;
	}


	public float[] getMyVertexArray() {
		return myVertexArray;
	}

}
