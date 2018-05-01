package gui;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import huellas.GestorHuellas;
import huellas.HuellaDactilar;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JSlider;

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
		
	    sliderUmbral.addChangeListener( new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	          System.out.println("Slider Umbral: " + sliderUmbral.getValue());
	        	
//				HuellaDactilar huellaUmbralizada = gh.umbralizar( huellaDerecha , sliderUmbral.getValue() );
//				huellaDerecha = huellaUmbralizada;
//				
//				pintarPanelDerecha( huellaUmbralizada , GestorHuellas.HUELLA_BYN );
	        	
	        }
	      });
		
		
		// BOTÓN PARA FILTRAR
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
				
			}
		});
		btnAdelgazar.setBounds(390, 232, 135, 29);
		frame.getContentPane().add(btnAdelgazar);
		btnAdelgazar.setEnabled( false );
		
		// BOTÓN MINUCIAS
		btnMinucias = new JButton("Minucias");
		btnMinucias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Copiamos la huella del panel de la derecha a la izquierda
				huellaIzquierda = huellaDerecha;				
				
				// Pintamos la huella de la izquierda
				pintarPanelIzquierda( GestorHuellas.HUELLA_BYN );
				
				// Detectamos las minucias en la huella derecha
//				huellaDerecha = gh.adelgazar( huellaDerecha );
				
				// Pintamos la huella de la derecha
//				pintarPanelDerecha( GestorHuellas.HUELLA_BYN );
				
				// Actualizamos el estado de los botones de la interfaz
				btnMinucias.setEnabled( false );
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
		
		Graphics g = panelHuellaIzquierda.getGraphics();
		panelHuellaIzquierda.paintComponents( g );
		g.drawImage( imagenHuellaAMostrar , 0 , 0 , null );
	}
	
	private void pintarPanelDerecha( int modo ) {
		
		BufferedImage imagenHuellaAMostrar = gh.convertirRGB( huellaDerecha , modo );
		
		Graphics g = panelHuellaDerecha.getGraphics();
		panelHuellaDerecha.paintComponents( g );
		g.drawImage( imagenHuellaAMostrar , 0 , 0 , null );
	}
	
	
	
	
	private void resetear() {
		pintarPanelIzquierda( gh.getHuellaOriginal() );
		
		
		Graphics g = panelHuellaDerecha.getGraphics();
		panelHuellaDerecha.paintComponents(g);
		g.clearRect( 0 , 0 , panelHuellaDerecha.getWidth() , panelHuellaDerecha.getHeight() );
		
		btnReiniciar.setEnabled( false );
		
		btnGrises.setEnabled( true );
		btnEcualizar.setEnabled( false );
		btnUmbralizar.setEnabled( false );
		sliderUmbral.setEnabled( false );
		btnFiltrar.setEnabled( false );
		btnAdelgazar.setEnabled( false );
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana window = new Ventana();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
