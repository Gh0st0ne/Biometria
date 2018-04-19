package huellas;

public class HuellaDactilar {
	
	private int alto;
	private int ancho;
	private int[][] huella;

	public HuellaDactilar( int alto , int ancho ){
		huella = new int[alto][ancho];
		this.alto = alto;
		this.ancho = ancho;
	}
	
	public void setPixel( int x , int y , int valorPixel ){
		huella[x][y] = valorPixel;
	}
	
	public int getPixel( int x , int y ){
		return huella[x][y];
	}
	
	public int getAlto(){
		return this.alto;
	}
	
	public int getAncho(){
		return this.ancho;
	}
	
	public void mostrar() {
		
		for( int filas = 0 ; filas < alto ; filas++ ) {
			for( int columnas = 0 ; columnas < ancho ; columnas++ ) {
				System.out.print( "X " );
			}
			System.out.println();
		}
		
	}
	
	public static void main(String[] args) {
		
		HuellaDactilar huella = new HuellaDactilar( 5 , 10 );
		System.out.println( "El alto es " + huella.getAlto() );
		System.out.println( "El ancho es " + huella.getAncho() );
		
		huella.mostrar();
		
	}
	
}