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

import huellas.GestorHuellas;
import huellas.HuellaDactilar;

import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class Ventana {
	
	// Instancia del gestor de huellas
	private GestorHuellas gh;

	// Frame de la interfaz
	private JFrame frame;
	
	// Paneles donde se muestran las huellas
	JPanel panelBordeIzquierda;
	JPanel panelHuellaIzquierda;
	JPanel panelBordeDerecha;
	JPanel panelHuellaDerecha;
	
	// Botones de la interfaz
	private JButton btnCargarHuella;
	private JButton btnGrises;

	/**
	 * Create the application.
	 */
	public Ventana() {
		initialize();
		
		gh = new GestorHuellas();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds( 100 , 100 , 765 , 620 );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable( false );
		frame.getContentPane().setLayout(null);
		
		// PANEL DE LA HUELLA ORIGINAL
		panelBordeIzquierda = new JPanel();
		panelBordeIzquierda.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Huella original", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordeIzquierda.setBounds(6, 40, 372, 504);
		frame.getContentPane().add(panelBordeIzquierda);
		panelBordeIzquierda.setLayout(null);
		
		panelHuellaIzquierda = new JPanel();
		panelHuellaIzquierda.setBounds(6, 18, 360, 480);
		panelBordeIzquierda.add(panelHuellaIzquierda);
		
		// PANEL DE LA HUELLA TRATADA
		panelBordeDerecha = new JPanel();
		panelBordeDerecha.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Huella tratada", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordeDerecha.setBounds(390, 40, 372, 504);
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
		
		// BOTÓN PARA CONVERTIR LA HUELLA A ESCALA DE GRISES
		btnGrises = new JButton("Grises");
		btnGrises.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				HuellaDactilar huellaGrises = gh.convertirGrises( gh.getHuellaOriginal() );
				BufferedImage huellaAMostrar = gh.convertirRGB( huellaGrises , GestorHuellas.HUELLA_GRIS );
				
				pintarPanelDerecha( huellaAMostrar );
				
			}
		});
		
		btnGrises.setBounds(6, 556, 117, 29);
		frame.getContentPane().add(btnGrises);
		btnGrises.setEnabled( false );
		
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
	
	/**
	 * Pinta en el marco derecho de la interfaz la BufferedImage pasada por parámetros
	 * @param huellaAMostrar la imagen de la huella que se desea mostrar en la interfaz
	 */
	private void pintarPanelDerecha( BufferedImage huellaAMostrar ) {
		Graphics g = panelHuellaDerecha.getGraphics();
		panelHuellaDerecha.paintComponents( g );
		g.drawImage( huellaAMostrar , 0 , 0 , null );
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
