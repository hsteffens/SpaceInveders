package br.furb.space.ambiente;

import java.awt.event.KeyEvent;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import br.furb.space.bo.BOObjects;
import br.furb.space.componentes.Camera2D;
import br.furb.space.componentes.ObjetoGrafico;
import br.furb.space.componentes.Tiro;
import br.furb.space.timers.MovimentoNave;
import br.furb.space.timers.MovimentoTiro;

import com.sun.opengl.util.GLUT;

/**
 * Classe que representa o ambiente do sistema.
 * 
 * @author helinton.steffens
 *
 */
public class Mundo extends OpenGL{

	private Camera2D camera;
	private List<ObjetoGrafico> lObjetos;
	private ObjetoGrafico objetoSelecionado;

	private float[] corFundo = new float[]{1.0f,0.0f,0.0f};

	private boolean gameOver;

	private static Thread threadNave;
	private static Thread threadTiro;



	public Mundo() {

	}

	
	public boolean isGameOver() {
		return gameOver;
	}


	public void setGameOver(boolean acabou) {
		this.gameOver = acabou;
	}



	public Camera2D getCamera() {
		return camera;
	}
	public void setCamera(Camera2D camera) {
		this.camera = camera;
	}
	public List<ObjetoGrafico> getlObjetos() {
		if (lObjetos == null) {
			lObjetos = new ArrayList<ObjetoGrafico>();
		}
		
		return lObjetos;
	}
	public void setlObjetos(List<ObjetoGrafico> objetos) {
		lObjetos = objetos;
	}
	public ObjetoGrafico getObjetoSelecionado() {
		if (objetoSelecionado == null && !lObjetos.isEmpty()) {
			objetoSelecionado = lObjetos.get(0);
		}
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(ObjetoGrafico objeto) {
		objetoSelecionado = objeto;
	}
	public float[] getCorFundo() {
		return corFundo;
	}
	public void setCorFundo(float[] corFundo) {
		this.corFundo = corFundo;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		super.init(drawable);
		Spaceship spaceship = new Spaceship(getGl(), getGlut());
		List<Alien> aliens = BOObjects.createAliens(getGl(), getGlut());

		getlObjetos().add(spaceship);
		for (Alien alien : aliens) {
			getlObjetos().add(alien);
		}

		setObjetoSelecionado(spaceship);

	}

	@Override
	public void display(GLAutoDrawable arg0) {
		super.display(arg0);

		if (!isGameOver()) {
			
			Iterator<ObjetoGrafico> iterator = getlObjetos().iterator();
			while (iterator.hasNext()) {
				ObjetoGrafico objetoGrafico = iterator.next();
				
				if (objetoGrafico.isElegivelRemocao()) {
					iterator.remove();
					continue;
				}
				objetoGrafico.draw();
				
				if (objetoGrafico instanceof Spaceship) {
					Spaceship nave = (Spaceship) objetoGrafico;
					
					Iterator<Tiro> iteratorTiros = nave.getTiros().iterator();
					while (iteratorTiros.hasNext()) {
						Tiro tiro = iteratorTiros.next();
						if (tiro.isElegivelRemocao()) {
							iteratorTiros.remove();
						}
						tiro.draw();
					}
				}
			}
			
			if (threadNave == null && threadTiro == null) {
				threadNave = new Thread(new MovimentoNave(this));
				threadTiro = new Thread(new MovimentoTiro(this));
			}

			if (threadNave.getState() != State.TERMINATED && threadTiro.getState() != State.TERMINATED) {
				threadNave.start();
				threadTiro.start();
			}
		}else{
			threadNave.stop();
			threadTiro.stop();
			
		    printGameOverMensage(); 
		}

	}


	private void printGameOverMensage() {
		getGl().glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		getGl().glMatrixMode(GL.GL_PROJECTION);
		getGl().glLoadIdentity();
		
		getGlu().gluLookAt(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

		try {
		    getGlu().gluOrtho2D(getCamera().getxMax(), getCamera().getxMin(), getCamera().getyMin(), getCamera().getxMax());
		} catch (Exception e) {}

		getGl().glColor3ub((byte)255, (byte)0, (byte)0);
		
		drawMensagem(0, 0, "Game Over");
		
		getGl().glFlush();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		ObjetoGrafico nave = getObjetoSelecionado();
		if (nave != null) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				if (nave instanceof Spaceship) {
					((Spaceship) nave).atirar();
				}

				break;
			case KeyEvent.VK_LEFT:
				if (nave.getMatrizTmpTranslacao().getData()[12] >= 2.5f) {
					return;
				}

				nave.getMatrizTmpTranslacao().getData()[12] += 0.5f; 
				setxCenter(getxCenter() + 2.0f);
				setxEye(getxEye() + 2.0f);

				for (ObjetoGrafico objetoGrafico : getlObjetos()) {
					if (objetoGrafico instanceof Alien) {
						if (BOObjects.validPosition(nave, objetoGrafico)) {
							setGameOver(true);
						}

					}
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (nave.getMatrizTmpTranslacao().getData()[12] <= -2.5f) {
					return;
				}

				nave.getMatrizTmpTranslacao().getData()[12] -= 0.5f; 
				setxCenter(getxCenter()  - 2.0f);
				setxEye(getxEye() - 2.0f);

				for (ObjetoGrafico objetoGrafico : getlObjetos()) {
					if (objetoGrafico instanceof Alien) {
						if (BOObjects.validPosition(nave, objetoGrafico)) {
							setGameOver(true);
						}

					}
				}
				break;
			}
			getGlDrawable().display();
		}
	}


	public void drawMensagem(int x, int y, String texto) {
		getGl().glRasterPos2i(x, y);
		//desenha letra por letra na tela

		getlObjetos().clear();
		try {
			for(int i=0; i < texto.length(); i++)
				getGlut().glutBitmapCharacter(GLUT.BITMAP_TIMES_ROMAN_24, texto.charAt(i));
		} catch(Exception e) {}

	}

}
