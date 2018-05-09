package huellas;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import librerias.ZhangSuen;

public class GestorHuellas {
	
	// Constantes de clase para tipo de huella
	public static int HUELLA_BYN = 0;
	public static int HUELLA_GRIS = 1;
	
	
	private BufferedImage huellaOriginal;		// Almacena la huella original
	private int umbralMedio;						// Almacena el valor medio del umbral
	
	private List<Minucia> minucias;				// Almacena las minucias de la huella
	
	private Stack<HuellaDactilar> historial;		// Pila que almacena los tratamientos realizados para habilitar función deshacer
	
	/**
	 * Constructor por defecto
	 */
	public GestorHuellas() {
		huellaOriginal = null;
		umbralMedio = -1;
		minucias = new ArrayList<Minucia>();
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
	 * Método que devuelve la lista de minucias detectadas en la huella
	 * @return Lista de Minucia
	 */
	public List<Minucia> getListaMinucias(){
		return minucias;
	}
	
	/**
	 * Almacena la minucia pasada por parámetros en la lista
	 * @param minucia la minucia que se quiere almacenar
	 */
	public void almacenarMinucia( Minucia minucia ) {
		minucias.add( minucia );
	}
	
	/**
	 * Crea una nueva lista para almacenar minucas, descartando la anterior
	 */
	public void reiniciarMinucias() {
		minucias = new ArrayList<Minucia>();
	}
			
	/**
	 * Método que almacena en el historial una huella tratada para poder deshacer
	 * @param huella la huella tratada que se quiere almacenar en el hisotorial
	 */
	public void almacenarEnHistorial( HuellaDactilar huella ){
		historial.push( huella );
	}
	
	/**
	 * Método que extrae (y elimina) del historial la última huella tratada
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
	 * Reinicia el hisotiral de huellas
	 */
	public void reiniciarHistorial() {
		historial = new Stack<HuellaDactilar>();
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
		 
			ZhangSuen zs = new ZhangSuen( imgEntrada );
			HuellaDactilar imgAdelgazada = zs.thinImage();
			
			return imgAdelgazada;
	 }
	 
	 /**
	  * Método que detecta las minucias de una huella adelgazada
	  * Las minucias devueltas tienen Crossing Numbre = 1 (corte o final) ó 3 (bifurcación)
	  * @param imgEntrada la huella sobre la que se quieren detectar las minucias
	  */
	 public void detectarMinucias( HuellaDactilar imgEntrada , int limite ) {
		 
		 // TODO: Añadir límites diferenciados para arriba/abajo e izquierda/derecha
		 
		 int p;						// Pixel central
		 int[] Pi = new int[9];		// Píxeles adyacentes
		 int cn;						// Crossing number
		 int aux = 0;
		 
		 for( int x=limite ; x < imgEntrada.getWidth()-limite ; x++ ){
			 for( int y=limite ; y<imgEntrada.getHeight()-limite ; y++ ){
				 
				 p = imgEntrada.getPixel( x , y );
				 aux = 0;
				 
				 if( p == 0 ){
					 
					 Pi[0] = imgEntrada.getPixel( x+1 , y   );
					 Pi[1] = imgEntrada.getPixel( x+1 , y-1 );
					 Pi[2] = imgEntrada.getPixel( x   , y-1 );
					 Pi[3] = imgEntrada.getPixel( x-1 , y-1 );
					 Pi[4] = imgEntrada.getPixel( x-1 , y   );
					 Pi[5] = imgEntrada.getPixel( x-1 , y+1 );
					 Pi[6] = imgEntrada.getPixel( x   , y+1 );
					 Pi[7] = imgEntrada.getPixel( x+1 , y+1 );
					 Pi[8] = imgEntrada.getPixel( x+1 , y   );
					 
					 for( int i = 0 ; i <= 7 ; i++ ){
						 aux = aux + Math.abs(Pi[i] - Pi[i+1]);
					 }
					 
					 cn = aux/2;
					 
					 if (cn == 1 || cn == 3){
						 minucias.add(new Minucia(x, y, cn));
					 }
					 
				 }
				 
			 }
		 }

	 }
	 
	 /**
	  * Método que calculas los ángulos de las minucias
	  * @param imgEntrada la huella sobre la que se quieren detectar los ángulos de las minucias
	  */
	 public void calcularAngulos( HuellaDactilar imgEntrada ) {
		 //TODO: Implementar método calcularAngulos
	 }
	 
}