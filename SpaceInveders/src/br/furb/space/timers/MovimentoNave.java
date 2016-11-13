package br.furb.space.timers;

import java.util.Timer;
import java.util.TimerTask;

import br.furb.space.ambiente.Mundo;
import br.furb.space.componentes.ObjetoGrafico;

public class MovimentoNave extends Mundo implements Runnable{

	private static long velocidade = 2000;
	
	@Override
	public void run() {
		 TimerTask timerTask = new TimerTask() {

	            @Override
	            public void run() {
	            	System.out.println("Movimento aut.");
	            	ObjetoGrafico nave = getObjetoSelecionado();
	        		nave.getMatrizTmpTranslacao().GetDate()[14] += 0.5f; 
	        		setzCenter(getzCenter() * 2);
	        		setzEye(getzEye() + 2.0f);
	        		setyEye(getyEye() - 0.25f);
	        		
	        		getGlDrawable().display();
	            }
	        };

	        if (velocidade > 500) {
	        	velocidade -= 500;
			}
	        
	        Timer timer = new Timer(MovimentoNave.class.getCanonicalName());//create a new Timer
	        timer.scheduleAtFixedRate(timerTask, 1000, velocidade);//
	}

}
