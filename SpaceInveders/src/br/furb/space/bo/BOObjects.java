package br.furb.space.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.media.opengl.GL;

import br.furb.space.ambiente.Alien;
import br.furb.space.componentes.BoundingBox;
import br.furb.space.componentes.ObjetoGrafico;
import br.furb.space.componentes.Ponto4D;
import br.furb.space.config.Configuration;

import com.sun.opengl.util.GLUT;

public final class BOObjects {

	private BOObjects(){

	}

	public static List<Alien> createAliens(GL gl, GLUT glut){
		List<Alien> aliens = new ArrayList<Alien>();

		Configuration instace = Configuration.getInstace();
		if (instace != null) {
			int qtdLinhas = 0;
			int qtdColum = 0;

			switch (instace.nivelJogo) {
			case EASY:
				qtdLinhas = 10;
				qtdColum = 3;
				break;
			case MEDIUM:
				qtdLinhas = 30;
				qtdColum = 5;
				break;
			case HARD:
				qtdLinhas = 50;
				qtdColum = 10;
			}
			
			
			int linha = 0;
			for (int i = 0; i < qtdLinhas; i++) {
				linha +=3;
				
				Random random = new Random();
				
				for (int j = 0; j < qtdColum; j++) {
					int colum = random.nextInt(qtdColum);
					
					Alien alienPos = new Alien(gl,glut, colum, linha);
					Alien alienNeg = new Alien(gl,glut, (-1 * colum), linha);
					
					aliens.add(alienPos);
					aliens.add(alienNeg);
				}
			}
		}

		return aliens;
	}
	
	public static boolean validPosition(ObjetoGrafico nave, ObjetoGrafico alien){
		BoundingBox bboxNave = nave.getBbox();
		BoundingBox bboxAlien = alien.getBbox();
		
		Ponto4D pontoMinNave = new Ponto4D(bboxNave.obterMenorX(), bboxNave.obterMenorY(), bboxNave.obterMenorZ(), 1);
		Ponto4D pontoMenorNave = nave.getMatrizObjeto().transformPoint(pontoMinNave);

		Ponto4D pontoMinAlien = new Ponto4D(bboxAlien.obterMenorX(), bboxAlien.obterMenorY(), bboxAlien.obterMenorZ(), 1);
		Ponto4D pontoMenorAlien = alien.getMatrizObjeto().transformPoint(pontoMinAlien);
		
		Ponto4D pontoMaxAlien = new Ponto4D(bboxAlien.obterMaiorX(), bboxAlien.obterMaiorY(), bboxAlien.obterMaiorZ(), 1);
		Ponto4D pontoMaiorAlien = alien.getMatrizObjeto().transformPoint(pontoMaxAlien);
		
		if (pontoMaiorAlien.obterX() >= pontoMenorNave.obterX() && pontoMenorAlien.obterX() <= pontoMenorNave.obterX() &&//
				pontoMaiorAlien.obterY() >= pontoMenorNave.obterY() && pontoMenorAlien.obterY() <= pontoMenorNave.obterY() &&//
				pontoMaiorAlien.obterZ() >= pontoMenorNave.obterZ() && pontoMenorAlien.obterZ() <= pontoMenorNave.obterZ()) {
			return true;
		}
	
		return false;
	}
	
}
