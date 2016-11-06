package br.furb.space.ambiente;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import br.furb.space.componentes.ObjetoGrafico;
import br.furb.space.componentes.Transformacao4D;

public class Spaceship extends ObjetoGrafico {

	public Spaceship(GL gl, GLUT glut) {
		setCorObjeto(new float[]{ 1.0f, 0.0f, 0.0f, 1.0f });
		
		Transformacao4D transformacaoEsc = new Transformacao4D();
		transformacaoEsc.atribuirEscala(4.0f, 4.0f, 4.0f);
		
		Transformacao4D transformacaoTrans = new Transformacao4D();
		transformacaoTrans.atribuirTranslacao( 0.0f, 0.0f, -2.0f );
		
		setMatrizTmpTranslacao(transformacaoTrans);
		setMatrizTmpEscala(transformacaoEsc);
		
		setGl(gl);
		setGlut(glut);
	}
	
	@Override
	public void draw() {
		super.draw();
		getGl().glEnable(GL.GL_LIGHTING);
		getGl().glEnable(GL.GL_LIGHT0);
		
		
		float pos[] = {0.0f, 0.0f, 0.0f, 1.0f };
		float  dir[] = { 0, 0, 5 };
		
		if (getMatrizTmpTranslacao().GetDate()[12] > 0) {
			dir[0] = (float) getMatrizTmpTranslacao().GetDate()[12] + 2;
		}else if(getMatrizTmpTranslacao().GetDate()[12] < 0){
			dir[0] = (float) getMatrizTmpTranslacao().GetDate()[12] - 2;
		}
		if (getMatrizTmpTranslacao().GetDate()[13] > 0) {
			dir[1] = (float) getMatrizTmpTranslacao().GetDate()[13] + 2;
		}else if(getMatrizTmpTranslacao().GetDate()[13] < 0){
			dir[1] = (float) getMatrizTmpTranslacao().GetDate()[13] - 2;
		}
		
		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
		getGl().glLightf(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, 30);
		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_SPOT_DIRECTION, dir,0 );
		getGl().glDisable(GL.GL_LIGHTING);
		getGl().glDisable(GL.GL_LIGHT0);
	}
}
