package br.furb.space.bo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import br.furb.space.ambiente.Alien;

public final class BOObjects {

	private BOObjects(){

	}

	public static List<Alien> createAliens(GL gl, GLUT glut, float x, float z){
		List<Alien> aliens = new ArrayList<Alien>();

		for (int i = 0; i < 3; i++) {
			x = 0;
			Alien alien = new Alien(gl,glut, x, z);
			x = -2;
			Alien alien1 = new Alien(gl,glut, x, z);
			x = 2;
			Alien alien2 = new Alien(gl,glut, x, z);
			z += 3;
			
			aliens.add(alien);
			aliens.add(alien1);
			aliens.add(alien2);
		}

		return aliens;
	}
}
