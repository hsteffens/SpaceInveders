package br.furb.space.timers;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.furb.space.ambiente.Alien;
import br.furb.space.ambiente.Mundo;
import br.furb.space.bo.BOObjects;
import br.furb.space.componentes.ObjetoGrafico;

public class CreateAliens extends Mundo implements Runnable{

	private static long velocidade = 3000;
	
	@Override
	public void run() {
		 TimerTask timerTask = new TimerTask() {

	            @Override
	            public void run() {
	            	System.out.println("Cria aliens.");
	            	List<ObjetoGrafico> lObjetos = getlObjetos();
	            	ObjetoGrafico ultimoAlienCriado = lObjetos.get(lObjetos.size()-1);
	            	
	            	float indiceZ = (float) ultimoAlienCriado.getMatrizTmpTranslacao().GetDate()[14] + 3.0f;
	            	List<Alien> aliens = BOObjects.createAliens(getGl(), getGlut(), 0.0f, indiceZ);
	            	for (Alien alien : aliens) {
						lObjetos.add(alien);
					}
	        		
	        		getGlDrawable().display();
	            }
	        };

	        if (velocidade > 500) {
	        	velocidade -= 50;
			}
	        
	        Timer timer = new Timer(CreateAliens.class.getCanonicalName());//create a new Timer
	        timer.scheduleAtFixedRate(timerTask, 3000, velocidade);//
	}

}
