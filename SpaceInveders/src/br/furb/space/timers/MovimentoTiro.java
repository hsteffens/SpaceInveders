package br.furb.space.timers;

import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import br.furb.space.ambiente.Alien;
import br.furb.space.ambiente.Mundo;
import br.furb.space.ambiente.Spaceship;
import br.furb.space.bo.BOObjects;
import br.furb.space.componentes.ObjetoGrafico;
import br.furb.space.componentes.Tiro;

public class MovimentoTiro implements Runnable{

	private Mundo cena;

	public MovimentoTiro(Mundo cena) {
		super();
		this.cena = cena;
	}



	@Override
	public void run() {
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				Spaceship nave = (Spaceship) cena.getObjetoSelecionado();
				if (nave != null && !nave.getTiros().isEmpty()) {
					try{
						for (Tiro tiro : nave.getTiros()) {
							if (tiro.getBbox() == null) {
								return;
							}

							tiro.getMatrizTmpTranslacao().getData()[14] += 0.5f; 

							for (ObjetoGrafico alien : cena.getlObjetos()) {
								if (alien instanceof Alien) {
									if (BOObjects.validPosition(tiro, alien)) {
										alien.setElegivelRemocao(true);
										tiro.setElegivelRemocao(true);
									}
								}
							}
						}
					}catch (ConcurrentModificationException e){}

					cena.getGlDrawable().display();
				}

			}
		};

		Timer timer = new Timer(MovimentoTiro.class.getCanonicalName());
		timer.scheduleAtFixedRate(timerTask, 2500, 100);
	}

}