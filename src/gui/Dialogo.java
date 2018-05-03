package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Dialogo extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTextArea textArea;

	/**
	 * Create the dialog.
	 */
	public Dialogo() {
		setBounds(200, 200, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		{
			
			textArea = new JTextArea( 14 , 35 );
			contentPanel.add(textArea);
			

			
			{
				JScrollPane scrollPane = new JScrollPane( textArea );
				contentPanel.add(scrollPane);
			}
			
			
			
		}
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			
			{
				JButton closeButton = new JButton("Cerrar");
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						borrarTexto();
						dispose();
						
						
					}
				});
				closeButton.setActionCommand("Close");
				buttonPane.add(closeButton);
			}
			
			
		}
		
		
		
	}
	
	public void imprimirLinea( String linea ) {
		textArea.append(linea + "\n");
	}
	
	public void borrarTexto() {
		textArea.setText( null );
	}

}
