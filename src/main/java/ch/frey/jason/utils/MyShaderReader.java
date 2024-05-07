package ch.frey.jason.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MyShaderReader {

	public String myShaderReader(String typeOfShader) {
		
		
		//erstes / heisst das man zurück zum root landet
		File file = new File("Assets/Shaders/default.glsl");
//		System.out.println(file.getAbsolutePath());		
		String shader = "";
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))){
			String line;
			boolean addToShader = false;
			while((line = br.readLine()) != null) {				
				if(addToShader) {					
					shader = shader+ line + "\n";					
				}

				if (line.contains("#type "+ typeOfShader)) {
					addToShader = true;
				}else if (line.contains("}")) {
					addToShader = false;
				}
				
				
			}
			
			
		} catch (Exception e) {
			System.err.println("something went wrong");
		}

	
		return shader;
	}
	
}
/*
find out what type of shader = ts

if detects #type ts
read until }

return string

falls beim shaderprogrammieren mehr als 1 } gebraucht wird dann einfach überprüfen auf nächstes #type (mit contains)

kann man auch so programmieren das mehrere Dateien für jeweilige Shader gebraucht werden, jetzt nicht relevant


Warum nicht scanner?
Weil es weniger optionen gibt, langsamer ist und hasNextLine benötigt um zu überprüfen und für mich einfacher zum programmieren
*/