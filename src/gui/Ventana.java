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
		
		// BOTÓN PARA CARGAR LA HUELLA
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
		
		btnCargarHuella.setBounds(6, 6, 117, 29);
		frame.getContentPane().add(btnCargarHuella);
		
		// BOTÓN DESHACER
//		btnDeshacer = new JButton("Deshacer");
//		btnDeshacer.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				
//			}
//		});
//		btnDeshacer.setBounds(126, 6, 117, 29);
//		frame.getContentPane().add(btnDeshacer);
//		btnDeshacer.setEnabled( false );
		
		// BOTÓN REINICIAR
//		btnReiniciar = new JButton("Reiniciar");
//		btnReiniciar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				
//				
//			}
//		});
//		btnReiniciar.setBounds(245, 6, 117, 29);
//		frame.getContentPane().add(btnReiniciar);
//		btnReiniciar.setEnabled( false );
		
		// BOTÓN PARA CONVERTIR LA HUELLA A ESCALA DE GRISES
		btnGrises = new JButton("Grises");
		btnGrises.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				HuellaDactilar huellaGrises = gh.convertirGrises( gh.getHuellaOriginal() );
				gh.almacenarEnHistorial( huellaGrises );
				huellaDerecha = huellaGrises;
				
				BufferedImage huellaAMostrar = gh.convertirRGB( huellaGrises , GestorHuellas.HUELLA_GRIS );
				
				pintarPanelDerecha( huellaAMostrar );
				
				btnGrises.setEnabled( false );
				btnEcualizar.setEnabled( true );
				
			}
		});
		
		btnGrises.setBounds(390, 55, 135, 29);
		frame.getContentPane().add(btnGrises);
		btnGrises.setEnabled( false );
		
		// BOTÓN PARA ECUALIZAR
		btnEcualizar = new JButton("Ecualizar");
		btnEcualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BufferedImage huellaAMostrar = gh.convertirRGB( huellaDerecha , GestorHuellas.HUELLA_GRIS );
				pintarPanelIzquierda( huellaAMostrar );
				
				HuellaDactilar huellaEcualizada = gh.ecualizado( huellaDerecha );
				gh.almacenarEnHistorial( huellaEcualizada );
				huellaDerecha = huellaEcualizada;
				
				huellaAMostrar = gh.convertirRGB( huellaDerecha , GestorHuellas.HUELLA_GRIS );
				pintarPanelDerecha( huellaAMostrar );
				
				btnEcualizar.setEnabled( false );
				btnUmbralizar.setEnabled( true );
				sliderUmbral.setEnabled( true );
				sliderUmbral.setValue( gh.getUmbralMedio() );
			}
		});
		btnEcualizar.setBounds(390, 86, 135, 29);
		frame.getContentPane().add(btnEcualizar);
		btnEcualizar.setEnabled( false );
		
		
		// BOTÓN PARA UMBRALIZAR
		btnUmbralizar = new JButton("Umbralizar");
		btnUmbralizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BufferedImage huellaAMostrar = gh.convertirRGB( huellaDerecha , GestorHuellas.HUELLA_GRIS );
				pintarPanelIzquierda( huellaAMostrar );
				
				HuellaDactilar huellaUmbralizada = gh.umbralizar( huellaDerecha , sliderUmbral.getValue() );
				gh.almacenarEnHistorial( huellaUmbralizada );
				huellaDerecha = huellaUmbralizada;
				
				huellaAMostrar = gh.convertirRGB( huellaDerecha , GestorHuellas.HUELLA_BYN );
				pintarPanelDerecha( huellaAMostrar );
				
				btnUmbralizar.setEnabled( false );
				sliderUmbral.setEnabled( false );
				btnFiltrar.setEnabled( true );
				
			}
		});
		btnUmbralizar.setBounds(390, 116, 135, 29);
		frame.getContentPane().add(btnUmbralizar);
		btnUmbralizar.setEnabled( false );
		
		// SLIDER PARA MARCAR EL UMBRAL
		sliderUmbral = new JSlider( 0 , 256 );
		sliderUmbral.setMinorTickSpacing(16);
		sliderUmbral.setPaintLabels( true );
		sliderUmbral.setPaintTicks( true );
		sliderUmbral.setMajorTickSpacing(64);
		sliderUmbral.setBounds(376, 153, 140, 39);
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
				
				BufferedImage huellaAMostrar = gh.convertirRGB( huellaDerecha , GestorHuellas.HUELLA_BYN );
				pintarPanelIzquierda( huellaAMostrar );
				
				HuellaDactilar huellaFiltrada = gh.filtrar( huellaDerecha );
				gh.almacenarEnHistorial( huellaFiltrada );
				huellaDerecha = huellaFiltrada;
				
				huellaAMostrar = gh.convertirRGB( huellaDerecha , GestorHuellas.HUELLA_BYN );
				pintarPanelDerecha( huellaAMostrar );
				
				btnFiltrar.setEnabled( false );
				btnAdelgazar.setEnabled( true );
				
			}
		});
		btnFiltrar.setBounds(386, 204, 117, 29);
		frame.getContentPane().add(btnFiltrar);
		btnFiltrar.setEnabled( false );
		
		// BOTÓN PARA ADELGAZAR
		btnAdelgazar = new JButton("Adelgazar");
		btnAdelgazar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BufferedImage huellaAMostrar = gh.convertirRGB( huellaDerecha , GestorHuellas.HUELLA_BYN );
				pintarPanelIzquierda( huellaAMostrar );
				
				HuellaDactilar huellaAdelgazada = gh.adelgazar( huellaDerecha );
				gh.almacenarEnHistorial( huellaAdelgazada );
				huellaDerecha = huellaAdelgazada;
				
				huellaAMostrar = gh.convertirRGB( huellaDerecha , GestorHuellas.HUELLA_BYN );
				pintarPanelDerecha( huellaAMostrar );
				
				btnAdelgazar.setEnabled( false );
				
			}
		});
		btnAdelgazar.setBounds(399, 245, 117, 29);
		frame.getContentPane().add(btnAdelgazar);
		btnAdelgazar.setEnabled( false );
		
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
	
	private void pintarPanelIzquierda( HuellaDactilar huellaAMostrar , int modo ){
		BufferedImage imagenHuellaAMostrar = gh.convertirRGB( huellaAMostrar , modo );
		
		Graphics g = panelHuellaIzquierda.getGraphics();
		panelHuellaIzquierda.paintComponents( g );
		g.drawImage( imagenHuellaAMostrar , 0 , 0 , null );
	}
	
	/**
	 * Pinta en el marco derecho de la interfaz la BufferedImage pasada por parámetros
	 * @param huellaAMostrar la imagen de la huella que se desea mostrar en la interfaz
	 */
	private void pintarPanelDerecha( BufferedImage huellaAMostrar ) {
		Graphics g = panelHuellaDerecha.getGraphics();
		panelHuellaDerecha.paintComponents( g );
		g.drawImage( huellaAMostrar , 0 , 0 , null );
	}
	
	private void pintarPanelDerecha( HuellaDactilar huellaAMostrar , int modo ){
		BufferedImage imagenHuellaAMostrar = gh.convertirRGB( huellaAMostrar , modo );
		
		Graphics g = panelHuellaDerecha.getGraphics();
		panelHuellaIzquierda.paintComponents( g );
		g.drawImage( imagenHuellaAMostrar , 0 , 0 , null );
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
