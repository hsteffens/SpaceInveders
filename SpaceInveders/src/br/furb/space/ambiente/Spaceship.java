package br.furb.space.ambiente;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;

import br.furb.space.componentes.OBJModel;
import br.furb.space.componentes.ObjetoGrafico;
import br.furb.space.componentes.Transformacao4D;

import com.sun.opengl.util.GLUT;

public class Spaceship extends ObjetoGrafico {

	public Spaceship(GL gl, GLUT glut) {
		
		Transformacao4D transformacaoEsc = new Transformacao4D();
		transformacaoEsc.atribuirEscala(4.0f, 4.0f, 4.0f);
		
		Transformacao4D transformacaoTrans = new Transformacao4D();
		transformacaoTrans.atribuirTranslacao( 0.0f, 0.0f, -1.0f );
		
		setMatrizTmpTranslacao(transformacaoTrans);
		setMatrizTmpEscala(transformacaoEsc);
		
		setGl(gl);
		setGlut(glut);
		
		setObj(new OBJModel("data/Arc170", 1.5f, gl, true));
	}
	
	@Override
	public void draw() {

		getGl().glEnable(GL.GL_LIGHTING);
		getGl().glEnable(GL.GL_LIGHT0);
		
		
		float pos[] = {0.0f, 0.0f, -10.0f, 1.0f };
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
		
		
		float  flashLightColor[] = { 0.8f, 0.8f, 0.8f, 1.0f };
		FloatBuffer color = FloatBuffer.wrap(flashLightColor);

		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, color);
		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, color);
		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, color);
		getGl().glLightf(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF,  45.0f);
		getGl().glLightfv(GL.GL_LIGHT0, GL.GL_SPOT_DIRECTION, dir,0 );
		
		super.draw();

		getGl().glDisable(GL.GL_LIGHTING);
		getGl().glDisable(GL.GL_LIGHT0);
	}
}
