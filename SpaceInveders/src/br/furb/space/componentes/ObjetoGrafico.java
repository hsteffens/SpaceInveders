package br.furb.space.componentes;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import br.furb.space.ambiente.OpenGL;

/**
 * Classe que representa um objeto gráfico em grafo de cena.
 * 
 * @author helinton.steffens
 *
 */
public class ObjetoGrafico extends OpenGL {

	private OBJModel obj;
	
	private float[] corObjeto = new float[]{0.0f,0.0f,0.0f};

	private List<ObjetoGrafico> lObjetos = new ArrayList<ObjetoGrafico>();
	private List<Ponto4D> arestas = new ArrayList<Ponto4D>();
	
	private BoundingBox bbox;
	private Ponto4D verticeSelecionado;
	private Transformacao4D matrizObjeto = new Transformacao4D();

	private  Transformacao4D matrizGlobal = new Transformacao4D();
	private  Transformacao4D matrizTmpTranslacao = new Transformacao4D();
	private  Transformacao4D matrizTmpTranslacaoInversa = new Transformacao4D();
	private  Transformacao4D matrizTmpEscala = new Transformacao4D();		
	
	private float rotacionaX = 0.0f;
	private float rotacionaY = 0.0f;
	private float rotacionaZ = 0.0f;
	
	public BoundingBox getBbox() {
		return bbox;
	}

	public void setBbox(BoundingBox bbox) {
		this.bbox = bbox;
	}

	public List<ObjetoGrafico> getlObjetos() {
		return lObjetos;
	}

	public void setlObjetos(List<ObjetoGrafico> lObjetos) {
		this.lObjetos = lObjetos;
	}

	public float[] getCorObjeto() {
		return corObjeto;
	}

	public void setCorObjeto(float[] corObjeto) {
		this.corObjeto = corObjeto;
	}
	
	public List<Ponto4D> getArestas() {
		return arestas;
	}

	public Ponto4D getVerticeSelecionado() {
		return verticeSelecionado;
	}

	public void setVerticeSelecionado(Ponto4D verticeSelecionado) {
		this.verticeSelecionado = verticeSelecionado;
	}
	
	public Transformacao4D getMatrizObjeto() {
		return matrizObjeto;
	}

	public void setMatrizObjeto(Transformacao4D matrizObjeto) {
		this.matrizObjeto = matrizObjeto;
	}

	public Transformacao4D getMatrizGlobal() {
		return matrizGlobal;
	}

	public void setMatrizGlobal(Transformacao4D matrizGlobal) {
		this.matrizGlobal = matrizGlobal;
	}

	public Transformacao4D getMatrizTmpTranslacao() {
		return matrizTmpTranslacao;
	}

	public void setMatrizTmpTranslacao(Transformacao4D matrizTmpTranslacao) {
		this.matrizTmpTranslacao = matrizTmpTranslacao;
	}

	public Transformacao4D getMatrizTmpTranslacaoInversa() {
		return matrizTmpTranslacaoInversa;
	}

	public void setMatrizTmpTranslacaoInversa(
			Transformacao4D matrizTmpTranslacaoInversa) {
		this.matrizTmpTranslacaoInversa = matrizTmpTranslacaoInversa;
	}

	public  Transformacao4D getMatrizTmpEscala() {
		return matrizTmpEscala;
	}

	public  void setMatrizTmpEscala(Transformacao4D matrizTmpEscala) {
		this.matrizTmpEscala = matrizTmpEscala;
	}

	public void setArestas(List<Ponto4D> arestas) {
		this.arestas = arestas;
	}
	
	public void adicionarAresta(Ponto4D ponto) {
		this.arestas.add(ponto);
	}
	
	public void removerAresta(Ponto4D ponto) {
		this.arestas.remove(ponto);
	}
	
	public OBJModel getObj() {
		return obj;
	}

	public void setObj(OBJModel obj) {
		this.obj = obj;
	}
	
	public float getRotacionaX() {
		return rotacionaX;
	}

	public void setRotacionaX(float rotacionaX) {
		this.rotacionaX = rotacionaX;
	}

	public float getRotacionaY() {
		return rotacionaY;
	}

	public void setRotacionaY(float rotacionaY) {
		this.rotacionaY = rotacionaY;
	}

	public float getRotacionaZ() {
		return rotacionaZ;
	}

	public void setRotacionaZ(float rotacionaZ) {
		this.rotacionaZ = rotacionaZ;
	}

	/**
	 * Adiciona um {@link ObjetoGrafico} a lista de {@link ObjetoGrafico} com as mesmas caracteristicas do objeto pai.
	 * 
	 * @return
	 */
	public ObjetoGrafico adicionaFilho(){
		try {
			ObjetoGrafico objeto = (ObjetoGrafico) this.clone();
			getlObjetos().add(objeto);
			
			return objeto;
		} catch (CloneNotSupportedException e) {
			throw new IllegalArgumentException("Deu problema no Clone");
		}
	}
	
	/**
	 * Realiza a translação no objeto atual a partir dos parametros informados.
	 * 
	 * @param tx
	 * @param ty
	 * @param tz
	 */
	public void translacaoXYZ(double tx, double ty, double tz) {
		Transformacao4D matrizTranslate = new Transformacao4D();
		matrizTranslate.atribuirTranslacao(tx,ty,tz);
		matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);		
	}
	
	/**
	 * Realiza a transformação da escala do objeto em relação ao ponto informado.
	 * 
	 * @param escala
	 * @param ptoFixo
	 */
	public void escalaXYZPtoFixo(double escala, Ponto4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirEscala(escala, escala, 1.0);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Realiza a rotação do objeto atual em relação ao ponto informado.
	 * 
	 * @param angulo
	 * @param ptoFixo
	 */
	public void rotacaoZPtoFixo(double angulo, Ponto4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
	}

	/**
	 * Desenha a bbox do objeto gráfico.
	 */
	public void desenhaBoundingBox(){
		if (bbox != null) {
			bbox.desenharOpenGLBBox(getGl(), getCorObjeto()[0], getCorObjeto()[1], getCorObjeto()[2]);
		}
	}
	
	@Override
	public void display(GLAutoDrawable arg0) {
		super.display(arg0);
		draw();
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		super.init(drawable);
	}
	
	
	public void draw() {
		getGl().glPushMatrix();

		getGl().glScalef((float) getMatrizTmpEscala().GetDate()[0],(float) getMatrizTmpEscala().GetDate()[5],(float) getMatrizTmpEscala().GetDate()[10]);
		getGl().glTranslated((float) getMatrizTmpTranslacao().GetDate()[12],(float) getMatrizTmpTranslacao().GetDate()[13],(float) getMatrizTmpTranslacao().GetDate()[14]);
		getGl().glRotatef(getRotacionaX(), 1.0f, 0.0f, 0.0f);
		getGl().glRotatef(getRotacionaY(), 0.0f, 1.0f, 0.0f);
		getGl().glRotatef(getRotacionaZ(), 0.0f, 0.0f, 1.0f);
		
		getObj().draw(getGl());
		//		getGlut().glutSolidCube(1.0f);
		
		getGl().glPopMatrix();
		getGl().glDisable(GL.GL_LIGHTING);
		
		getGl().glFlush();
	}

	
	
}
