package br.furb.space.ambiente;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import br.furb.space.componentes.OBJModel;
import br.furb.space.componentes.ObjetoGrafico;
import br.furb.space.componentes.Transformacao4D;

public class Alien extends ObjetoGrafico {

	public Alien(GL gl,GLUT glut, float x, float z) {
		
		Transformacao4D transformacaoEsc = new Transformacao4D();
		transformacaoEsc.atribuirEscala(4.0f, 4.0f, 4.0f);
		
		Transformacao4D transformacaoTrans = new Transformacao4D();
		transformacaoTrans.atribuirTranslacao(x, 0.0f, z);
		
		setRotacionaY(180);
		
		setMatrizTmpTranslacao(transformacaoTrans);
		setMatrizTmpEscala(transformacaoEsc);
		
		setGl(gl);
		setGlut(glut);
		
		setObj(new OBJModel("data/Aliene_OBJ", 1.5f, gl, true));
	}
	
	@Override
	public void draw() {
		
		getGl().glEnable(GL.GL_LIGHTING);
		getGl().glEnable(GL.GL_LIGHT0);
		
		float pos[] = {0.0f, 0.0f, 3.0f, 1.0f };
		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
		float dif[] = {1.0f,1.0f,1.0f,1.0f};
		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, dif, 0);
//		float amb[] = { 0.0f, 0.0f, 0.0f, 1f };
//		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, amb, 0);
		
		super.draw();
		
		getGl().glDisable(GL.GL_LIGHTING);
		getGl().glDisable(GL.GL_LIGHT0);
	}
	
}
