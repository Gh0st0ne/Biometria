package gui;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import huellas.GestorHuellas;
import huellas.HuellaDactilar;
import huellas.Minucia;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JLabel;

public class Ventana {
	
	// Instancia del gestor de huellas
	private GestorHuellas gh;

	// Frame de la interfaz
	private JFrame frame;
	
	// Paneles donde se muestran las huellas
	private JPanel panelBordeIzquierda;
	private JPanel panelHuellaIzquierda;
	private JPanel panelBordeDerecha;
	private JPanel panelHuellaDerecha;
	
	// Graphics del JPanel utilizados para pintar en la interfaz
	private Graphics gIzquierda;
	private Graphics gDerecha;
	
	// Huellas mostradas en la interfaz
	HuellaDactilar huellaIzquierda;
	HuellaDactilar huellaDerecha;
	
	// Botones de la interfaz
	private JButton btnCargarHuella;
	private JButton btnDeshacer;
	private JButton btnReiniciar;
	private JButton btnGrises;
	private JButton btnEcualizar;
	private JButton btnUmbralizar;
	private JSlider sliderUmbral;
	private JButton btnFiltrar;
	private JButton btnAdelgazar;
	private JLabel lblLmite;
	private JSpinner spinnerLimite;
	private JButton btnMinucias;
	private JButton btnAngulos;


