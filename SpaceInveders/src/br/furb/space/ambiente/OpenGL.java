package br.furb.space.ambiente;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

/**
 * Classe com os facilitadores para manipulação da API OpenGl.
 * 
 * @author helinton.steffens
 *
 */
public class OpenGL implements GLEventListener,KeyListener {

	private static GL gl;
	private static GLU glu;
	private static GLUT glut;
	private static GLAutoDrawable glDrawable;
	
	private static double xEye, yEye, zEye;
	private static double xCenter, yCenter, zCenter;

	public OpenGL() {

	}

	public GL getGl() {
		return gl;
	}

	public void setGl(GL openGl) {
		gl = openGl;
	}

	public GLU getGlu() {
		return glu;
	}

	public void setGlu(GLU gluGl) {
		glu = gluGl;
	}

	public GLUT getGlut() {
		return glut;
	}

	public void setGlut(GLUT glutGL) {
		glut = glutGL;
	}

	public GLAutoDrawable getGlDrawable() {
		return glDrawable;
	}

	public void setGlDrawable(GLAutoDrawable drawable) {
		glDrawable = drawable;
	}

	public double getxEye() {
		return xEye;
	}

	public void setxEye(double x) {
		xEye = x;
	}

	public double getyEye() {
		return yEye;
	}

	public void setyEye(double y) {
		yEye = y;
	}

	public double getzEye() {
		return zEye;
	}

	public void setzEye(double z) {
		zEye = z;
	}

	public double getxCenter() {
		return xCenter;
	}

	public void setxCenter(double x) {
		xCenter = x;
	}

	public double getyCenter() {
		return yCenter;
	}

	public void setyCenter(double y) {
		yCenter = y;
	}

	public double getzCenter() {
		return zCenter;
	}

	public void setzCenter(double z) {
		zCenter = z;
	}

	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		xEye = 0.0f; 		yEye = 5.0f; 		zEye = -15.0f;
		xCenter = 0.0f;		yCenter = 2.5f;		zCenter = 0.0f;
//		
//		
//		float posLight[] = { 0.0f, 0.0f, 0.0f, 0.0f };
		
//		xEye = 0.0f; 		yEye = 5.0f; 		zEye = -20.0f;
//		xCenter = 0.0f;		yCenter = 0.0f;		zCenter = 2.0f;
		
		
		float posLight[] = { 5.0f, 5.0f, 10.0f, 0.0f };
		
		
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
		gl.glEnable(GL.GL_LIGHT0);
		
	    gl.glEnable(GL.GL_CULL_FACE);
//	    gl.glDisable(GL.GL_CULL_FACE);
		
	    gl.glEnable(GL.GL_DEPTH_TEST);
//	    gl.glDisable(GL.GL_DEPTH_TEST);
	}	
	
	public void display(GLAutoDrawable drawable) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
		glu.gluLookAt(xEye, yEye, zEye, xCenter, yCenter, zCenter, 0.0f, 1.0f, 0.0f);
		
	}
	
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);

	    glu.gluPerspective(60, width/height, 0.1, 50);				// projecao Perpectiva 1 pto fuga 3D    
	}

	
	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
