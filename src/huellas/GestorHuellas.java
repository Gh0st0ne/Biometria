package huellas;

import java.awt.image.BufferedImage;
import java.util.Stack;

public class GestorHuellas {
	
	// Constantes de clase para tipo de huella
	public static int HUELLA_BYN = 0;
	public static int HUELLA_GRIS = 1;
	
	
	private BufferedImage huellaOriginal;	// Almacena la huella original
	private int umbralMedio;						// Almacena el valor medio del umbral
	
	// Pila que almacena los tratamientos realizados para habilitar función deshacer
	private Stack<HuellaDactilar> historial;
	
	/**
	 * Constructor por defecto
	 */
	public GestorHuellas() {
		huellaOriginal = null;
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
	
	public int getUmbralMedio() {
		return umbralMedio;
	}
	
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
	
	 public HuellaDactilar umbralizar(HuellaDactilar imgEntrada, int umbral){
		 HuellaDactilar imgSalida = new HuellaDactilar(imgEntrada.getWidth(), imgEntrada.getHeight());
		 for (int x = 0; x < imgEntrada.getWidth(); ++x) {
			 for (int y = 0; y < imgEntrada.getHeight(); ++y){
				 int valor= imgEntrada.getPixel(x, y);
				 if(valor< umbral ){
					 imgSalida.setPixel(x, y, 1);
				 } else{
					 imgSalida.setPixel(x, y, 0);
				 }
			 }
		 }
		 return imgSalida;
	 }

	 
	 public HuellaDactilar filtrar (HuellaDactilar imgEntrada){
		 HuellaDactilar imgSalida = new HuellaDactilar(imgEntrada.getWidth(), imgEntrada.getHeight());

		 return imgSalida;
	 }
	 
}