	/**
	 * Create the application.
	 */
	public Ventana() {
		initialize();
		
		gh = new GestorHuellas();
		huellaIzquierda = null;
		huellaDerecha = null;
		
		frame.setVisible( true );
		
		gIzquierda = panelHuellaIzquierda.getGraphics();
		panelHuellaIzquierda.paintComponents( gIzquierda );
		
		gDerecha = panelHuellaDerecha.getGraphics();
		panelHuellaDerecha.paintComponents( gDerecha );
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds( 100 , 100 , 920 , 570 );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable( false );
		frame.getContentPane().setLayout(null);
		
		// PANEL DE LA HUELLA IZQUIERDA
		panelBordeIzquierda = new JPanel();
		panelBordeIzquierda.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Huella original", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordeIzquierda.setBounds(6, 40, 372, 504);
		frame.getContentPane().add(panelBordeIzquierda);
		panelBordeIzquierda.setLayout(null);
		
		panelHuellaIzquierda = new JPanel();
		panelHuellaIzquierda.setBounds(6, 18, 360, 480);
		panelBordeIzquierda.add(panelHuellaIzquierda);
		
		// PANEL DE LA HUELLA DERECHA
		panelBordeDerecha = new JPanel();
		panelBordeDerecha.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Huella tratada", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordeDerecha.setBounds(542, 40, 372, 504);
		frame.getContentPane().add(panelBordeDerecha);
		panelBordeDerecha.setLayout(null);
		
		panelHuellaDerecha = new JPanel();
		panelHuellaDerecha.setBounds(6, 18, 360, 480);
		panelBordeDerecha.add(panelHuellaDerecha);
	
		// Listener que ejecuta cuando se restaura la ventana y pinta los paneles de nuevo en la interfaz
		frame.addWindowListener( new WindowAdapter() {
			
			//TODO: Pintar huellas al restaurar la ventana
			
			public void windowDeiconified(WindowEvent e) {
	            System.out.println("Estoy restaurando");
	            
	            if( btnGrises.isEnabled() ) {
		            System.out.println("Botón grises enabled");
		            
		            // Ultimo paso ejecutado CARGAR IMAGEN
		            
		            panelHuellaIzquierda.repaint();
		            
	            } else if( btnEcualizar.isEnabled() ) {
		            System.out.println("Botón ecualizar enabled");
		            
		            // Ultimo paso ejecutado GRISES
	            	
	            } else if( btnUmbralizar.isEnabled() ) {
		            System.out.println("Botón umbralizar enabled");
		            
		            // Ultimo paso ejecutado ECUALIZAR
		            
	            	
	            } else if( btnFiltrar.isEnabled() ) {
		            System.out.println("Botón filtrar enabled");
		            
		            // Ultimo paso ejecutado UMBRALIZAR
	            	
	            } else if( btnAdelgazar.isEnabled() ) {
		            System.out.println("Botón adelgazar enabled");
		            
		            // Ultimo paso ejecutado FILTRAR
	            	
	            } else if( btnMinucias.isEnabled() ) {
		            System.out.println("Botón minucias enabled");
		            
		            // Ultimo paso ejecutado ADELGAZAR
	            	
	            } else if( btnAngulos.isEnabled() ) {
		            System.out.println("Botón ángulos enabled");
		            
		            // Ultimo paso ejecutado MINUCIAS
	            	
	            }
	            
	        }
			
		});
		
		
		// ===== BOTÓN PARA CARGAR LA HUELLA =====
		btnCargarHuella = new JButton("Cargar Huella");
		btnCargarHuella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Creamos el objeto JFileChooser
		        JFileChooser fc = new JFileChooser();
		        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter( "JPG , PNG & GIF" , "jpg" , "png" , "gif" );
		        fc.setFileFilter( filtroImagen );
		        
		        // Abrimos la ventana, guardamos la opcion seleccionada por el usuario
		        int seleccion = fc.showOpenDialog( frame );
		        
		        // Si el usuario, pincha en aceptar
		        if( seleccion == JFileChooser.APPROVE_OPTION ){
		         
		            //Seleccionamos el fichero
		            File fichero = fc.getSelectedFile();
		         
		            try {
		            	
						BufferedImage huella = ImageIO.read( new File( fichero.getAbsolutePath() ) );
						
						gh.setHuellaOriginal( huella );
						
						pintarPanelIzquierda( huella );
						
						btnGrises.setEnabled( true );
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	            
		        }
			}
		});
		
		btnCargarHuella.setBounds(7, 7, 117, 29);
		frame.getContentPane().add(btnCargarHuella);
		
		// BOTÓN DESHACER
		btnDeshacer = new JButton("Deshacer");
		btnDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				deshacer();
				
			}
		});
		btnDeshacer.setBounds(124, 7, 117, 29);
		frame.getContentPane().add(btnDeshacer);
		btnDeshacer.setEnabled( false );
		
		// BOTÓN REINICIAR
		btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				resetear();
				
			}
		});
		btnReiniciar.setBounds(241, 7, 117, 29);
		frame.getContentPane().add(btnReiniciar);
		btnReiniciar.setEnabled( false );
		
		// BOTÓN PARA CONVERTIR LA HUELLA A ESCALA DE GRISES
		btnGrises = new JButton("Grises");
		btnGrises.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Convertimos la huella original a Grises y la almacenamos en la huella derecha
				huellaDerecha = gh.convertirGrises( gh.getHuellaOriginal() );

				// Pinta en el panel de la derecha la huella de la derecha
				pintarPanelDerecha( GestorHuellas.HUELLA_GRIS );

				// Actualizamos el estado de los botones de la interfaz
				btnGrises.setEnabled( false );
				btnEcualizar.setEnabled( true );
				btnReiniciar.setEnabled( true );
				
			}
		});
		
		btnGrises.setBounds(390, 55, 135, 29);
		frame.getContentPane().add(btnGrises);
		btnGrises.setEnabled( false );
		
		// BOTÓN PARA ECUALIZAR
		btnEcualizar = new JButton("Ecualizar");
		btnEcualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Copiamos la huella del panel de la derecha a la izquierda
				huellaIzquierda = huellaDerecha;
				
				// Pintamos la huella de la izquierda
				pintarPanelIzquierda( GestorHuellas.HUELLA_GRIS );
				
				// Aplicamos el ecualizado a la huella derecha
				huellaDerecha = gh.ecualizado( huellaDerecha );

				// Pintamos la huella de la derecha
				pintarPanelDerecha( GestorHuellas.HUELLA_GRIS );
				
				// Actualizamos el estado de los botones de la interfaz
				btnEcualizar.setEnabled( false );
				btnUmbralizar.setEnabled( true );
				sliderUmbral.setEnabled( true );
				sliderUmbral.setValue( gh.getUmbralMedio() );
				
			}
		});
		btnEcualizar.setBounds(390, 83, 135, 29);
		frame.getContentPane().add(btnEcualizar);
		btnEcualizar.setEnabled( false );
		
		
		// BOTÓN PARA UMBRALIZAR
		btnUmbralizar = new JButton("Umbralizar");
		btnUmbralizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// La huella del panel izquierdo desaparece de la interfaz, se almacena en el historial de deshacer
				gh.almacenarEnHistorial( huellaIzquierda );

				// Copiamos la huella del panel de la derecha a la izquierda
				huellaIzquierda = huellaDerecha;				
				
				// Pintamos la huella de la izquierda
				pintarPanelIzquierda( GestorHuellas.HUELLA_GRIS );
				
				// Aplicamos el umbralizado a la huella derecha
				huellaDerecha = gh.umbralizar( huellaDerecha , sliderUmbral.getValue() );
				
				// Pintamos la huella de la derecha
				pintarPanelDerecha( GestorHuellas.HUELLA_BYN );
				
				// Actualizamos el estado de los botones de la interfaz
				btnUmbralizar.setEnabled( false );
				sliderUmbral.setEnabled( false );
				btnFiltrar.setEnabled( true );

				// Se activa la función deshacer
				btnDeshacer.setEnabled( true );

				
			}
		});
		btnUmbralizar.setBounds(390, 111, 135, 29);
		frame.getContentPane().add(btnUmbralizar);
		btnUmbralizar.setEnabled( false );
		
		// SLIDER PARA MARCAR EL UMBRAL
		sliderUmbral = new JSlider( 0 , 256 );
		sliderUmbral.setMinorTickSpacing(16);
		sliderUmbral.setPaintLabels( true );
		sliderUmbral.setPaintTicks( true );
		sliderUmbral.setMajorTickSpacing(64);
		sliderUmbral.setBounds(385, 153, 145, 39);
		frame.getContentPane().add(sliderUmbral);
		sliderUmbral.setEnabled( false );
		
		
		// BOTÓN PARA FILTRAR
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// La huella del panel izquierdo desaparece de la interfaz, se almacena en el historial de deshacer
				gh.almacenarEnHistorial( huellaIzquierda );

				// Copiamos la huella del panel de la derecha a la izquierda
				huellaIzquierda = huellaDerecha;				
				
				// Pintamos la huella de la izquierda
				pintarPanelIzquierda( GestorHuellas.HUELLA_BYN );
				
				// Aplicamos el filtrado a la huella derecha
				huellaDerecha = gh.filtrar( huellaDerecha );
				
				// Pintamos la huella de la derecha
				pintarPanelDerecha( GestorHuellas.HUELLA_BYN );
				
				// Actualizamos el estado de los botones de la interfaz
				btnFiltrar.setEnabled( false );
				btnAdelgazar.setEnabled( true );
				
			}
		});
		btnFiltrar.setBounds(390, 204, 135, 29);
		frame.getContentPane().add(btnFiltrar);
		btnFiltrar.setEnabled( false );
		
		// BOTÓN PARA ADELGAZAR
		btnAdelgazar = new JButton("Adelgazar");
		btnAdelgazar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// La huella del panel izquierdo desaparece de la interfaz, se almacena en el historial de deshacer
				gh.almacenarEnHistorial( huellaIzquierda );

				// Copiamos la huella del panel de la derecha a la izquierda
				huellaIzquierda = huellaDerecha;				
				
				// Pintamos la huella de la izquierda
				pintarPanelIzquierda( GestorHuellas.HUELLA_BYN );
				
				// Aplicamos el adelgazado a la huella derecha
				huellaDerecha = gh.adelgazar( huellaDerecha );
				
				// Pintamos la huella de la derecha
				pintarPanelDerecha( GestorHuellas.HUELLA_BYN );
				
				// Actualizamos el estado de los botones de la interfaz
				btnAdelgazar.setEnabled( false );
				btnMinucias.setEnabled( true );
				lblLmite.setEnabled( true );
				spinnerLimite.setEnabled( true );
				
			}
		});
		btnAdelgazar.setBounds(390, 232, 135, 29);
		frame.getContentPane().add(btnAdelgazar);
		btnAdelgazar.setEnabled( false );
		
		// SPINNER PARA MARCAR LOS LÍMITES AL DETECTAR MINUCIAS
		lblLmite = new JLabel( "Límite" );
		lblLmite.setBounds(410, 331, 61, 16);
		frame.getContentPane().add(lblLmite);
		lblLmite.setEnabled( false );
		
		spinnerLimite = new JSpinner();
		spinnerLimite.setBounds(450, 326, 60, 26);
		spinnerLimite.setValue( 10 );
		frame.getContentPane().add( spinnerLimite );
		spinnerLimite.setEnabled( false );
		
		// TODO: dibujar límite en huella y modificar al variar el valor
		
		// BOTÓN MINUCIAS
		btnMinucias = new JButton("Minucias");
		btnMinucias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// La huella del panel izquierdo desaparece de la interfaz, se almacena en el historial de deshacer
				gh.almacenarEnHistorial( huellaIzquierda );

				// Copiamos la huella del panel de la derecha a la izquierda
				huellaIzquierda = huellaDerecha;				
				
				// Pintamos la huella de la izquierda
				pintarPanelIzquierda( GestorHuellas.HUELLA_BYN );
				
				// Detectamos las minucias en la huella derecha
				gh.detectarMinucias( huellaDerecha , (int) spinnerLimite.getValue() );
				
				//TODO: Pintar minucias en interfaz
				
				// Pintamos la huella de la derecha
