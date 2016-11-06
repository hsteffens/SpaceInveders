package br.furb.space.ambiente;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GLAutoDrawable;

import br.furb.space.bo.BOObjects;
import br.furb.space.componentes.Camera2D;
import br.furb.space.componentes.ObjetoGrafico;

/**
 * Classe que representa o ambiente do sistema.
 * 
 * @author helinton.steffens
 *
 */
public class Mundo extends OpenGL{

	private Camera2D camera;
	private List<ObjetoGrafico> lObjetos = new ArrayList<ObjetoGrafico>();
	private ObjetoGrafico objetoSelecionado;
	
	private float[] corFundo = new float[]{1.0f,0.0f,0.0f};

	public Mundo() {

	}

	public Camera2D getCamera() {
		return camera;
	}
	public void setCamera(Camera2D camera) {
		this.camera = camera;
	}
	public List<ObjetoGrafico> getlObjetos() {
		return lObjetos;
	}
	public void setlObjetos(List<ObjetoGrafico> lObjetos) {
		this.lObjetos = lObjetos;
	}
	public ObjetoGrafico getObjetoSelecionado() {
		if (objetoSelecionado == null && !lObjetos.isEmpty()) {
			objetoSelecionado = lObjetos.get(0);
		}
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(ObjetoGrafico objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
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
		
		lObjetos.add(spaceship);
		for (Alien alien : aliens) {
			lObjetos.add(alien);
		}
		
		setObjetoSelecionado(spaceship);
		
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		super.display(arg0);

		for (ObjetoGrafico objetoGrafico : lObjetos) {
			objetoGrafico.draw();
		}
		
		if (getObjetoSelecionado() != null) {
			getObjetoSelecionado().desenhaBoundingBox();
		}

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		ObjetoGrafico nave = getObjetoSelecionado();
		if (nave != null) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				nave.getMatrizTmpTranslacao().GetDate()[14] += 0.5f; 
				setzCenter(getzCenter() * 2);
				setzEye(getzEye() + 2.0f);
				setyEye(getyEye() - 0.25f);
				break;
			case KeyEvent.VK_LEFT:
				nave.getMatrizTmpTranslacao().GetDate()[12] += 0.5f; 
				setxCenter(getxCenter() + 2.0f);
				setxEye(getxEye() + 2.0f);
				break;
			case KeyEvent.VK_RIGHT:
				nave.getMatrizTmpTranslacao().GetDate()[12] -= 0.5f; 
				setxCenter(getxCenter()  - 2.0f);
				setxEye(getxEye() - 2.0f);
				break;
			case KeyEvent.VK_DOWN:
				nave.getMatrizTmpTranslacao().GetDate()[14] -= 0.5f; 
				setzCenter(getzCenter() / 2);
				setzEye(getzEye() - 2.0f);
				setyEye(getyEye() + 0.25f);
				break;
			}
			getGlDrawable().display();
		}
	}
	
}
