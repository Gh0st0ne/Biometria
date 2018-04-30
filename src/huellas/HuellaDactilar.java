package huellas;

public class HuellaDactilar {
	
	private int width;
	private int height;
	private int[][] imagen;
	
	public HuellaDactilar( int width , int height ) {
		imagen = new int[width][height];
		this.width = width;
		this.height = height;
	}
	
	public void setPixel( int x , int y , int pixel ){
		imagen[x][y] = pixel;
	}
	
	public int getPixel( int x , int y ){
		return imagen[x][y];
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
}