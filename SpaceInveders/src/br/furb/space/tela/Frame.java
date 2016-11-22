package br.furb.space.tela;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import br.furb.space.ambiente.Mundo;
import br.furb.space.config.Configuration;
import br.furb.space.config.EnNivelJogo;

/**
 * Classe responsável por criar tela e suas respectivas configurações.
 * 
 * @author helinton.steffens
 *
 */
public class Frame extends JFrame{

	private static final long serialVersionUID = 1L;
	private Mundo renderer;

	private int janelaLargura  = 600, janelaAltura = 600;


	public Frame() {		
		super("Space Inveders 3D");   
		setBounds(300,50,janelaLargura,janelaAltura+22);  // 400 + 22 da borda do titulo da janela
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);

		/* Cria um objeto GLCapabilities para especificar 
		 * o numero de bits por pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8); 

		/* Cria um canvas, adiciona ao frame e objeto "ouvinte" 
		 * para os eventos Gl, de mouse e teclado
		 */

		renderer = new Mundo();
		GLCanvas canvas = new GLCanvas(glCaps);
		add(canvas,BorderLayout.CENTER);
		canvas.addGLEventListener(renderer);  
		canvas.addKeyListener(renderer);
		canvas.requestFocus();
	}

	public static void main(String[] args) {
		new Frame().setVisible(true);
		Configuration.getInstace(EnNivelJogo.getInstance(1));
		
	}

}
