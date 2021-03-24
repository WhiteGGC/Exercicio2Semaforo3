package view;

import java.util.concurrent.Semaphore;

import controller.ThreadVolta;

public class Main {

	public static void main(String[] args) {
		
		int escuderia = 0;
		Semaphore pista = new Semaphore(5);
		Semaphore semaescuderia[] = new Semaphore[7];
		
		for(int i=0;i<semaescuderia.length;i++){
			semaescuderia[i] = new Semaphore(1);
		}
		
		for(int i=0;i<14;i++){
			Thread tCorrida = new ThreadVolta(i, escuderia, semaescuderia[escuderia], pista);
			tCorrida.start();
			if(i%2!=0){
				escuderia++;
			}
		}

	}

}
