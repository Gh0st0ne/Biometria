package huellas;

import java.awt.image.BufferedImage;
import java.util.Stack;

import librerias.ZhangSuen;

public class GestorHuellas {
	
	// Constantes de clase para tipo de huella
	public static int HUELLA_BYN = 0;
	public static int HUELLA_GRIS = 1;
	
	
	private BufferedImage huellaOriginal;		// Almacena la huella original
	private int umbralMedio;						// Almacena el valor medio del umbral
	
	private Stack<HuellaDactilar> historial;		// Pila que almacena los tratamientos realizados para habilitar función deshacer
	
	/**
	 * Constructor por defecto
	 */
	public GestorHuellas() {
		huellaOriginal = null;
		umbralMedio = -1;
		historial = new Stack<HuellaDactilar>();
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
	 * Método que almacena en el historial una huella tratada para poder deshacer
	 * @param huella la huella tratada que se quiere almacenar en el hisotorial
	 */
	public void almacenarEnHistorial( HuellaDactilar huella ){
		historial.push( huella );
	}
	
	/**
	 * Método que extrae del historial la última huella tratada
	 * @return la última huella tratada
	 */
	public HuellaDactilar recuperarDeHistorial(){
		return historial.pop();
	}
	
	/**
	 * Método que comprueba si hay elementos en el historial
	 * @return 'true' si el historial está vacío, 'false' en caso contrario
	 */
	public boolean historialVacio(){
		return historial.isEmpty();
	}
	
	/**
	 * Método que devuelve el umbral medio de la huella
	 * @return entero que representa el umbral medio
	 */
	public int getUmbralMedio() {
		return umbralMedio;
	}
	
	/**
	 * Método que fija el umbral medio de la huella
	 * @param umbralMedio entero que representa el umbral medio
	 */
	public void setUmbralMedio( int umbralMedio ) {
		this.umbralMedio = umbralMedio;
	}
	
	/**
	 * Convierte una imagen de entrada en un objeto de la clase huella dactilar
	 * @param huellaEntrada la imagen origen
	 * @return objeto HuellaDactilar
	 */
	public HuellaDactilar convertirGrises( BufferedImage imgEntrada ) {
		
		 HuellaDactilar imgSalida = new HuellaDactilar(imgEntrada.getWidth(), imgEntrada.getHeight());
		 for (int x = 0; x < imgEntrada.getWidth(); ++x){
			 for (int y = 0; y < imgEntrada.getHeight(); ++y){
				 int rgb = imgEntrada.getRGB(x, y);
				 int r = (rgb >> 16) & 0xFF;
				 int g = (rgb >> 8) & 0xFF;
				 int b = (rgb & 0xFF);
				 int nivelGris = (r + g + b) / 3;
				 
				 imgSalida.setPixel(x, y, nivelGris);
			 }
		 }
		 return imgSalida;
	}
	
	/**
	 * Convierte una huella dactilar a BufferedImage para su visualización por pantalla
	 * @param huellaEntrada la HuellaDactilar que se desea tratar
	 * @param modo 0 si la huella está en Blanco y Negro
	 * 			   1 si la huella está en escala de grises
	 * @return BufferedImage que contiene al huella para visualizar
	 */
	public BufferedImage convertirRGB( HuellaDactilar imgEntrada , int modo ) {
		
		 BufferedImage imgSalida = new BufferedImage(imgEntrada.getWidth(), imgEntrada.getHeight(), 2);
		 
		 for (int x = 0; x < imgEntrada.getWidth(); ++x) {
			 for (int y = 0; y < imgEntrada.getHeight(); ++y){
				 int valor = imgEntrada.getPixel(x, y);
				 if(modo == 0){
					 valor=valor*255;
				 }
				 int pixelRGB =(255<<24 | valor << 16 | valor << 8 | valor);
				 imgSalida.setRGB(x, y, pixelRGB);
			 }
		}
		return imgSalida;
	}

	/**
	 * Realiza el ecualizado de la huella de entrada
	 * @param huellaEntrada la HuellaDactilar que se desea tratar
	 * @return HuellaDactilar con los valores de grises normalizados según histograma
	 */
	public HuellaDactilar ecualizado( HuellaDactilar imgEntrada ){
		
		 HuellaDactilar imgSalida = new HuellaDactilar(imgEntrada.getWidth(), imgEntrada.getHeight());
		 int width = imgEntrada.getWidth();
		 int height = imgEntrada.getHeight();
		 int tampixel= width*height;
		 int[] histograma = new int[256];
		 int i =0;
		 // Calculamos frecuencia relativa de ocurrencia
		 // de los distintos niveles de gris en la imagen
		 for (int x = 0; x < width; x++){
			 for (int y = 0; y < height; y++){
				 int valor= imgEntrada.getPixel(x, y);
				 histograma[valor]++;
			 }
		 }
		 int sum =0;
		 // Construimos la Lookup table LUT containing scale factor
		 float[] lut = new float[256];
		 for ( i=0; i < 256; ++i ){
			 sum += histograma[i];
			 lut[i] = sum * 255 / tampixel;
		 }
		 
		 
		 // Calculamos el umbral medio
		 int acumulador = 0;
		 int numPixels = width*height;
		 for (int x = 0; x < width; x++){
			 for (int y = 0; y < height; y++){
				 acumulador += imgEntrada.getPixel(x, y);
			 }
		 }

		 umbralMedio = acumulador/numPixels;
		 
		 // Se transforma la imagen utilizando la tabla LUT

		 for (int x = 0; x < width; x++) {
			 for (int y = 0; y < height; y++) {
				 int valor= imgEntrada.getPixel(x, y);
				 int valorNuevo= (int) lut[valor];
				 imgSalida.setPixel(x, y, valorNuevo);
			 }
		 }
		 
		 return imgSalida;
	}
	
	/**
	 * Método que convierte la imagen de la huella a una matriz de blancos y negros según el umbral fijado
	 * @param imgEntrada objeto HuellaDactilar que se desea umbralizar
	 * @param umbral entero que representa el umbral a utilizar
	 * @return objeto HuellaDactilar con la huella umbralizada a blancos y negros según el umbral
	 */
	public HuellaDactilar umbralizar( HuellaDactilar imgEntrada , int umbral ){
		
		HuellaDactilar imgSalida = new HuellaDactilar( imgEntrada.getWidth() , imgEntrada.getHeight() );
		
		 for( int x = 0 ; x < imgEntrada.getWidth() ; x++ ){
			 for( int y = 0 ; y < imgEntrada.getHeight() ; y++ ){
				 int valor = imgEntrada.getPixel( x , y );
				 if( valor < umbral ){
					 imgSalida.setPixel( x , y , 0 );
				 } else {
					 imgSalida.setPixel( x , y , 1 );
				 }
			 }
		 }
		 
		 return imgSalida;
	 }

	 /**
	  * Método para filtrar la HuellaDactilar
	  * @param imgEntrada la HuellaDactilar a filtrar
	  * @return objeto HuellaDactilar con la huella filtrada
	  */
	 public HuellaDactilar filtrar( HuellaDactilar imgEntrada ){
		 
		 HuellaDactilar imgSalida = new HuellaDactilar( imgEntrada.getWidth() , imgEntrada.getHeight() );
		 HuellaDactilar imgAux = new HuellaDactilar( imgEntrada.getWidth() , imgEntrada.getHeight() );
		 
		 int a, b, c ,d, e, f, g, h, p, B1, B2;
		 
		 for( int x = 0 ; x < imgEntrada.getWidth() ; x++ ){
			 for( int y = 0 ; y < imgEntrada.getHeight() ; y++ ){
				 if( x > 0 && x < imgEntrada.getWidth()-1 && y > 0 && y < imgEntrada.getHeight()-1 ){

					 a = imgEntrada.getPixel( x-1 , y-1 );
					 b = imgEntrada.getPixel( x   , y-1 );
					 c = imgEntrada.getPixel( x+1 , y-1 );
					 d = imgEntrada.getPixel( x-1 , y   );
					 e = imgEntrada.getPixel( x+1 , y   );
					 f = imgEntrada.getPixel( x-1 , y+1 );
					 g = imgEntrada.getPixel( x   , y+1 );
					 h = imgEntrada.getPixel( x+1 , y+1 );
					 p = imgEntrada.getPixel( x   , y   );
					 
					 //B1 = p+b.g.(d+e)+d.e.(b+g) + --> OR . --> AND
					 B1 = p | b & g & ( d|e ) | d & e & ( b|g );
				 } else {
					 B1 = imgEntrada.getPixel( x , y );					 
				 }
				 
				 imgAux.setPixel( x , y , B1 );	 
			 }
		 }
		 
		 
		 for( int x = 0 ; x < imgAux.getWidth() ; x++ ){
			 for( int y = 0 ; y < imgAux.getHeight() ; y++ ){
				 if( x > 0 && x < imgAux.getWidth()-1 && y > 0 && y < imgAux.getHeight()-1 ){
					 
					 a = imgAux.getPixel( x-1 , y-1 );
					 b = imgAux.getPixel( x   , y-1 );
					 c = imgAux.getPixel( x+1 , y-1 );
					 d = imgAux.getPixel( x-1 , y   );
					 e = imgAux.getPixel( x+1 , y   );
					 f = imgAux.getPixel( x-1 , y+1 );
					 g = imgAux.getPixel( x   , y+1 );
					 h = imgAux.getPixel( x+1 , y+1 );
					 p = imgAux.getPixel( x   , y   );
					 
					//B2 = p[(a+b+d).(e+g+h)+(b+c+e).(d+f+g)] + --> OR . --> AND
					 B2 = p & ( ( a|b|d ) & ( e|g|h ) & ( b|c|e ) & ( d|f|g ) );
					 
				 } else {
					 B2 = imgAux.getPixel( x , y );					 
				 }
				 
				 imgSalida.setPixel( x , y , B2 ); 
			 }
		 }
		 		 
		 return imgSalida;
	 }
	 
	 /**
	  * Método que adelagaza la huella utilizando el algoritmo ZhangSuen
	  * @param imgEntrada la Huella que se quiere adelgazar
	  * @return objeto HuellaDactilar con la huella adelgazada
	  */
	 public HuellaDactilar adelgazar( HuellaDactilar imgEntrada ) {
		 HuellaDactilar imgSalida = new HuellaDactilar( imgEntrada.getWidth() , imgEntrada.getHeight() );
		 
	        ZhangSuen.grid = new int[imgEntrada.getWidth()][imgEntrada.getHeight()];
		        
		    	for (int i=0; i<imgEntrada.getWidth(); i++){
		    		for (int j=0; j<imgEntrada.getHeight(); j++){
		    			ZhangSuen.grid[i][j] = imgEntrada.getPixel(i, j);
		    		}
		    	}
		    	
		    	ZhangSuen.thinImage();
		    	
		    	for (int i=0; i<imgSalida.getWidth(); i++){
		    		for (int j=0; j<imgSalida.getHeight(); j++){
		    			imgSalida.setPixel(i, j, ZhangSuen.grid[i][j]);
		    		}
		    	}
		 
		 return imgSalida;
	 }
	 
}