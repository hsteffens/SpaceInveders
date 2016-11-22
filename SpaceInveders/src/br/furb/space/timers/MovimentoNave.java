package br.furb.space.timers;

import java.util.Timer;
import java.util.TimerTask;

import br.furb.space.ambiente.Alien;
import br.furb.space.ambiente.Mundo;
import br.furb.space.bo.BOObjects;
import br.furb.space.componentes.ObjetoGrafico;

public class MovimentoNave implements Runnable{

	private static long velocidade = 2000;
	
	private Mundo cena;
	
	public MovimentoNave(Mundo cena) {
		super();
		this.cena = cena;
	}

	@Override
	public void run() {
			TimerTask timerTask = new TimerTask() {

				@Override
				public void run() throws IllegalStateException{
					ObjetoGrafico nave = cena.getObjetoSelecionado();
					nave.getMatrizTmpTranslacao().getData()[14] += 0.5f; 
					cena.setzCenter(cena.getzCenter() + 2.0f);
					cena.setzEye(cena.getzEye() + 2.0f);

					for (ObjetoGrafico objetoGrafico : cena.getlObjetos()) {
						if (objetoGrafico instanceof Alien) {
							if (BOObjects.validPosition(nave, objetoGrafico)) {
								cena.setGameOver(true);
							}

						}
					}

					cena.getGlDrawable().display();
				}
			};

			if (velocidade > 500) {
				velocidade -= 500;
			}
		
			Timer timer = new Timer(MovimentoNave.class.getCanonicalName());//create a new Timer
			timer.scheduleAtFixedRate(timerTask, 2000, velocidade);//
	}

}
