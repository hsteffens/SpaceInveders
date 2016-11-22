package br.furb.space.componentes;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class Tiro extends ObjetoGrafico {

	@Override
	public void display(GLAutoDrawable arg0) {
		draw();
	}

	@Override
	public void draw() {

		Transformacao4D transformMatrix = getMatrizGlobal().transformMatrix(getMatrizTmpEscala());
		transformMatrix = transformMatrix.transformMatrix(getMatrizTmpTranslacao());

		setMatrizObjeto(transformMatrix);


		float[] rgba = {1f, 1f, 1f};
		getGl().glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
		getGl().glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
		getGl().glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
		getGl().glPushMatrix();

		getGl().glScalef((float) getMatrizTmpEscala().getData()[0],(float) getMatrizTmpEscala().getData()[5],(float) getMatrizTmpEscala().getData()[10]);
		getGl().glTranslated((float) getMatrizTmpTranslacao().getData()[12],(float) getMatrizTmpTranslacao().getData()[13],(float) getMatrizTmpTranslacao().getData()[14]);

		getGl().glColor3f(1.0f, 0.0f, 1.0f);
		getGlut().glutSolidCube(0.1f);

		setBbox(new BoundingBox(-0.05, -0.05, -0.05, 0.05, 0.05, 0.05));

		getGl().glPopMatrix();

		getGl().glFlush();
	}
}
