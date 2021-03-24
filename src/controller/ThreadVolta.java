package controller;

import java.util.concurrent.Semaphore;

public class ThreadVolta extends Thread {
	
	private int Id;
	private int Idescuderia;
	private double tempov;
	private Semaphore pista;
	private Semaphore escuderia[] = new Semaphore[7];
	private static double melhores[][] = new double[14][2];
	private static int cond;
	
	public ThreadVolta(int Id,int Idescuderia, Semaphore escuderia, Semaphore pista){
		this.Id = Id;
		this.Idescuderia = Idescuderia;
		this.escuderia[Idescuderia] = escuderia;
		this.pista = pista;
	}
	@Override
	public void run() {
		try {
			escuderia[Idescuderia].acquire();
			pista.acquire();
			Correr();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			pista.release();
			escuderia[Idescuderia].release();
		}
		if(cond==14){
			Grid();
		}
	}
	
	private void Correr(){
		for(int i=0;i<3;i++){
			double tempoi = System.nanoTime();
			int sleep = (int)(Math.random() * 3001 + 7000);
			try {
				sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			double tempof = System.nanoTime();
			tempov = tempof - tempoi;
			tempov = tempov / Math.pow(10, 9);
			System.out.println("O Carro "+(Id+1)+" da escuderia "+(Idescuderia+1)+" fez a volta em "+tempov+"s");
			if(melhores[Id][0]>tempov || melhores[Id][0] == 0){
				melhores[Id][0] = tempov;
				melhores[Id][1] = Id;
				System.out.println("O Carro "+(Id+1)+" bateu seu recorde!");
			}
		}
		cond++;
	}
	
	private void Grid(){
		String apresentacao="";
		for(int i=0;i<melhores.length - 1;i++){
			for(int j=0;j<melhores.length - 1;j++){
				if(melhores[j][0]>melhores[j+1][0]){
					double tempo = melhores[j][0];
					double id = melhores[j][1];
					melhores[j][0] = melhores[j+1][0];
					melhores[j][1] = melhores[j+1][1];
					melhores[j+1][0] = tempo;
					melhores[j+1][1] = id;
				}
			}
		}
		for(int i=0;i<14;i++){
			int pos = (int)(melhores[i][1]);
			apresentacao = apresentacao +(i+1)+"° - carro "+(pos+1)+" com "+melhores[i][0]+" segundos\n";
		}
		System.out.println("\nGrid de largada/melhores tempos do treino: \n"+apresentacao);
	}
	
}
