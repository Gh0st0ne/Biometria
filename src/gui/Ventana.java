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
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class Ventana {

	private JFrame frame;

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

	/**
	 * Create the application.
	 */
	public Ventana() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 765, 595);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Panel de huella original
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Huella original", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(6, 47, 372, 504);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panelHuellaOriginal = new JPanel();
		panelHuellaOriginal.setBounds(6, 18, 360, 480);
		panel.add(panelHuellaOriginal);
		
		// Panel de huella tratada
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Huella tratada", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(390, 47, 372, 504);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panelHuellaTratada = new JPanel();
		panelHuellaTratada.setBounds(6, 18, 360, 480);
		panel_1.add(panelHuellaTratada);
		
		// Botón para cargar la huella original
		JButton btnCargarHuella = new JButton("Cargar Huella");
		btnCargarHuella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Creamos el objeto JFileChooser
		        JFileChooser fc = new JFileChooser();
		        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter( "JPG , PNG & GIF" , "jpg" , "png" , "gif" );
		        fc.setFileFilter( filtroImagen );
		        
		        // Abrimos la ventana, guardamos la opcion seleccionada por el usuario
		        int seleccion = fc.showOpenDialog( frame );
		        
		        //Si el usuario, pincha en aceptar
		        if( seleccion == JFileChooser.APPROVE_OPTION ){
		         
		            //Seleccionamos el fichero
		            File fichero = fc.getSelectedFile();
		         
		            try {
						BufferedImage huella = ImageIO.read( new File( fichero.getAbsolutePath() ) );
						Graphics g = panelHuellaOriginal.getGraphics();
						panelHuellaOriginal.paintComponents( g );
						g.drawImage( huella , 0 , 0 , null );	
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	            
		        }
			}
		});
		
		btnCargarHuella.setBounds(6, 6, 117, 29);
		frame.getContentPane().add(btnCargarHuella);
		
	}
}