//				pintarPanelDerecha( GestorHuellas.HUELLA_BYN );
				
				// Actualizamos el estado de los botones de la interfaz
				btnMinucias.setEnabled( false );
				lblLmite.setEnabled( false );
				spinnerLimite.setEnabled( false );
				btnAngulos.setEnabled( true );
				
			}
		});
		btnMinucias.setBounds(390, 261, 135, 29);
		frame.getContentPane().add(btnMinucias);
		btnMinucias.setEnabled( false );
		
		// BOTÓN ÁNGULOS
		btnAngulos = new JButton("Ángulos");
		btnAngulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Creamos el cuadro de diálogo y lo mostramos
				Dialogo dialog = new Dialogo();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				
				
				// Imprimimos el contenido a mostrar en el diálogo
				dialog.imprimirLinea( "INFORMACIÓN SOBRE MINUCIAS:" );
				dialog.imprimirLinea( "---------------------------------" );
				dialog.imprimirLinea( "Número de minucias detectadas: " + gh.getListaMinucias().size() );
				
				Minucia aux;
				for( int i = 0 ; i < gh.getListaMinucias().size() ; i++ ) {
					aux = gh.getListaMinucias().get( i );
					dialog.imprimirLinea( "  " + i + " en (" + aux.getX() + "," + aux.getY() + ")" );
				}
				
				
				//TODO: Imprimir contenido en diálogo
				
			}
		});
		btnAngulos.setBounds(390, 290, 135, 29);
		frame.getContentPane().add(btnAngulos);
		btnAngulos.setEnabled( false );
		
	}
	
	/**
	 * Pinta en el marco izquierdo de la interfaz la BufferedImage pasada por parámetros
	 * @param huellaAMostrar la imagen de la huella que se desea mostrar en la interfaz
	 */
	private void pintarPanelIzquierda( BufferedImage huellaAMostrar ) {
		Graphics g = panelHuellaIzquierda.getGraphics();
		panelHuellaIzquierda.paintComponents( g );
		g.drawImage( huellaAMostrar , 0 , 0 , null );
	}
	

	private void pintarPanelIzquierda( int modo ) {
		
		BufferedImage imagenHuellaAMostrar = gh.convertirRGB( huellaIzquierda , modo );
		
//		gIzquierda = panelHuellaIzquierda.getGraphics();
//		panelHuellaIzquierda.paintComponents( gIzquierda );
		gIzquierda.drawImage( imagenHuellaAMostrar , 0 , 0 , null );
	}
	
	private void pintarPanelDerecha( int modo ) {
		
		BufferedImage imagenHuellaAMostrar = gh.convertirRGB( huellaDerecha , modo );
		
//		gDerecha = panelHuellaDerecha.getGraphics();
//		panelHuellaDerecha.paintComponents( gDerecha );
		gDerecha.drawImage( imagenHuellaAMostrar , 0 , 0 , null );
	}
	
	
	
	
	private void resetear() {
		
		// Pinta huella original en el panel izquierdo
		pintarPanelIzquierda( gh.getHuellaOriginal() );
		
		// Borramos lo pintado en el panel de la derecha
		Graphics g = panelHuellaDerecha.getGraphics();
		panelHuellaDerecha.paintComponents(g);
		g.clearRect( 0 , 0 , panelHuellaDerecha.getWidth() , panelHuellaDerecha.getHeight() );
		
		// Eliminamos el hisotiral de deshacer
		gh.reiniciarHistorial();
		
		// Desactivamos botones de reiniciar e historial
		btnReiniciar.setEnabled( false );
		btnDeshacer.setEnabled( false );
		
		// Colocamos el estado de inicio de los botones de la interfaz
		btnGrises.setEnabled( true );
		btnEcualizar.setEnabled( false );
		btnUmbralizar.setEnabled( false );
		sliderUmbral.setEnabled( false );
		btnFiltrar.setEnabled( false );
		btnAdelgazar.setEnabled( false );
		btnMinucias.setEnabled( false );
		lblLmite.setEnabled( false );
		spinnerLimite.setEnabled( false );
		btnAngulos.setEnabled( false );
		
	}
	
	private void deshacer() {
		
		if( btnFiltrar.isEnabled() ) {	// PREVIO    --> Panel izquierdo: Ecualizada	Panel derecho: Umbralizada	Historial: Grises
										// RESULTADO --> Panel izquierdo: Grises	Panel derecho: Ecualizada	Historial: -
			
			// En el panel derecho mostramos el contenido del izquierdo
			huellaDerecha = huellaIzquierda;
			pintarPanelDerecha( GestorHuellas.HUELLA_GRIS );
			
			// Recuperamos para el panel izquierdo la huella en grises
			huellaIzquierda = gh.recuperarDeHistorial();
			pintarPanelIzquierda( GestorHuellas.HUELLA_GRIS );
			
			// Cambiamos el estado de los botones
			btnUmbralizar.setEnabled( true );
			sliderUmbral.setEnabled( true );
			btnFiltrar.setEnabled( false );
			
			// Ya no podemos deshacer más pasos, desactivamos el botón
			btnDeshacer.setEnabled( false );
			
		} else if( btnAdelgazar.isEnabled() ) {	// PREVIO    --> Panel izquierdo: Umbralizada	Panel derecho: Filtrada	Historial: Ecualizada/Grises
												// RESULTADO --> Panel izquierdo: Ecualizada	Panel derecho: Umbralizada	Historial: Grises
			
			// En el panel derecho mostramos el contenido del izquierdo
			huellaDerecha = huellaIzquierda;
			pintarPanelDerecha( GestorHuellas.HUELLA_BYN );
			
			// Recuperamos para el panel izquierdo la huella ecualizada
			huellaIzquierda = gh.recuperarDeHistorial();
			pintarPanelIzquierda( GestorHuellas.HUELLA_GRIS );
			
			// Cambiamos el estado de los botones
			btnFiltrar.setEnabled( true );
			btnAdelgazar.setEnabled( false );
			
		} else if( btnMinucias.isEnabled() ) {	// PREVIO    --> Panel izquierdo: Filtrada	Panel derecho: Adelgazada	Historial: Umbralizada/Ecualizada/Grises
												// RESULTADO --> Panel izquierdo: Umbralizada	Panel derecho: Filtrada	Historial: Ecualizada/Grises
			
			// En el panel derecho mostramos el contenido del izquierdo
			huellaDerecha = huellaIzquierda;
			pintarPanelDerecha( GestorHuellas.HUELLA_BYN );
			
			// Recuperamos para el panel izquierdo la huella ecualizada
			huellaIzquierda = gh.recuperarDeHistorial();
			pintarPanelIzquierda( GestorHuellas.HUELLA_BYN );
			
			// Cambiamos el estado de los botones
			btnAdelgazar.setEnabled( true );
			btnMinucias.setEnabled( false );
			lblLmite.setEnabled( false );
			spinnerLimite.setEnabled( false );
			
		} else if( btnAngulos.isEnabled() ) {	// PREVIO    --> Panel izquierdo: Adelgazada	Panel derecho: Minucias	Historial: Filtrada/Umbralizada/Ecualizada/Grises
												// RESULTADO --> Panel izquierdo: Filtrada	Panel derecho: Adelgazada	Historial: Umbralizada/Ecualizada/Grises
			
			// En el panel derecho mostramos el contenido del izquierdo
			huellaDerecha = huellaIzquierda;
			pintarPanelDerecha( GestorHuellas.HUELLA_BYN );
			
			// Recuperamos para el panel izquierdo la huella ecualizada
			huellaIzquierda = gh.recuperarDeHistorial();
			pintarPanelIzquierda( GestorHuellas.HUELLA_BYN );
			
			// Cambiamos el estado de los botones
			btnMinucias.setEnabled( true );
			lblLmite.setEnabled( true );
			spinnerLimite.setEnabled( true );
			btnAngulos.setEnabled( false );
			
		}
		
	}
	
	
	private void pintarMinucias() {
		// TODO: Implementar pintar minucias
	}
	
	
	
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana window = new Ventana();
//					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
