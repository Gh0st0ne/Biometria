package huellas;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.PrintWriter;
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
	private List<Double> angulos;					// Almacena los ángulos de las minucias
	
	private Stack<HuellaDactilar> historial;		// Pila que almacena los tratamientos realizados para habilitar función deshacer
	
	/**
	 * Constructor por defecto
	 */
	public GestorHuellas() {
		huellaOriginal = null;
		umbralMedio = -1;
		minucias = new ArrayList<Minucia>();
		angulos = new ArrayList<Double>();
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
	 * Método que devuelve la lista de ángulos de las minucias
	 * @return Lista de Ángulos
	 */
	public List<Double> getListaAngulos(){
		return angulos;
	}
	
	/**
	 * Almacena el ángulo pasada por parámetros en la lista
	 * @param angulo el ángulo a almacenar
	 */
	public void almacenarAngulo( Double angulo ) {
		angulos.add( angulo );
	}
	
	/**
	 * Crea una nueva lista para almacenar ángulos de las minucias, descartando la anterior
	 */
	public void reiniciarAngulos() {
		angulos = new ArrayList<Double>();
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
	  * @param limite el límite para alejarse de los bordes y descartar falsas minucias
	  */
	 public void detectarMinucias( HuellaDactilar imgEntrada , int limite ) {
		 		 
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
		 		 
		 boolean fin = false;
		 int xInicial, yInicial;
		 int x, y;
		 
		 ArrayList<Point> visitados = new ArrayList<Point>();
		 ArrayList<Point> vecinos = new ArrayList<Point>();
		 ArrayList<Point> aux = new ArrayList<Point>();
		 
		 for( int i = 0 ; i < this.minucias.size() ; i++ ){
			 
			 // Las coordenadas iniciales son las de la minucia
			 xInicial = minucias.get( i ).getX();
			 yInicial = minucias.get( i ).getY();
			 
			 // Las coordenadas actuales, son las de la minucia
			 x = xInicial;
			 y = yInicial;
			 
			 // Vaciamos la lista de visitados y añaidmos la minucia a visitados
			 visitados.clear();
			 visitados.add( new Point ( x , y ) );
			 
			 // Añadimos los vecinos de la minucia
			 obtenerVecinos( vecinos , visitados , vecinos , imgEntrada , x , y );
			 
			 // Recorremos los vecinos y los vamos procesando
			 while( !vecinos.isEmpty() ){
				 
				 x = vecinos.get(0).x;
				 y = vecinos.get(0).y;
				 
				 vecinos.remove(0);
			 
				 for( int j = 0 ; j < 6 && !fin ; j++ ){
					 
					 visitados.add( new Point( x , y ) );
					 aux.clear();
					 obtenerVecinos( aux , visitados , vecinos , imgEntrada , x , y );
					 
					 if( aux.isEmpty() )
						 fin = true;
					 else {
					 
						 x = aux.get(0).x;
						 y = aux.get(0).y;
						 
						 if( visitados.contains( new Point( x , y ) ) ){
							 if( aux.size()==1 )
								 fin = true;
							 else{
								 aux.remove(0);
							 
								 x = aux.get(0).x;
								 y = aux.get(0).y;
							 }
						 }
					 }
				 } // fin de bucle for
				 
				 if( fin )
					 fin = false;
				 else{
					 
					 // Calcula los gradientes
					 float Gx =  x - xInicial;
					 float Gy = y - yInicial;
					 
					 // Calcula el ángulo
					 double radianes = Math.atan( -Gy/Gx );
					 double angulo = Math.toDegrees( radianes );
					 
					 // Corregimos el ángulo según el cuadrante donde se encuentra
					 if( Gx < 0 && Gy > 0 )
						 angulo = angulo - 180.0;
					 
					 if( Gx < 0 && Gy <= 0 )
						 angulo = angulo + 180.0;

					 // Añadimos el ángulo a la lista
					 angulos.add( angulo );
					 
				 }
			 }
			 
		 } // Fin de bucle for
		 
	 }
	 
	 /**
	  * Método que obtiene los vecinos de la minucia
	  * @param aux estructura auxiliar
	  * @param visitados lista con los vecinos visitados
	  * @param vecinos lista con los vecinos de la minucia
	  * @param imgEntrada huella tratada
	  * @param x coordenada x de la minucia
	  * @param y coordenada y de la minucia
	  */
	 public void obtenerVecinos( ArrayList<Point> aux , ArrayList<Point> visitados , ArrayList<Point> vecinos , HuellaDactilar imgEntrada , int x , int y ){
		 		 
		 // Obtenemos los píxeles vecinos del pixel actual
		 int pi1 = imgEntrada.getPixel( x+1 , y   );
		 int pi2 = imgEntrada.getPixel( x   , y+1 );
		 int pi3 = imgEntrada.getPixel( x-1 , y   );
		 int pi4 = imgEntrada.getPixel( x   , y-1 );
		 int pi5 = imgEntrada.getPixel( x+1 , y-1 );
		 int pi6 = imgEntrada.getPixel( x-1 , y-1 );
		 int pi7 = imgEntrada.getPixel( x-1 , y+1 );
		 int pi8 = imgEntrada.getPixel( x+1 , y+1 );
		 
		 // Creamos puntos con las coordenadas de los píxeles actuales
		 Point pu1 = new Point( x+1 , y   );
		 Point pu2 = new Point( x   , y+1 );
		 Point pu3 = new Point( x-1 , y   );
		 Point pu4 = new Point( x   , y-1 );
		 Point pu5 = new Point( x+1 , y-1 );
		 Point pu6 = new Point( x-1 , y-1 );
		 Point pu7 = new Point( x-1 , y-1 );
		 Point pu8 = new Point( x+1 , y+1 );
		 
		 
		 if( pi1 == 0 ) {
			 if( !visitados.contains( pu1 ) && !vecinos.contains( pu1 ) )
				 aux.add( pu1 );
		 }

		 if( pi2 == 0 ) {
			 if( !visitados.contains( pu2 ) && !vecinos.contains( pu2 ) )
				 aux.add( pu2 );
		 }
		 
		 if( pi3 == 0 ) {
			 if( !visitados.contains( pu3 ) && !vecinos.contains( pu3 ) )
				 aux.add( pu3 );
		 }
		 
		 if( pi4 == 0 ) {
			 if( !visitados.contains( pu4 ) && !vecinos.contains( pu4 ) )
				 aux.add( pu4 );
		 }
		 
		 if( pi5 == 0 ) {
			 if( !visitados.contains( pu5 ) && !vecinos.contains( pu5 ) )
				 aux.add( pu5 );
		 }
		 
		 if( pi6 == 0 ) {
			 if( !visitados.contains( pu6 ) && !vecinos.contains( pu6 ) )
				 aux.add( pu6 );
		 }
		 
		 if( pi7 == 0 ) {
			 if( !visitados.contains( pu7 ) && !vecinos.contains( pu7 ) )
				 aux.add( pu7 );
		 }
		 
		 if( pi8 == 0 ) {
			 if( !visitados.contains( pu8 ) && !vecinos.contains( pu8 ) )
				 aux.add( pu8 );
		 }
		 
	 }
	 
	 /**
	  * Método que exporta los datos de la huella a un fichero csv
	  * @param ruta la ruta del fichero a crear
	  */
	 public boolean escribirFichero( String ruta ) {
		 
		 boolean correcto = false;
		 
         FileWriter fichero = null;
         PrintWriter pw = null;
         Minucia aux;
         Double angulo;

         try {
             fichero = new FileWriter( ruta );
             pw = new PrintWriter( fichero );
             
             pw.println( "i,X,Y,Ang" );
             
			for( int i = 0 ; i < this.getListaMinucias().size() ; i++ ) {
				aux = this.getListaMinucias().get( i );
				angulo = this.getListaAngulos().get( i );
				pw.println( i + "," + aux.getX() + "," + aux.getY() + "," + angulo );
			}
			
			correcto = true;

         } catch (Exception e) {
             e.printStackTrace();
        } finally {
	           try {
		            
	        	   	if (null != fichero)
	               fichero.close();
	            } catch (Exception e2) {
	               e2.printStackTrace();
	            }
	         }
         return correcto;
	     }
	 
	 
}