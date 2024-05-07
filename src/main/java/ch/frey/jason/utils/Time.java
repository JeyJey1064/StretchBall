package ch.frey.jason.utils;

//here we convert the different variables, dt = time passed per frame, dt^(-1)=F/s=FPS
public class Time {
	
	//System.nanoTime is the time of then wehen the application started
	public static float timeStarted = System.nanoTime();
	
	//gets the time elapsed, *1E-9 for transofmation from ns to s
	public static float getTime() { return (float)(System.nanoTime() - timeStarted * 1E-9);}
	
}
