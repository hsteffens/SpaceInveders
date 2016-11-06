package br.furb.space.componentes;

/**
 * Classe que representa as configurações de uma camera.
 * 
 * @author Bruno
 *
 */
public class Camera2D {

	private float xMin;
	private float xMax;
	private float yMin;
	private float yMax;
	
	public Camera2D(float xMin, float xMax, float yMin, float yMax) {
		super();
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	public float getxMin() {
		return xMin;
	}

	public void setxMin(float xMin) {
		this.xMin = xMin;
	}

	public float getxMax() {
		return xMax;
	}

	public void setxMax(float xMax) {
		this.xMax = xMax;
	}

	public float getyMin() {
		return yMin;
	}

	public void setyMin(float yMin) {
		this.yMin = yMin;
	}

	public float getyMax() {
		return yMax;
	}

	public void setyMax(float yMax) {
		this.yMax = yMax;
	}
}
