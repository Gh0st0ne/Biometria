package huellas;

import java.awt.image.BufferedImage;

public class GestorHuellas {
	
	// Constantes de clase para tipo de huella
	public static int HUELLA_BYN = 0;
	public static int HUELLA_GRIS = 1;
	
	
	private BufferedImage huellaOriginal;	// Almacena la huella original
	
	/**
	 * Constructor por defecto
	 */
	public GestorHuellas() {
		huellaOriginal = null;
	}
	
	/**
	 * Fija la huella original con la BufferedImage pasada por parámetros
	 * @param huellaOriginal huella original sin ningún tratamiento
	 */
	public void setHuellaOriginal( BufferedImage huellaOriginal ) {
		this.huellaOriginal = huellaOriginal;
	}
	
	/**
	 * Recupera la huella original sin tratamientos
	 * @return BufferedImage que representa la huella original
	 */
	public BufferedImage getHuellaOriginal() {
		return huellaOriginal;
	}
	
	/**
	 * Convierte una imagen de entrada en un objeto de la clase huella dactilar
	 * @param huellaEntrada la imagen origen
	 * @return objeto HuellaDactilar
	 */
	public HuellaDactilar convertirGrises( BufferedImage huellaEntrada ) {
		
		HuellaDactilar huellaSalida = new HuellaDactilar( huellaEntrada.getHeight(), huellaEntrada.getWidth() );
		
		for( int alto = 0 ; alto < huellaEntrada.getHeight() ; alto++ ) {
			
			for( int ancho = 0 ; ancho < huellaEntrada.getWidth() ; ancho++ ) {
				
				int rgb = huellaEntrada.getRGB( ancho , alto );
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);
				int nivelGris = (r + g + b) / 3;
				
				huellaSalida.setPixel( alto , ancho , nivelGris );	
			}
			
		}
		
		return huellaSalida;
	}
	
	/**
	 * Convierte una huella dactilar a BufferedImage para su visualización por pantalla
	 * @param huellaEntrada la HuellaDactilar que se desea mostrar
	 * @param modo 0 si la huella está en Blanco y Negro
	 * 			   1 si la huella está en escala de grises
	 * @return BufferedImage que contiene al huella para visualizar
	 */
	public BufferedImage convertirRGB( HuellaDactilar huellaEntrada , int modo ) {
		
		BufferedImage huellaSalida = new BufferedImage( huellaEntrada.getAncho() , huellaEntrada.getAlto() , BufferedImage.TYPE_INT_ARGB );
		
		for( int alto = 0 ; alto < huellaEntrada.getAlto() ; alto++ ) {
			
			for( int ancho = 0 ; ancho < huellaEntrada.getAncho() ; ancho++ ) {

				int valor = huellaEntrada.getPixel( alto , ancho );
				
				if( modo == 0 ) {
					valor = valor * 255;
				}
				
				int pixelRGB = (255 << 24 | valor << 16 | valor << 8 | valor);
				
				huellaSalida.setRGB( ancho , alto , pixelRGB );
			}
				
		}
				
		return huellaSalida;		
	}
	

}