package huellas;

/**
 * Clase Minucia
 */
public class Minucia {
	
	private int x;		/** posición en eje X que ocupa la minucia */
	private int y;		/** posición en eje Y que ocupa la minucia */
	private int tipo;	/** entero que representa el tipo de minucia según su Crossing Number
						  *		0 -> Punto Aislado
						  *		1 -> Corte (DE INTERÉS EN BIOMETRÍA)
						  *		2 -> Continuación
						  *		3 -> Bifurcación (DE INTERÉS EN BIOMETRÍA)
						  *		4 -> Punto de cruce
						  */
	
	/**
	 * Constructor paramétrico
	 * @param x posición en eje X
	 * @param y posición en eje Y
	 * @param tipo el tipo de minucia según su Crossing Number
	 */
	public Minucia( int x , int y , int tipo ){
		this.x = x;
		this.y = y;
		this.tipo = tipo;
	}
	
	/**
	 * Devuelve la posición en X de la minucia
	 * @return entero que representa la posición en X
	 */
	public int getX() {
		return x;
	}

	/**
	 * Fija la posición en X de la minucia
	 * @param x entero que representa la posición en X
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Devuelve la posición en Y de la minucia
	 * @return entero que representa la posición en Y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Fija la posición en Y de la minucia
	 * @param x entero que representa la posición en Y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Devuelve el tipo de la minucia según su Crossing Number
	 * @return entero que representa el tipo de la minucia
	 */
	public int getTipo() {
		return tipo;
	}
	
	/**
	 * Fija el tipo de la minucia según su Crossing Number
	 * @param entero que representa el tipo de la minucia
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}